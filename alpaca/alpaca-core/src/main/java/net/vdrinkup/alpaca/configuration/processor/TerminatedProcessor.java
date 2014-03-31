/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.processor;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.TerminatedDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 26, 2013
 */
public class TerminatedProcessor extends AbstractProcessor< TerminatedDefinition > {

	/**
	 * @param t
	 */
	public TerminatedProcessor( TerminatedDefinition t ) {
		super( t );
	}
	
	@Override
	public boolean process( DataContext context, DoneCallback callback ) {
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "The flow will be terminated." );
		}
		context.setStatus( ContextStatus.TERMINATED );
		return true;
	}

}
