/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.definition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.flow.processor.DmsProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 17, 2014
 */
@XmlRootElement( name = "dms" )
public class DmsDefinition extends ProcessorDefinition {
	@XmlAttribute
	private String service;
	@XmlAttribute
	private String to;
	@XmlTransient
	private volatile Processor processor;

	public String getService() {
		return service;
	}

	public void setService( String service ) {
		this.service = service;
	}

	public String getTo() {
		return to;
	}

	public void setTo( String to ) {
		this.to = to;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new DmsProcessor( this );
				}
			}
		}
		return processor;
	}
	

}
