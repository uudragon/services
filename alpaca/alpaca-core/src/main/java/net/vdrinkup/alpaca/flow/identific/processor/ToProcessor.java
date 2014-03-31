/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.identific.processor;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.FlowDefinition;
import net.vdrinkup.alpaca.flow.FlowManager;
import net.vdrinkup.alpaca.flow.identific.definition.ToDefinition;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 28, 2013
 */
public class ToProcessor extends AbstractProcessor< ToDefinition > {

	/**
	 * @param t
	 */
	public ToProcessor( ToDefinition t ) {
		super( t );
	}

	@Override
	public boolean process( DataContext context, DoneCallback callback ) {
		final String to = getDefinition().getUri();
		if ( to == null || "".equals( to ) ) {
			context.setException( new IllegalArgumentException( "Attribute [flow] can not be null." ) );
			context.setStatus( ContextStatus.EXCEPTION );
			return true;
		}
		final FlowDefinition flowDef = FlowManager.INSTANCE.lookup( to );
		context.setProperty( ContextConstants.TO_NAME, to );
		context.setProperty( ContextConstants.FLOW, flowDef );
		return true;
	}

}
