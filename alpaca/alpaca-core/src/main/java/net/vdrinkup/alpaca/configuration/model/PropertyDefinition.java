/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 属性定义类
 * <p>
 * </p>
 * @author liubing Date Feb 12, 2014
 */
@XmlRootElement( name = "property" )
public class PropertyDefinition extends AbstractDefinition {
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String value;

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue( String value ) {
		this.value = value;
	}

}
