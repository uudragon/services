/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration;

/**
 * 断言接口
 * <p></p>
 * @author bing.liu
 * Date 2012-2-9
 */
public interface Predicate {

	public boolean matches( Object ctx );

}
