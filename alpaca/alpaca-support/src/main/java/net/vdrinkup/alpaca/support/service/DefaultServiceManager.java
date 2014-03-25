/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.support.service;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.service.KeyType;
import net.vdrinkup.alpaca.service.Service;
import net.vdrinkup.alpaca.service.ServiceEntry;
import net.vdrinkup.alpaca.service.ServiceManager;
import net.vdrinkup.alpaca.service.definition.ServiceDefinition;
import net.vdrinkup.alpaca.service.definition.ServiceSetDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 默认服务管理器
 * <p>
 * 服务管理器接口{@link ServiceManager}的一般实现。
 * </p>
 * 
 * @author liubing Date 2013-11-19
 */
public class DefaultServiceManager implements ServiceManager {
	
	private static Logger LOG = LoggerFactory.getLogger( DefaultServiceManager.class );
	
	private Lock lock = new ReentrantLock();
	
	private boolean running = false;
	
	/**
	 * 服务注册表
	 */
	private Map< String, ServiceEntry > registry = new ConcurrentHashMap< String, ServiceEntry >();

	@Override
	public Collection< ServiceEntry > entries() {
		return registry.values();
	}

	@Override
	public ServiceEntry unregister( String key, KeyType type ) {
		return null;
	}

	@Override
	public ServiceEntry lookup( String id ) {
		return registry.get( id );
	}

	@Override
	public void bind( String id, String callerId ) {
		lookup( id ).addCaller( callerId );
	}

	@Override
	public void unbind( String id, String callerId ) {
		lookup( id ).removeCaller( callerId );
	}

	@Override
	public List< String > showBind( String id ) {
		return lookup( id ).getCallers();
	}

	@Override
	public void register( ServiceEntry entry ) {
		registry.put( entry.getId(), entry );
	}

	@Override
	public void start() throws Exception {
		if ( lock.tryLock() ) {
			try {
				if ( isStartup() ) {
					LOG.warn( "ServiceManager has been started." );
					return ;
				}
				load();
				running = true;
				LOG.info( "ServiceManager started successfully." );
			} finally {
				lock.unlock();
			}
		}
		
	}
	
	private void load() throws Exception {
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "The services definition will be registered..." );
		}
		final JAXBContext context = JAXBContext.newInstance( 
				ServiceSetDefinition.class.getPackage().getName(), this.getClass().getClassLoader() );
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		final String fileName = Env.getConfPath().concat( "services" ).concat( File.separator ).concat( "service-set.xml" );
		final File file = new File( fileName );
		final ServiceSetDefinition serviceSet = ( ServiceSetDefinition ) unmarshaller.unmarshal( file );
		for ( ServiceDefinition service : serviceSet.getServices() ) {
			ServiceEntry entry = new ServiceEntry();
			entry.setDefinition( service );
			entry.setId( service.getName().concat( 
					service.getVersion() == null ? "" : ServiceEntry.Separator.VERSION_SEPARATOR + service.getVersion() ) );
			Class< ? > clazz = this.getClass().getClassLoader().loadClass( service.getClazz() );
			if ( Service.class.isAssignableFrom( clazz ) ) {
				entry.setInstance( ( Service ) clazz.newInstance() );
			} else {
				LOG.error( "The service [{}] is not a subclass of " + Service.class.getName() );
				continue ;
			}
			this.register( entry );
			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "The service [{}] which instance of [{}] has been registry.", entry.getId(), entry.getDefinition().getClazz() );
			}
		}
	}

	@Override
	public synchronized void stop() {
		if ( lock.tryLock() ) {
			try {
				if ( isShutdown() ) {
					LOG.warn( "ServiceManager has been stopped." );
					return ;
				}
				unload();
				running = false;
				LOG.info( "ServiceManager stopped successfully." );
			} finally {
				lock.unlock();
			}
		}
	}

	private void unload() { 
		this.registry.clear();
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
