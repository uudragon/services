/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.processor;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.CatchDefinition;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Catch配置处理器
 * <p></p>
 * @author liubing
 * Date Mar 25, 2014
 */
public class CatchProcessor implements Processor {
	
	private static Logger LOG = LoggerFactory.getLogger( CatchProcessor.class );
	
	private CatchDefinition definition;
	
	public CatchProcessor( CatchDefinition definition ) {
		this.definition = definition;
	}

	public CatchDefinition getDefinition() {
		return definition;
	}

	public void setDefinition( CatchDefinition definition ) {
		this.definition = definition;
	}

	@Override
	public void process( DataContext context ) throws Exception {
		if ( ! ContextStatus.EXCEPTION.equals( context.getStatus() ) ) {
			return ;
		}
		final Throwable throwable = context.getException();
		boolean isMatch = false;
		for ( String excepName : getDefinition().getExceptions() ) {
			if ( excepName.equals( throwable.getClass().getName() ) ) {
				isMatch = true;
				break;
			}
		}
		if ( ! isMatch ) {
			LOG.debug( "Current exception can not match this definition" );
			return ;
		}
		LOG.error( "Catch the exception is [{}]", throwable.getClass().getName() );
		context.setStatus( ContextStatus.VALID );
		for ( ProcessorDefinition p : getDefinition().getOutputs() ) {
			p.createProcessor().process( context );
		}
		context.setStatus( ContextStatus.EXCEPTION );
	}

}
