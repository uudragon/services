/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 异步流程Worker
 * <p>
 * 用于执行异步流程。
 * </p>
 * 
 * @author pluto.bing.liu Date 2013-12-2
 */
public class AsyncFlowWorker implements FlowWorker, Runnable {
	
	private static Logger LOG = LoggerFactory.getLogger( AsyncFlowWorker.class );

	private final ExecutorService executor;

	private final FlowDefinition definition;

	private DataContext context;
	
	private Future< ? > future;
	
	public AsyncFlowWorker( ExecutorService executor, FlowDefinition definition, DataContext context ) {
		this.definition = definition;
		this.executor = executor;
		this.context = context;
	}

	@Override
	public void run() {
		try {
			definition.createProcessor().process( context );
		} catch ( Exception e ) {
			LOG.error( "Flow [" + definition.getId() + "] run failture.", e );
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
		}
	}

	@Override
	public ExecutorService getExecutor() {
		return executor;
	}

	@Override
	public boolean execute() {
		this.future = this.executor.submit( this );
		return true;
	}

	@Override
	public boolean cancell() {
		return this.future.cancel( true );
	}

	@Override
	public FlowDefinition getDefinition() {
		return this.definition;
	}

	@Override
	public boolean isDone() {
		return future.isDone();
	}

	@Override
	public boolean isCancelled() {
		return future.isCancelled();
	}

}
