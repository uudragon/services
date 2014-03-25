/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml.definition;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.messageset.definition.MappedElementDefinition;
import net.vdrinkup.alpaca.messageset.xml.processor.XMLElementProcessor;


/**
 *
` * <p></p>
 * @author liubing
 * Date Jan 16, 2014
 */
@XmlRootElement( name = "element" )
public class XMLElementDefinition extends MappedElementDefinition {
	@XmlAttribute
	private String prefix;
	@XmlAttribute
	private String namespace;
	@XmlAttribute
	private boolean isList;
	@XmlElement
	private List< XMLAttributeDefinition > attributes = new LinkedList< XMLAttributeDefinition >();
	
	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix( String prefix ) {
		this.prefix = prefix;
	}

	

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace( String namespace ) {
		this.namespace = namespace;
	}

	public boolean isList() {
		return isList;
	}

	public void setList( boolean isList ) {
		this.isList = isList;
	}

	public List< XMLAttributeDefinition > getAttributes() {
		return attributes;
	}

	public void setAttributes( List< XMLAttributeDefinition > attributes ) {
		this.attributes = attributes;
	}
	
	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new XMLElementProcessor( this );
				}
			}
		}
		return processor;
	}

}
