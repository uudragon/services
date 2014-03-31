/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.processor;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.ExpressionNode;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p></p>
 * @author bing.liu
 * Date 2012-2-10
 */
public class ExpressionNodeProcessor implements Processor {

	private static Logger LOG = LoggerFactory.getLogger( ExpressionNodeProcessor.class );

	private ExpressionNode node;

	public ExpressionNodeProcessor( ExpressionNode node ) {
		this.node = node;
	}

	@Override
	public void process( DataContext context ) {
		if ( ! ContextStatus.VALID.equals( context.getStatus() ) ) {
			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "The status of current context is [{}], it will be return.", context.getStatus().name() );
			}
			return;
		}
		boolean isMatch = node.getExpression().matches( context );
		if ( isMatch ) {
			for ( ProcessorDefinition processor : node.getOutputs() ) {
				try {
					processor.createProcessor().process( context );
				} catch ( Exception e ) {
					LOG.error( "Expression execute error.", e );
					context.setStatus( ContextStatus.EXCEPTION );
					context.setException( e );
				}
			}
		}
	}

}
