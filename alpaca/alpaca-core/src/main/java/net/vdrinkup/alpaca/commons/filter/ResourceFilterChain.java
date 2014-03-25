/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.commons.filter;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 25, 2013
 */
public interface ResourceFilterChain {
	
	public < T, R > R doFilter( T t ) throws Exception;

	
}
