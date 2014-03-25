/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.ws.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.protocol.definition.IContentType;
import net.vdrinkup.alpaca.protocol.definition.RequestConfig;



/**
 * Web Service请求配置类
 * <p></p>
 * @author liubing
 * Date Nov 1, 2013
 */
@XmlRootElement( name = "request" )
public class WsRequestConfig extends RequestConfig {
	@XmlElement
	private WsEndpointConfig endpoint;

	public WsEndpointConfig getEndpoint() {
		return endpoint;
	}

	public void setEndpoint( WsEndpointConfig endpoint ) {
		this.endpoint = endpoint;
	}

	@Override
	public IContentType getContentType() {
		return null;
	}

	@Override
	public void setContentType(IContentType contentType) {
	}

}
