/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.processor;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.messageset.MessageNode;

/**
 * Json报文编码器接口
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-4
 */
public interface JsonEncoder {
	/**
	 * Json报文编码方法
	 * @param context
	 * @param definitions
	 */
	public void encode( DataContext context, MessageNode definition ) throws Exception;

}
