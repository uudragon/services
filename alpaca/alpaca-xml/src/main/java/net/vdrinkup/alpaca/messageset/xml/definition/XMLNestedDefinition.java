/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml.definition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.messageset.xml.processor.XMLNestedProcessor;


/**
 * 
 * <p></p>
 * @author liubing
 * Date Mar 14, 2014
 */
@XmlRootElement( name = "nested" )
public class XMLNestedDefinition extends XMLElementDefinition {
	@XmlAttribute
	private String to;

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
					processor = new XMLNestedProcessor( this );
				}
			}
		}
		return processor;
	}

}
