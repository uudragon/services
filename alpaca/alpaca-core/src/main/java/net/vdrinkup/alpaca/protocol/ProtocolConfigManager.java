/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.Manager;
import net.vdrinkup.alpaca.configuration.ConfigProcessor;
import net.vdrinkup.alpaca.protocol.definition.ProtocolConfig;
import net.vdrinkup.alpaca.protocol.plugin.ProtocolPlugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 协议配置管理类
 * <p>
 * 注册当前所有协议实例配置
 * </p>
 * @author liubing
 * Date Oct 28, 2013
 */
public class ProtocolConfigManager implements Manager {
	
	private static Logger LOG = LoggerFactory.getLogger( ProtocolConfigManager.class );
	
	private static volatile ProtocolConfigManager _instance;
	
	private Lock lock = new ReentrantLock();
		
	private ProtocolConfigManager() {
	}
	
	public static ProtocolConfigManager getInstance() {
		if ( _instance == null ) {
			synchronized (  ProtocolConfigManager.class ) {
				if ( _instance == null ) {
					_instance = new ProtocolConfigManager();
				}
			}
		}
		return _instance;
	}
	
	private Map< String, ProtocolConfig > registry = new ConcurrentHashMap< String, ProtocolConfig >( 16 );

	private void loadAll() {
		final File configDir = new File( Env.getConfPath() + "protocol/configuration" );
		if ( ! configDir.exists() ) {
			return ;
		}
		final File[] files = configDir.listFiles();
		final XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader xsr = null;
		String protocol = null;
		FileInputStream fis = null;
		ProtocolConfig config = null;
		for ( File file : files ) {
			try {
				fis = new FileInputStream( file );
				xsr = xif.createXMLStreamReader( fis );
				if ( ! xsr.hasNext() ) {
					continue ;
				}
				xsr.next();
				protocol = xsr.getLocalName();
			} catch ( Exception e ) {
				LOG.error( e.getMessage(),  e );
				continue ;
			} finally {
				if ( xsr != null ) {
					try {
						xsr.close();
					} catch ( XMLStreamException e ) {
						LOG.error( e.getMessage(),  e );
					}
					xsr = null;
				}
			}
			try {
				fis = new FileInputStream( file );
				config = readProtocol( protocol, fis );
				String id = file.getName().substring( 0, file.getName().lastIndexOf( "." ) );
				config.setName( id );
				ProtocolInstanceManager.getInstance().load( config );
				registry.put( id, config );
			} catch ( Exception e ) {
				e.printStackTrace();
				LOG.error( e.getMessage(), e );
				continue ;
			} finally {
				if ( fis != null ) {
					try {
						fis.close();
					} catch ( IOException e ) {
						e.printStackTrace();
					}
					fis = null;
				}
			}
		}
	}

	/**
	 * @param tagName
	 * @param xsr
	 */
	private ProtocolConfig readProtocol( String protocol, InputStream is ) throws Exception {
		if ( protocol == null || "".equals( protocol ) ) {
			throw new IllegalArgumentException( "The prtocol string can not be null." );
		}
		protocol = protocol.substring( 
				protocol.lastIndexOf( "." ) + 1, protocol.length() );
		final ProtocolPlugin plugin = ProtocolPluginManager.getInstance().lookup( protocol );
		final ConfigProcessor processor = plugin.getConfigProcessor();
		final ProtocolConfig config = processor.read( is );
		config.setProtocolName( protocol );
		return config;
	}

	/**
	 * 
	 */
	public Map< String, ProtocolConfig > getRegistry() {
		return this.registry;
	}
	
	public ProtocolConfig lookup( String key ) throws Exception {
		if ( isShutdown() ) {
			LOG.warn( "ProtocolConfigManager has been stopped.It will be restarted." );
			start();
		}
		return this.registry.get( key );
	}
	
	private boolean running;

	@Override
	public void start() throws Exception {
		if ( lock.tryLock() ) {
			try {
				if ( isStartup() ) {
					LOG.warn( "ProtocolConfigManager has been started." );
					return ;
				}
				loadAll();
				running = true;
			} finally {
				lock.unlock();
			}
		}
	}

	@Override
	public void stop() throws Exception {
		if ( lock.tryLock() ) {
			try {
				if ( isShutdown() ) {
					LOG.warn( "ProtocolConfigManager has been stopped." );
					return ;
				}
				this.registry.clear();
				this.registry = null;
				running = false;
			} finally {
				lock.unlock();
			}
		}
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
