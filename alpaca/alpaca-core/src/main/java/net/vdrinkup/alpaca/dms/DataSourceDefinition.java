/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.dms;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;
import net.vdrinkup.alpaca.configuration.model.PropertyDefinition;


/**
 * 数据源配置类
 * <p>
 * </p>
 * @author liubing Date Feb 12, 2014
 */
public abstract class DataSourceDefinition extends AbstractDefinition {
	@XmlTransient
	private String name;
	@XmlAttribute
	protected String uri;
	@XmlElementRef( name = "property" )
	protected List< PropertyDefinition > properties = new LinkedList< PropertyDefinition >();

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri( String uri ) {
		this.uri = uri;
	}

	public List< PropertyDefinition > getProperties() {
		return properties;
	}

	public void setProperties( List< PropertyDefinition > properties ) {
		this.properties = properties;
	}

}
