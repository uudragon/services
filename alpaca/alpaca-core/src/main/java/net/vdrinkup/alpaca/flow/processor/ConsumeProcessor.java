/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.processor;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.SchemeConstants;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.FlowEngine;
import net.vdrinkup.alpaca.flow.definition.ConsumeDefinition;

/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 11, 2014
 */
public class ConsumeProcessor extends AbstractProcessor< ConsumeDefinition > {

	/**
	 * @param t
	 */
	public ConsumeProcessor( ConsumeDefinition t ) {
		super( t );
	}

	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		final String oldFromName = context.getProperty( ContextConstants.FROM_NAME, String.class );
		final String oldToName = context.getProperty( ContextConstants.TO_NAME, String.class );
		final String fromName = oldToName.concat( SchemeConstants.LOCATION_SEPARATOR ).concat( getDefinition().getId() );
		context.setProperty( ContextConstants.FROM_NAME, fromName );
		FlowEngine.INSTANCE.incoming( context );
		context.setProperty( ContextConstants.FROM_NAME, oldFromName );
		context.setProperty( ContextConstants.FROM_NAME, oldToName );
		return true;
	}

}
