/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.sql.definition.SQLExpressionNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-1-29
 */
public class SQLExpressionProcessor implements Processor {
	
	private static Logger LOG = LoggerFactory.getLogger( SQLExpressionProcessor.class );

	private SQLExpressionNode node;
	
	/**
	 * @param t
	 */
	public SQLExpressionProcessor( SQLExpressionNode node ) {
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
			for ( ProcessorDefinition processor : node.getElements() ) {
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
