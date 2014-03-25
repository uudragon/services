/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.definition;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.messageset.definition.MessageInputDefinition;
import net.vdrinkup.alpaca.sql.processor.SQLInProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Jan 15, 2014
 */
@XmlRootElement( name = "in" )
public class SQLInDefinition extends MessageInputDefinition {
	@XmlElementRef( type = ProcessorDefinition.class )
	private List< ProcessorDefinition > elements = new LinkedList< ProcessorDefinition >();

	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new SQLInProcessor( this );
				}
			}
		}
		return processor;
	}

	public List< ProcessorDefinition > getElements() {
		return elements;
	}

	public void setElements( List< ProcessorDefinition > elements ) {
		this.elements = elements;
	}

	public boolean isLeaf() {
		boolean isLeaf = false;
		if ( elements == null || elements.size() == 0 ) {
			isLeaf = true;
		}
		return isLeaf;
	}

}
