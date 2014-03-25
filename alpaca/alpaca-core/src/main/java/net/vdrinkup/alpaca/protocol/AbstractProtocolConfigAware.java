/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.protocol;

import net.vdrinkup.alpaca.protocol.definition.ProtocolConfig;

/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2013-12-5
 */
public class AbstractProtocolConfigAware implements ProtocolConfigAware {

	protected ProtocolConfig config;

	@Override
	public < T extends ProtocolConfig > void setConfig( T config ) {
		this.config = config;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends ProtocolConfig > T getConfig() {
		return ( T ) this.config;
	}
	
	

}
