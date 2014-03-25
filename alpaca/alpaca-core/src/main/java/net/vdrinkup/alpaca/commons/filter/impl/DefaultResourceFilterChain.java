/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.commons.filter.impl;

import java.util.Iterator;
import java.util.List;

import net.vdrinkup.alpaca.commons.filter.ResourceFilter;
import net.vdrinkup.alpaca.commons.filter.ResourceFilterChain;


/**
 * 默认的资源过滤器链
 * @author liubing
 * Date Nov 25, 2013
 */
public class DefaultResourceFilterChain implements ResourceFilterChain {
		
	private Iterator< ResourceFilter > iter;
	
	public DefaultResourceFilterChain( List< ResourceFilter > filters ) {
		iter = filters.iterator();
	}

	@Override
	public < T, R > R doFilter( T t ) throws Exception {
		R result = null;
		if ( iter.hasNext() ) {
			result = iter.next().doFilter( t, this );
		}
		return result;
	}

}
