/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.context;

import net.vdrinkup.alpaca.HelperProvider;

/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-24
 */
public interface DataContextFactory {
	
	DataContextFactory INSTANCE = HelperProvider.INSTANCE.getContextFactory();

	public DataContext create();

}
