/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.ws.config;

import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.protocol.definition.IContentType;
import net.vdrinkup.alpaca.protocol.definition.ResponseConfig;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 2, 2013
 */
@XmlRootElement( name = "response" )
public class WsResponseConfig extends ResponseConfig {

	@Override
	public IContentType getContentType() {
		return null;
	}

	@Override
	public void setContentType(IContentType contentType) {		
	}

}
