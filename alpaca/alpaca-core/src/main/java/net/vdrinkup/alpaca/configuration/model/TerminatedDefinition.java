/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.processor.TerminatedProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 26, 2013
 */
@XmlRootElement( name = "terminated" )
public class TerminatedDefinition extends ProcessorDefinition {
	@XmlTransient
	private volatile TerminatedProcessor processor;

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new TerminatedProcessor( this );
				}
			}
		}
		return processor;
	}

}
