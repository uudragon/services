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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.SchemeConstants;
import net.vdrinkup.alpaca.flow.FlowConfigProcessor;
import net.vdrinkup.alpaca.flow.FlowDefinition;
import net.vdrinkup.alpaca.flow.FlowExistException;
import net.vdrinkup.alpaca.flow.FlowManager;
import net.vdrinkup.alpaca.flow.FlowNotFoundException;


/**
 * 默认的流程管理器
 * <p>
 * 流程管理器的默认实现，提供对流程配置的查找，注册，更新，删除等管理操作。
 * </p>
 * @author liubing Date Nov 26, 2013
 */
public class DefaultFlowManager implements FlowManager {

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
		final File dir = new File( Env.getConfPath()
				.concat( "flow" ) );
		final File[] files = dir.listFiles();
		FileInputStream fis = null;
		FlowDefinition definition = null;
		for ( File file : files ) {
			try {
				fis = new FileInputStream( file );
				definition = configProcessor.read( fis );
				String id = file.getName().substring( 0,
						file.getName().indexOf( "." ) );
				id = SchemeConstants.Prefix.FLOW_PREFIX.concat( id );
				definition.setId( id );
				this.register( definition );
			} catch ( Exception e ) {
				e.printStackTrace();
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
