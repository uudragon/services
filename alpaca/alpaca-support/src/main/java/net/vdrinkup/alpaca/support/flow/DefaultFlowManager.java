/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.support.flow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.commons.resource.ResourceFilter;
import net.vdrinkup.alpaca.commons.resource.ResourceScanner;
import net.vdrinkup.alpaca.commons.resource.impl.FileResourceScanner;
import net.vdrinkup.alpaca.flow.FlowConfigProcessor;
import net.vdrinkup.alpaca.flow.FlowDefinition;
import net.vdrinkup.alpaca.flow.FlowExistException;
import net.vdrinkup.alpaca.flow.FlowManager;
import net.vdrinkup.alpaca.flow.FlowNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 默认的流程管理器
 * <p>
 * 流程管理器的默认实现，提供对流程配置的查找，注册，更新，删除等管理操作。
 * </p>
 * @author liubing Date Nov 26, 2013
 */
public class DefaultFlowManager implements FlowManager {
	
	private static Logger LOG = LoggerFactory.getLogger( DefaultFlowManager.class );

	private boolean running = false;

	private Map< String, FlowDefinition > registry = new ConcurrentHashMap< String, FlowDefinition >();

	private final FlowConfigProcessor configProcessor = new FlowConfigProcessor();

	@Override
	public void register( FlowDefinition definition ) {
		if ( registry.containsKey( definition.getId() ) ) {
			throw new FlowExistException( "The flow named ["
					+ definition.getId() + "] exist." );
		}
		registry.put( definition.getId(), definition );
	}

	@Override
	public FlowDefinition unregister( String id ) {
		return registry.remove( id );
	}

	@Override
	public FlowDefinition lookup( String id ) {
		if ( !registry.containsKey( id ) ) {
			throw new FlowNotFoundException( "The flow named [" + id
					+ "] can not be found." );
		}
		return registry.get( id );
	}

	@Override
	public synchronized void start() {
		if ( isStartup() ) {
			return;
		}
		final File dir = new File( Env.getConfPath().concat( "flow" ) );
		final List< File > files = new LinkedList< File >();
		final ResourceScanner scanner = new FileResourceScanner();
		scanner.scan( dir, new ResourceFilter() {

			@Override
			public < T, R > R doFilter( T t ) throws Exception {
				File file = ( File ) t;
				if ( file.getName().endsWith( ".xml" ) ) {
					files.add( file );
				}
				return null;
			}
			
		} );
		FileInputStream fis = null;
		FlowDefinition definition = null;
		for ( File file : files ) {
			try {
				fis = new FileInputStream( file );
				definition = configProcessor.read( fis );
				String id = file.getName().substring( 0,
						file.getName().indexOf( "." ) );
				definition.setId( id );
				this.register( definition );
				LOG.info( "Flow [{}] has been register successfully.", id );
			} catch ( Exception e ) {
				LOG.error( e.getMessage(), e );
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
		running = true;
	}

	@Override
	public synchronized void stop() {
		if ( isShutdown() ) {
			return;
		}
		registry.clear();
		running = false;
	}

	@Override
	public boolean isStartup() {
		return running;
	}

	@Override
	public boolean isShutdown() {
		return !running;
	}

}
