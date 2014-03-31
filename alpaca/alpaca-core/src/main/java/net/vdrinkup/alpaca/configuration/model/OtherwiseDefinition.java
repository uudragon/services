/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.processor.OtherwiseProcessor;


/**
 * <p></p>
 * @author bing.liu
 * Date 2012-2-10
 */
@XmlRootElement( name = "otherwise" )
public class OtherwiseDefinition extends AbstractOutputsDefinition {
	@XmlTransient
	private volatile Processor processor;

	/* (non-Javadoc)
	 * @see net.neptune.mp.model.ProcessorDefinition#createProcessor()
	 */
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new OtherwiseProcessor( this );
				}
			}
		}
		return processor;
	}

}
