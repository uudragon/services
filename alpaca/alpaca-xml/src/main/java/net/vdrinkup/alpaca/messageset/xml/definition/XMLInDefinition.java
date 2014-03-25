/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml.definition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.messageset.MessageNode;
import net.vdrinkup.alpaca.messageset.definition.MappedElementDefinition;
import net.vdrinkup.alpaca.messageset.definition.MessageInputDefinition;
import net.vdrinkup.alpaca.messageset.xml.processor.XMLInProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 26, 2013
 */
@XmlRootElement( name = "in" )
public class XMLInDefinition extends MessageInputDefinition implements MessageNode {
	@XmlElementRef( name = "element", type = MappedElementDefinition.class )
	protected List< MessageNode > elements;
	@XmlTransient
	protected Map< String, MessageNode > mappers;

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new XMLInProcessor( this );
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
		MessageNode definition = null;
		if ( mappers != null ) {
			definition = mappers.get( key );
		}
		return definition;
	}
	
	protected void afterUnmarshal( Unmarshaller unmarshaller, Object parent ) {
		this.mappers = new HashMap< String, MessageNode >( 16 );
		for ( MessageNode element : getElements() ) {
			mappers.put( element.getName(), element );
		}
	}

}
