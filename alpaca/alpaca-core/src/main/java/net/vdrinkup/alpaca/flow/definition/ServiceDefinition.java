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
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.flow.processor.ServiceProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 26, 2013
 */
@XmlRootElement( name = "service" )
public class ServiceDefinition extends ProcessorDefinition {
	@XmlAttribute( required = true )
	private String id;
	@XmlTransient
	private volatile ServiceProcessor processor;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId( String id ) {
		this.id = id;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new ServiceProcessor( this );
				}
			}
		}
		return processor;
	}

}
