/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.definition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.AbstractOutputsDefinition;
import net.vdrinkup.alpaca.flow.processor.TransactedProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 28, 2013
 */
@XmlRootElement( name = "transacted" )
public class TransactedDefinition extends AbstractOutputsDefinition {
	@XmlAttribute
	private String dms;
	@XmlAttribute
	private ISOLATION isolation = ISOLATION.ISOLATION_DEFAULT;
	@XmlTransient
	private volatile TransactedProcessor processor;

	public String getDms() {
		return dms;
	}

	public void setDms( String dms ) {
		this.dms = dms;
	}

	public ISOLATION getIsolation() {
		return isolation;
	}

	public void setIsolation( ISOLATION isolation ) {
		this.isolation = isolation;
	}

	public TransactedProcessor getProcessor() {
		return processor;
	}

	public void setProcessor( TransactedProcessor processor ) {
		this.processor = processor;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new TransactedProcessor( this );
				}
			}
		}
		return processor;
	}

}
