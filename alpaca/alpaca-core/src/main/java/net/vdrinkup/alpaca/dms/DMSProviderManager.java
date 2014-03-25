/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.dms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.Manager;
import net.vdrinkup.alpaca.configuration.ConfigProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 数据中介提供者管理器
 * <p></p>
 * @author liubing
 * Date Feb 12, 2014
 */
public class DMSProviderManager implements Manager {
	
	private static Logger LOG = LoggerFactory.getLogger( DMSProviderManager.class );
		
	private static volatile DMSProviderManager _instance;
	
	private DMSProviderManager() {
	}
	
	public static DMSProviderManager getInstance() {
		if ( _instance == null ) {
			synchronized ( DMSProviderManager.class ) {
				if ( _instance == null ) {
					_instance = new DMSProviderManager();
				}
			}
		}
		return _instance;
	}
	
	private Map< String, Provider > registry = new ConcurrentHashMap< String, Provider >( 16 );

	private void loadAll() {
		final File configDir = new File( Env.getConfPath() + "dms" );
		if ( ! configDir.exists() ) {
			return ;
		}
		final File[] files = configDir.listFiles();
		final XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader xsr = null;
		String dms = null;
		FileInputStream fis = null;
		Provider provider = null;
		for ( File file : files ) {
			try {
				fis = new FileInputStream( file );
				xsr = xif.createXMLStreamReader( fis );
				if ( ! xsr.hasNext() ) {
					continue ;
				}
				xsr.next();
				dms = xsr.getLocalName();
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
				provider = initProvider( dms, fis );
				String id = file.getName().substring( 0, file.getName().lastIndexOf( "." ) );
				provider.getDataSourceDefinition().setName( id );
				
				registry.put( id, provider );
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
	
	private Provider initProvider( String name, InputStream is ) throws Exception {
		if ( name == null || "".equals( name ) ) {
			throw new IllegalArgumentException( "The prtocol string can not be null." );
		}
		name = name.substring( 
				name.lastIndexOf( "." ) + 1, name.length() );
		final DMSExpander expander = DMSProviderExtRegistry.getInstance().lookup( name );
		final ConfigProcessor processor = expander.getConfigProcessor();
		final DataSourceDefinition definition = processor.read( is );
		Provider provider = expander.getProviderClazz().newInstance();
		provider.setDataSourceDefinition( definition );
		provider.start();
		return provider;
	}
	
	private Lock lock = new ReentrantLock();
	
	private boolean running;

	@Override
	public void start() throws Exception {
		if ( lock.tryLock() ) {
			try {
				if ( isStartup() ) {
					LOG.warn( "DMSProviderManager has been started." );
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
					LOG.warn( "DMSProviderManager has been stopped." );
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

	public Provider lookup( String providerName ) {
		Provider provider = registry.get( providerName );
		if ( providerName == null ) {
			throw new NoSuchElementException( "Can not found DMS provider named '" + providerName + "'" );
		}
		return provider;
	}

}
