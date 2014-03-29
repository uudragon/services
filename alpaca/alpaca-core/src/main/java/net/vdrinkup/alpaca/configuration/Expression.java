/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;

/**
 * 表达式接口
 * <p></p>
 * @author bing.liu
 * Date 2012-2-9
 */
public interface Expression {

	public < T > T evaluate( DataObject dataObject, DataContext context, Class< T > type );

}
