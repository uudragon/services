/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.protocol;

import net.vdrinkup.alpaca.protocol.definition.ProtocolConfig;

/**
 * 协议配置织入接口
 * <p>
 * 该接口声明用于织入协议配置的方法。
 * </p>
 * @author pluto.bing.liu
 * Date 2013-12-5
 */
public interface ProtocolConfigAware {
	
	public < T extends ProtocolConfig > void setConfig( T config );
	
	public < T extends ProtocolConfig > T getConfig();

}
