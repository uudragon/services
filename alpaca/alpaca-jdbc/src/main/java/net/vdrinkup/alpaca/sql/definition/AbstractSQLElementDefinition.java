/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.definition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.messageset.definition.AbstractBaseAttrDefinition;
import net.vdrinkup.alpaca.sql.processor.SQLElementProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 20, 2014
 */
public abstract class AbstractSQLElementDefinition extends AbstractBaseAttrDefinition {
	@XmlAttribute
	private String symbol = "";
	@XmlTransient
	private volatile Processor processor;

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

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol( String symbol ) {
		this.symbol = symbol;
	}

	public String getType() {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue( String defaultValue ) {
		this.defaultValue = defaultValue;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new SQLElementProcessor( this );
				}
			}
		}
		return processor;
	}

}
