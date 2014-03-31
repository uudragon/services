/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.processor.TryProcessor;

/**
 *
 * <p></p>
 * @author liubing
 * Date Mar 25, 2014
 */
@XmlRootElement( name = "doTry" )
public class TryDefinition extends AbstractOutputsDefinition {
	@XmlTransient
	private List< CatchDefinition > catchClauses = new LinkedList< CatchDefinition >();
	@XmlTransient
	private FinallyDefinition finallyClause;
	@XmlTransient
	private List< ProcessorDefinition > outputsWithoutCatch = new LinkedList< ProcessorDefinition >();
	@XmlTransient
	private volatile Processor processor;

	public List< CatchDefinition > getCatchClauses() {
		return catchClauses;
	}

	public void setCatchClauses( List< CatchDefinition > catchClauses ) {
		this.catchClauses = catchClauses;
	}

	public FinallyDefinition getFinallyClause() {
		return finallyClause;
	}

	public void setFinallyClause( FinallyDefinition finallyClause ) {
		this.finallyClause = finallyClause;
	}

	public List< ProcessorDefinition > getOutputsWithoutCatch() {
		return outputsWithoutCatch;
	}

	public void setOutputsWithoutCatch(
			List< ProcessorDefinition > outputsWithoutCatch ) {
		this.outputsWithoutCatch = outputsWithoutCatch;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					prepared();
					processor = new TryProcessor( outputsWithoutCatch, catchClauses, finallyClause );
				}
			}
		}
		return processor;
	}

	private void prepared() {
		for ( ProcessorDefinition output : getOutputs() ) {
			if ( output instanceof CatchDefinition ) {
				catchClauses.add( ( CatchDefinition ) output );
			} else if ( output instanceof FinallyDefinition ) {
				finallyClause = ( FinallyDefinition ) output;
			} else {
				outputsWithoutCatch.add( output );
			}
		}
	}

}
