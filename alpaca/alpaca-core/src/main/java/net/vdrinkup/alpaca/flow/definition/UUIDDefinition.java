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
import net.vdrinkup.alpaca.flow.processor.UUIDProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 21, 2014
 */
@XmlRootElement( name = "uuid" )
public class UUIDDefinition extends ProcessorDefinition {
	@XmlAttribute
	private String binding;
	@XmlTransient
	private volatile Processor processor;
	
	public String getBinding() {
		return binding;
	}

	public void setBinding( String binding ) {
		this.binding = binding;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new UUIDProcessor( this );
				}
			}
		}
		return processor;
	}

}
