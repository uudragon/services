/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.support.flow;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.configuration.model.Mode;
import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.context.DataContextFactory;
import net.vdrinkup.alpaca.flow.AsyncFlowWorker;
import net.vdrinkup.alpaca.flow.FlowDefinition;
import net.vdrinkup.alpaca.flow.FlowEngine;
import net.vdrinkup.alpaca.flow.FlowWorker;
import net.vdrinkup.alpaca.flow.SyncFlowWorker;
import net.vdrinkup.alpaca.flow.identific.IdentificManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 流程引擎的默认实现
 * <p></p>
 * @author liubing
 * Date Nov 10, 2013
 */
public class DefaultFlowEngine implements FlowEngine {
	
	private static Logger LOG = LoggerFactory.getLogger( DefaultFlowEngine.class );
	
	private volatile ThreadPoolExecutor threadPool;
	
	private String name;
	
	private int threadSize;
	
	private volatile boolean running = false;
		
	public DefaultFlowEngine() {
	}

	@Override
	public DataContext createnNewContext() {
		final DataContext context = DataContextFactory.INSTANCE.create();
		return context;
	}

	@Override
	public void incoming( DataContext context ) {
		IdentificManager.getInstance().identify( context );
		if ( ContextStatus.EXCEPTION.equals( context.getStatus() ) ) {
			//TODO process exception
		}
		final Mode mode = context.getProperty( ContextConstants.EXECUTE_MODE, Mode.class );
		final FlowDefinition flow = context.getProperty( ContextConstants.FLOW, FlowDefinition.class );
		context.setProperty( ContextConstants.TIMEOUT, 
				Env.getInstance().getProperty( Env.Keys.EXECUTE_TIMEOUT, Integer.class ) );
		FlowWorker worker = null;
		if ( Mode.ASYNC.equals( mode ) ) {
			worker = new AsyncFlowWorker( threadPool, flow, context );
		} else if ( Mode.PSEUDO.equals( mode ) ) {
			//TODO process pseudo
		} else {
			worker = new SyncFlowWorker( threadPool, flow, context );
		}
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Flow [{}] working...", worker.getDefinition().getId() );
		}
		final long beginTimestamp = System.currentTimeMillis();
//		final boolean completed = worker.execute();
		worker.execute();
		final long endTimestamp = System.currentTimeMillis();
		LOG.info( "Execute context [{}] used [{}ms]", context.getId(), ( endTimestamp - beginTimestamp ) );
//		if ( ! completed ) {
//		}
	}

	@Override
	public synchronized void start() {
		if ( isStartup() ) {
			return ;
		}
		if ( threadPool == null ) {
			threadPool = ( ThreadPoolExecutor ) Executors.newFixedThreadPool( 10, new ThreadFactory() {
				private int count = 0;
				private ThreadGroup group = new ThreadGroup( getName() + "-ThreadPool"  );
				@Override
				public Thread newThread( Runnable r ) {
					return new Thread( group, r, new StringBuilder( 
							group.getName() ).append("-" ).append( count ++ ).toString() );
				}
			} );
		}
		running = true;
	}

	@Override
	public synchronized void stop() {
		if ( isShutdown() ) {
			return ;
		}
		running = false;
		if ( threadPool != null ) {
			threadPool.shutdown();
			try {
				if ( ! threadPool.awaitTermination( Env.getInstance().getProperty( Env.Keys.ENGINE_SHUTDOWN_TIMEOUT, int.class ), TimeUnit.MILLISECONDS ) ) {
					threadPool.shutdownNow();
				}
			} catch ( InterruptedException e ) {
				LOG.error( e.getMessage(), e );
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

	@Override
	public int getThreadSize() {
		return this.threadSize;
	}

	@Override
	public void setThreadSize( int size ) {
		this.threadSize = size;
		resetThreadPool();
	}

	private void resetThreadPool() {
		if ( threadPool == null ) {
			return ;
		}
		threadPool.setCorePoolSize( threadSize );
	}

	@Override
	public String getName() {
		return name;
	}

}
