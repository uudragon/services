/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.dms;

import net.vdrinkup.alpaca.configuration.ConfigProcessor;

/**
 * DMS扩展器
 * <p></p>
 * @author liubing
 * Date Feb 12, 2014
 */
public class DMSExpander {
	
	private ConfigProcessor configProcessor;
	
	private Class< Provider > providerClazz;

	public ConfigProcessor getConfigProcessor() {
		return configProcessor;
	}

	public void setConfigProcessor( ConfigProcessor configProcessor ) {
		this.configProcessor = configProcessor;
	}

	public Class< Provider > getProviderClazz() {
		return providerClazz;
	}

	public void setProviderClazz( Class< Provider > providerClazz ) {
		this.providerClazz = providerClazz;
	}

	public String toString() {
		return new StringBuilder( ", ConfigProcessor is " )
				.append( configProcessor.getClass().getName() )
				.append( ", Provider is " )
				.append( providerClazz.toString() ).toString();
	}

}
