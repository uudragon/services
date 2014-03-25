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
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Jan 16, 2014
 */
@XmlRootElement( name = "attribute" )
public class XMLAttributeDefinition extends ProcessorDefinition {
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String binding;
	@XmlAttribute
	private String namespace;
	@XmlAttribute
	private String type;
	@XmlAttribute
	private String length;
//	@XmlTransient
//	private volatile Processor processor;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName( String name ) {
		this.name = name;
	}

	/**
	 * @return the binding
	 */
	public String getBinding() {
		return binding;
	}

	/**
	 * @param binding the binding to set
	 */
	public void setBinding( String binding ) {
		this.binding = binding;
	}

	/**
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * @param namespace the namespace to set
	 */
	public void setNamespace( String namespace ) {
		this.namespace = namespace;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType( String type ) {
		this.type = type;
	}

	/**
	 * @return the length
	 */
	public String getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength( String length ) {
		this.length = length;
	}

	@Override
	public Processor createProcessor() {
		return null;
	}

}
