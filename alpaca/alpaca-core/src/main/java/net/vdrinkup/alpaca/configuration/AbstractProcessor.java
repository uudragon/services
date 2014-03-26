/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration;

import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *
 * <p></p>
 * @author liubing
 * Date 2013-11-13
 */
public abstract class AbstractProcessor< T extends ProcessorDefinition > implements Processor {
	
	protected static Logger LOG = LoggerFactory.getLogger( AbstractProcessor.class.getPackage().getName() );
	
	protected T definition;
	
	public AbstractProcessor( T t ) {
		this.definition = t;
	}

	public T getDefinition() {
		return definition;
	}

	@Override
	public void process( DataContext context ) throws Exception {
		try {
			handle( context );
		} catch ( Exception e ) {
			LOG.error( "Process error.", e );
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
			throw e;
		}
	}
	
	protected abstract void handle( DataContext context ) throws Exception;

}
