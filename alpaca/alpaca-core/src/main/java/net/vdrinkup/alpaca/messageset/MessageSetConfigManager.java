/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.Manager;
import net.vdrinkup.alpaca.configuration.ConfigProcessor;
import net.vdrinkup.alpaca.messageset.definition.MessageDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 25, 2013
 */
public class MessageSetConfigManager implements Manager {
	
	private static final String NAME_SEP = "_";

	private static Logger LOG = LoggerFactory.getLogger( MessageSetConfigManager.class );
		
	private boolean running = false;
	
	private ReentrantLock lock = new ReentrantLock();
	
	private static MessageSetConfigManager _instance;
	
	private MessageSetConfigManager() {
	}
	
	public static MessageSetConfigManager getInstance() {
		if ( _instance == null ) {
			synchronized ( MessageSetConfigManager.class ) {
				if ( _instance == null ) {
					_instance = new MessageSetConfigManager();
				}
			}
		}
		return _instance;
	}

	private Map< String, MessageDefinition > registry = new ConcurrentHashMap< String, MessageDefinition >();
	
	@Override
	public void start() throws Exception {
		if ( lock.tryLock() ) {
			try {
				if ( isStartup() ) {
					LOG.warn( "MessageSetConfigManager has been started." );
					return ;
				}
				loadAll();
				running = true;
			} finally {
				lock.unlock();
			}
		}
	}
	
	private void loadAll() {
		final String confPath = Env.getConfPath().concat( "messageset" ).concat( File.separator ).concat( "configuration" );
		final File dir = new File( confPath );
		if ( ! dir.exists() ) {
			LOG.warn( "No config file be found in {}", confPath );
			return ;
		}
		final File[] files = dir.listFiles();
		final XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader xsr = null;
		ConfigProcessor processor = null;
		for ( File file : files ) {
			try {
				xsr = xif.createXMLStreamReader( file.toURI().toURL().openStream() );
				xsr.next();
				String type = xsr.getAttributeValue( null, "type" );
				processor = MessageSetExtRegistry.getInstance().lookup( type );
			} catch ( Exception e ) {
				LOG.error( "Load the configuration [{}] error.", file.getName(), e );
				continue ;
			} finally {
				if ( xsr != null ) {
					try {
						xsr.close();
					} catch ( XMLStreamException e ) {
					}
					xsr = null;
				}
			}
			if ( processor == null ) {
				continue ;
			}
			FileInputStream fis = null;
			try {
				fis = new FileInputStream( file );
				MessageDefinition definition = processor.read( fis );
				StringBuilder keyBuff = new StringBuilder( definition.getFrom() );
				keyBuff.append( NAME_SEP ).append( definition.getTo() );
				registry.put( keyBuff.toString(), definition );
				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "MessageConfig [{}] has benn register", keyBuff.toString() );
				}
			} catch ( Exception e ) {
				LOG.error( "Load the configuration [{}] error.", file.getName(), e );
			} finally {
				if ( fis != null ) {
					try {
						fis.close();
					} catch ( IOException e ) {
					}
					fis = null;
				}
			}
		}
	}

	@Override
	public void stop() throws Exception {
		if ( lock.tryLock() ) {
			try {
				if ( isShutdown() ) {
					LOG.warn( "MessageSetConfigManager has been stopped." );
					return ;
				}
				
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
	
	/**
	 * 按给定的Key查找对应的报文配置定义
	 * @param key
	 * @return
	 */
	public MessageDefinition lookup( String key ) {
		MessageDefinition definition = registry.get( key );
		if ( definition == null ) {
			LOG.warn( "No such messageset definition named [" + key + "]" );
		}
		return definition;
	}

}
