/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.processor.ChoiceProcessor;


/**
 * <p>
 * </p>
 * 
 * @author bing.liu Date 2012-2-10
 */
@XmlRootElement( name = "choice" )
public class ChoiceDefinition extends ProcessorDefinition {
	@XmlElement( name = "when" )
	private List< WhenDefinition > whenClauses = new LinkedList< WhenDefinition >();
	@XmlElement
	private OtherwiseDefinition otherwise;
	@XmlTransient
	private volatile Processor processor;

	/**
	 * @return the whenClauses
	 */
	public List< WhenDefinition > getWhenClauses() {
		return whenClauses;
	}

	/**
	 * @param whenClauses
	 *            the whenClauses to set
	 */
	public void setWhenClauses( List< WhenDefinition > whenClauses ) {
		this.whenClauses = whenClauses;
	}

	/**
	 * @return the otherwise
	 */
	public OtherwiseDefinition getOtherwise() {
		return otherwise;
	}

	/**
	 * @param otherwise
	 *            the otherwise to set
	 */
	public void setOtherwise( OtherwiseDefinition otherwise ) {
		this.otherwise = otherwise;
	}

	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new ChoiceProcessor( this );
				}
			}
		}
		return processor;
	}

}
