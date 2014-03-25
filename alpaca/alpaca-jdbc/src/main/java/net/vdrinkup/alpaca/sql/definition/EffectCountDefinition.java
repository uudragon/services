/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.definition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.sql.processor.EffectCountProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 18, 2014
 */
@XmlRootElement( name = "effectCount" )
public class EffectCountDefinition extends ProcessorDefinition {
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
					processor = new EffectCountProcessor( this );
				}
			}
		}
		return processor;
	}

}
