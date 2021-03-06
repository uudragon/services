/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.definition;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.sql.processor.SQLCollectionProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 19, 2014
 */
@XmlRootElement( name = "collection" )
public class SQLCollectionDefinition extends ProcessorDefinition {
	@XmlAttribute
	private String binding;
	@XmlElement( name = "element" )
	private List< SQLElementDefinition > elements = new LinkedList< SQLElementDefinition >();
	@XmlElement( name = "collection" )
	private List< SQLCollectionDefinition > collections = new LinkedList< SQLCollectionDefinition >();
	@XmlTransient
	private volatile Processor processor;
	
	public String getBinding() {
		return binding;
	}

	public void setBinding( String binding ) {
		this.binding = binding;
	}
	
	public List< SQLElementDefinition > getElements() {
		return elements;
	}

	public void setElements( List< SQLElementDefinition > elements ) {
		this.elements = elements;
	}

	public List< SQLCollectionDefinition > getCollections() {
		return collections;
	}

	public void setCollections( List< SQLCollectionDefinition > collections ) {
		this.collections = collections;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new SQLCollectionProcessor( this );
				}
			}
		}
		return processor;
	}

}
