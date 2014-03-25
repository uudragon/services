/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.protocol;

import java.io.File;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.Manager;
import net.vdrinkup.alpaca.protocol.plugin.ProtocolPlugin;
import net.vdrinkup.alpaca.protocol.plugin.ProtocolPlugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 通讯插件管理器
 * <p>
 * 用于管理通讯插件。例如：TCP， WS，HTTP等
 * </p>
 * @author liubing
 * Date Nov 1, 2013
 */
public class ProtocolPluginManager implements Manager {
	
	private static Logger LOG = LoggerFactory.getLogger( ProtocolPluginManager.class );

	private static volatile ProtocolPluginManager _instance;
	
	private boolean running;
	
	private ProtocolPluginManager() {
	}
	
	public static ProtocolPluginManager getInstance() {
		if ( _instance == null ) {
			synchronized ( ProtocolPluginManager.class ) {
				if ( _instance == null ) {
					_instance = new ProtocolPluginManager();
				}
			}
		}
		return _instance;
	}

	@Override
	public synchronized void start() throws Exception {
		if ( isStartup() ) {
			LOG.warn( "ProtoclPluginManager has been started." );
			return ;
		}
		StringBuilder plugin_conf_path = new StringBuilder( Env.getConfPath() );
		plugin_conf_path.append( "protocol" ).append( File.separator ).append( "plugins.xml" );
		final File plugin_conf_file = new File( plugin_conf_path.toString() );
		final JAXBContext context = JAXBContext.newInstance( ProtocolPlugins.class.getPackage().getName(), 
				this.getClass().getClassLoader());
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		this.plugins = ( ProtocolPlugins ) unmarshaller.unmarshal( plugin_conf_file );
		running = true;
	}

	public Map< String, ProtocolPlugin > plugins;
	
	public ProtocolPlugin lookup( String key ) throws Exception {
		if ( isShutdown() ) {
			LOG.warn( "ProtocolPluginManager has been stopped.Now will be restarted." );
			start();
		}
		if ( ! plugins.containsKey( key ) ) {
			throw new NoSuchElementException( "No such plugin named [" + key + "]." );
		}
		return plugins.get( key );
	}

	@Override
	public synchronized void stop() throws Exception {
		if ( isShutdown() ) {
			LOG.error( "ProtocolPluginManager has been stopped." );
			return ;
		}
		running = false;
		this.plugins.clear();
		this.plugins = null;
	}

	@Override
	public boolean isStartup() {
		return running;
	}

	@Override
	public boolean isShutdown() {
		return ! running;
	}
	
}
