/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.dms;

import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import net.vdrinkup.alpaca.configuration.ConfigProcessor;
import net.vdrinkup.alpaca.messageset.MessageSetExtRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * DMS扩展注册器
 * <p>
 * 该类用于管理DMSProvider扩展器
 * </p>
 * @author liubing
 * Date Feb 12, 2014
 */
public class DMSProviderExtRegistry {
	
	private static Logger LOG = LoggerFactory
			.getLogger( DMSProviderExtRegistry.class );

	private static final String RESOURCE_NAME = "META-INF/services/net.vdrinkup.alpaca.dms.ext";

	private static final String EXT_NAME = "name";
	
	private static final String EXT_CONFIG_PROCESSOR = "config.processor";
	
	private static final String EXT_DMS_PROVIDER = "provider";

	private static DMSProviderExtRegistry _instance;

	private DMSProviderExtRegistry() {
	}

	public static DMSProviderExtRegistry getInstance() {
		if ( _instance == null ) {
			synchronized ( MessageSetExtRegistry.class ) {
				if ( _instance == null ) {
					_instance = new DMSProviderExtRegistry();
					try {
						_instance.init();
					} catch ( Exception e ) {
						LOG.error( "MessageSetExtRegistry initialized unsuccessfully." );
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
		// Init DMS Expander
		final Enumeration< URL > urls = _instance.getClass().getClassLoader()
				.getResources( RESOURCE_NAME );
		URL url = null;
		Properties properties = null;
		while ( urls.hasMoreElements() ) {
			url = urls.nextElement();
			properties = new Properties();
			properties.load( url.openStream() );
			String extName = properties.getProperty( EXT_NAME );
			String configPClassName = properties.getProperty( EXT_CONFIG_PROCESSOR );
			String providerClassName = properties.getProperty( EXT_DMS_PROVIDER );
			Class< ConfigProcessor > configPClazz = ( Class< ConfigProcessor > ) Class.forName( configPClassName, false, this.getClass().getClassLoader() );
			Class< Provider > providerClazz = ( Class< Provider > ) Class.forName( providerClassName, false, this.getClass().getClassLoader() );
			DMSExpander expander = new DMSExpander();
			expander.setConfigProcessor( configPClazz.newInstance() );
			expander.setProviderClazz( providerClazz );
			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "-------------------\nExtName is {}, {}-------------------\n", extName, expander.toString() );
			}
			registry.put( extName, expander ); 
		}
	}
	
	private Map< String, DMSExpander > registry = new ConcurrentHashMap< String, DMSExpander >( 16 );

	/**
	 * 按扩展插件名称查找对应的扩展器。
	 * @param extName
	 * @return
	 */
	public DMSExpander lookup( String extName ) {
		DMSExpander expander = null;
		if ( registry.containsKey( extName ) ) {
			expander = registry.get( extName );
		}
		return expander;
	}

}
