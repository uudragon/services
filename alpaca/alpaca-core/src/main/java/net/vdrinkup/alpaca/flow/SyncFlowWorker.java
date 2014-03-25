/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 同步流程Worker
 * <p>
 * 该Worker用于执行同步流程，
 * </p>
 * @author liubing
 * Date Nov 10, 2013
 */
public class SyncFlowWorker implements FlowWorker, Runnable  {
	
	private static Logger LOG = LoggerFactory.getLogger( SyncFlowWorker.class );
		
	private final ExecutorService executor;
	
	private final FlowDefinition definition;
	
	private Future< ? > future;
	
	private DataContext context;
	
	private SynchronousQueue< Boolean > queue = new SynchronousQueue< Boolean >();
	
	public SyncFlowWorker( ExecutorService executor, FlowDefinition definition, DataContext context ) {
		this.definition = definition;
		this.executor = executor;
		this.context = context;
	}

	@Override
	public boolean execute(){
		this.future = executor.submit( this );
		boolean completed = false;
		try {
			completed = queue.poll( context.getProperty( ContextConstants.TIMEOUT, Integer.class), TimeUnit.MILLISECONDS );
		} catch ( Exception e ) {
			LOG.error( "Context [{}] timeout.", context.getId(), e );
			if ( ! future.isDone() ) {
				LOG.error( "The execution of context [{}]  will be cancell.", context.getId() );
				cancell();
			}
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
		}
		return completed;
	}

	@Override
	public boolean cancell() {
		return this.future.cancel( true );
	}

	@Override
	public ExecutorService getExecutor() {
		return executor;
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

	@Override
	public void run() {
		boolean success = false;
		try {
			definition.createProcessor().process( context );
			success = true;
		} catch ( Exception e ) {
			LOG.error( "Flow [" + definition.getId() + "] run failture.", e );
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
		}
		queue.offer( success );
	}

}
