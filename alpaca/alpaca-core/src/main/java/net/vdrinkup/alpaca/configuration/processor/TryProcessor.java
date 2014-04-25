/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.processor;

import java.util.List;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.CatchDefinition;
import net.vdrinkup.alpaca.configuration.model.FinallyDefinition;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.DataContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * <p></p>
 * @author liubing
 * Date Mar 26, 2014
 */
public class TryProcessor implements Processor {
	
	private static Logger LOG = LoggerFactory.getLogger( TryProcessor.class );

	private final List< ProcessorDefinition > outputs;
	
	private final List< CatchDefinition > catchClauses;
	
	private final FinallyDefinition finallyClause;
	
	public TryProcessor( List< ProcessorDefinition > outputs, final List< CatchDefinition > catchClauses, final FinallyDefinition finallyClause ) {
		this.outputs = outputs;
		this.catchClauses = catchClauses;
		this.finallyClause = finallyClause;
	}
	
	@Override
	public void process( DataContext context ) throws Exception {
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Begin try chunk." );
		}
		for ( ProcessorDefinition processor : outputs ) {
			processor.createProcessor().process( context );
		}
		processCatch( context );
		processFinally( context );
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "End try chunk." );
		}
	}

	/**
	 * @param context
	 */
	private void processFinally( DataContext context ) throws Exception {
		if ( finallyClause != null ) {
			finallyClause.createProcessor().process( context );
		}
	}

	/**
	 * @param context
	 */
	private void processCatch( DataContext context ) throws Exception  {
		if ( catchClauses == null ) {
			return;
		}
		for ( CatchDefinition catchClause : catchClauses ) {
			catchClause.createProcessor().process( context );
		}
	}

}
