/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.identific.processor;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.identific.definition.FromDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 28, 2013
 */
public class FromProcessor extends AbstractProcessor< FromDefinition > {

	/**
	 * @param t
	 */
	public FromProcessor( FromDefinition t ) {
		super( t );
	}

	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		process : for ( ProcessorDefinition processor : getDefinition().getOutputs() ) {
			try {
				processor.createProcessor().process( context );
			} catch ( Exception e ) {
				LOG.error( e.getMessage(), e );
				context.setException( e );
				context.setStatus( ContextStatus.EXCEPTION );
				break process;
			}
		}
		callback.done( true );
		return true;
	}

}
