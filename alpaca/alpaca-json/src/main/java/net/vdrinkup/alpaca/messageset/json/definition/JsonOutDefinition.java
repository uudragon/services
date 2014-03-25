/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.definition;

import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.messageset.MessageNode;
import net.vdrinkup.alpaca.messageset.definition.MappedElementDefinition;
import net.vdrinkup.alpaca.messageset.definition.MessageOutputDefinition;
import net.vdrinkup.alpaca.messageset.json.processor.JsonOutProcessor;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-27
 */
@XmlRootElement( name = "out" )
public class JsonOutDefinition extends MessageOutputDefinition implements MessageNode {
	
	@XmlElementRef( name = "element", type = MappedElementDefinition.class )
	protected List< MessageNode > elements;

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new JsonOutProcessor( this );
				}
			}
		}
		return processor;
	}
	
	@Override
	public boolean isLeaf() {
		boolean isLeaf = true;
		if ( elements != null && elements.size() != 0 ) {
			isLeaf = false;
		}
		return isLeaf;
	}

	@Override
	public List< MessageNode > getElements() {
		return elements;
	}

	@Override
	public MessageNode findSub( String key ) {
		return null;
	}

}
