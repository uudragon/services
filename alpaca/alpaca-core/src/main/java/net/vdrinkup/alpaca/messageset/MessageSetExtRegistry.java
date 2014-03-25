/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset;

import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import net.vdrinkup.alpaca.configuration.ConfigProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 数据集扩展管理器
 * <p>
 * 用于管理数据集扩展插件，数据集的扩展插件用于对不同数据集格式进行处理。
 * 数据集扩展插件会在所在JAR文件的META-IN/services目录下包含com.jd.wms.messageset.ext文件。
 * com.jd.wms.messageset.ext为.properties文件，其内容如下：
 *  <example> 
 *  ext.name=当前扩展插件的名称
 * 	config.processor=当前扩展插件用于解析数据及配置的处理类 
 * </example>
 * </p>
 * 
 * @author liubing Date Dec 26, 2013
 */
public class MessageSetExtRegistry {

	private static Logger LOG = LoggerFactory
			.getLogger( MessageSetExtRegistry.class );

	private static final String RESOURCE_NAME = "META-INF/services/net.vdrinkup.alpaca.messageset.ext";

	private static final String EXT_NAME = "ext.name";
	
	private static final String EXT_CONFIG_PROCESSOR = "config.processor";

	private static MessageSetExtRegistry _instance;

	private MessageSetExtRegistry() {
	}

	public static MessageSetExtRegistry getInstance() {
		if ( _instance == null ) {
			synchronized ( MessageSetExtRegistry.class ) {
				if ( _instance == null ) {
					_instance = new MessageSetExtRegistry();
					try {
						_instance.init();
					} catch ( Exception e ) {
						LOG.error( "MessageSetExtRegistry initialized unsuccessfully.", e );
					}
				}
			}
		}
		return _instance;
	}

	/**
	 * 
	 */
	@SuppressWarnings( "unchecked" )
	private void init() throws Exception {
		// Init messageset ext plugin
		final Enumeration< URL > urls = _instance.getClass().getClassLoader()
				.getResources( RESOURCE_NAME );
		URL url = null;
		Properties properties = null;
		while ( urls.hasMoreElements() ) {
			url = urls.nextElement();
			properties = new Properties();
			properties.load( url.openStream() );
			String extName = properties.getProperty( EXT_NAME );
			String className = properties.getProperty( EXT_CONFIG_PROCESSOR );
			Class< ConfigProcessor > clazz = ( Class< ConfigProcessor > ) Class.forName( className, false, this.getClass().getClassLoader() );
			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "Current extName is {}, ConfigProcessor is {}", extName, clazz.getName() );
			}
			registry.put( extName, clazz.newInstance() ); 
		}
	}
	
	private Map< String, ConfigProcessor > registry = new ConcurrentHashMap< String, ConfigProcessor >( 16 );

	/**
	 * 按扩展插件名称查找对应的配置解析处理器。
	 * 如果当前管理器已经关闭或者不包含所给定的扩展名称对应的处理器，则返回Null
	 * @param extName
	 * @return
	 */
	public ConfigProcessor lookup( String extName ) {
		ConfigProcessor processor = null;
		if ( registry.containsKey( extName ) ) {
			processor = registry.get( extName );
		}
		return processor;
	}

}
