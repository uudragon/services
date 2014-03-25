/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.ws.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;


/**
 * WebService endpoint定义类
 * <p>
 * 定义Web Service的Endpoint的配置
 * </p>
 * @author liubing Date Dec 16, 2013
 */
@XmlRootElement( name = "endpoint" )
public class WsEndpointConfig extends AbstractDefinition {
	@XmlAttribute
	private String namespace;
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String port;
	@XmlAttribute
	private String wsdlLocation;
	@XmlAttribute
	private String implementor;

	/**
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * @param namespace
	 *            the namespace to set
	 */
	public void setNamespace( String namespace ) {
		this.namespace = namespace;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName( String name ) {
		this.name = name;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort( String port ) {
		this.port = port;
	}

	/**
	 * @return the wsdlLocation
	 */
	public String getWsdlLocation() {
		return wsdlLocation;
	}

	/**
	 * @param wsdlLocation the wsdlLocation to set
	 */
	public void setWsdlLocation( String wsdlLocation ) {
		this.wsdlLocation = wsdlLocation;
	}

	/**
	 * @return the implementor
	 */
	public String getImplementor() {
		return implementor;
	}

	/**
	 * @param implementor
	 *            the implementor to set
	 */
	public void setImplementor( String implementor ) {
		this.implementor = implementor;
	}

}
