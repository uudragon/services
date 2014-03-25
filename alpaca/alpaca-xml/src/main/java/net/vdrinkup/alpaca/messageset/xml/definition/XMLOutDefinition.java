/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml.definition;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.messageset.definition.MappedElementDefinition;
import net.vdrinkup.alpaca.messageset.definition.MessageOutputDefinition;
import net.vdrinkup.alpaca.messageset.xml.processor.XMLOutProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 26, 2013
 */
@XmlRootElement( name = "out" )
public class XMLOutDefinition extends MessageOutputDefinition {
	@XmlElementRef( name = "element", type = MappedElementDefinition.class )
	private List< MappedElementDefinition > elements = new LinkedList< MappedElementDefinition >(); 
	
	public List< MappedElementDefinition > getElements() {
		return elements;
	}

	public void setElements( List< MappedElementDefinition > elements ) {
		this.elements = elements;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new XMLOutProcessor( this );
				}
			}
		}
		return processor;
	}

}
