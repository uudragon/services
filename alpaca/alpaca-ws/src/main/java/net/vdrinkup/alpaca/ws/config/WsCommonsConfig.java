/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.ws.config;

import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.protocol.definition.CommonsConfig;


/**
 * Web Service通用配置
 * <p></p>
 * @author liubing
 * Date Nov 1, 2013
 */
@XmlRootElement( name = "commons" )
public class WsCommonsConfig extends CommonsConfig {
}
