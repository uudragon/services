/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration;

import java.io.InputStream;
import java.io.OutputStream;

import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;
import net.vdrinkup.alpaca.configuration.model.language.ExpressionDefinition;


/**
 * 配置处理器接口
 * <p></p>
 * @author pluto.bing.liu
 * Date 2013-12-4
 */
public interface ConfigProcessor {
	
	public static final String CONFIG_CONTEXT_PATH = 
			AbstractDefinition.class.getPackage().getName()
			.concat( ":" )
			.concat( ExpressionDefinition.class.getPackage().getName() );
	
	/**
	 * 读取配置
	 * <p>
	 * 从传入的输入流中读取配置信息封装成配置对象
	 * </p>
	 * @param is 配置对应输入流
	 * @return 对应的配置对象
	 * @throws Exception
	 */
	public < T extends AbstractDefinition > T read( InputStream is ) throws Exception;
	
	/**
	 * 写入配置
	 * <p>
	 * 将配置对象写入对应的输出流
	 * </p>
	 * @param config 配置对象
	 * @param 对应的输出流
	 * @throws Exception
	 */
	public < T extends AbstractDefinition > void write( T config, OutputStream os ) throws Exception;

}
