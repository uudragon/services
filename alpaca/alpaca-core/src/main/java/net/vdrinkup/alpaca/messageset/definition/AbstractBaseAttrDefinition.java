/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.definition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;


/**
 * 
 * <p>
 * </p>
 * 
 * @author liubing Date Mar 10, 2014
 */
public abstract class AbstractBaseAttrDefinition extends ProcessorDefinition {
	@XmlAttribute
	protected String name;
	@XmlAttribute
	protected String binding;
	@XmlAttribute
	protected String type;
	@XmlAttribute
	protected int length;
	@XmlAttribute
	protected String format;
	@XmlAttribute
	protected String defaultValue;
	@XmlAttribute
	protected boolean required;
	@XmlTransient
	protected String path;
	@XmlTransient
	protected volatile Processor processor;

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding( String binding ) {
		this.binding = binding;
	}

	public String getType() {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength( int length ) {
		this.length = length;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue( String defaultValue ) {
		this.defaultValue = defaultValue;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired( boolean required ) {
		this.required = required;
	}

	public String getPath() {
		return path;
	}

	public void setPath( String path ) {
		this.path = path;
	}

	public Processor getProcessor() {
		return processor;
	}

	public void setProcessor( Processor processor ) {
		this.processor = processor;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat( String format ) {
		this.format = format;
	}

}
