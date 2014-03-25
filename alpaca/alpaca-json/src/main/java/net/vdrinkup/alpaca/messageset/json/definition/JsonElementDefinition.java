/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.definition;

import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.messageset.definition.MappedElementDefinition;
import net.vdrinkup.alpaca.messageset.json.processor.JsonElementProcessor;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-27
 */
@XmlRootElement( name = "element" )
public class JsonElementDefinition extends MappedElementDefinition {
	
	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new JsonElementProcessor( this );
				}
			}
		}
		return processor;
	}

}
