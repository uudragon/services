/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.support.context;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.context.DataContextFactory;

/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-24
 */
public class DefaultDataContextFactory implements DataContextFactory {

	@Override
	public DataContext create() {
		return new DefaultDataContext();
	}

}
