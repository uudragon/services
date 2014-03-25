/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration;

import net.vdrinkup.alpaca.context.DataContext;


/**
 * 配置处理器接口
 * <p></p>
 * @author liubing
 * Date 2013-11-13
 */
public interface Processor {
	
	public void process( DataContext context );

}
