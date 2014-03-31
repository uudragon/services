/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.processor.FinallyProcessor;

/**
 * Finally配置类
 * <p></p>
 * @author liubing
 * Date Mar 26, 2014
 */
@XmlRootElement( name = "doFinally" )
public class FinallyDefinition extends AbstractOutputsDefinition {
	@XmlTransient
	private volatile Processor processor;

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new FinallyProcessor( this );
				}
			}
		}
		return processor;
	}

}
