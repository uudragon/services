/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.processor;

import java.util.List;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.OtherwiseDefinition;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;


/**
 * <p></p>
 * @author bing.liu
 * Date 2012-2-10
 */
public class OtherwiseProcessor extends AbstractProcessor< OtherwiseDefinition > {

	/**
	 * @param definition
	 */
	public OtherwiseProcessor( OtherwiseDefinition definition ) {
		super( definition );
	}

	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		final List< ProcessorDefinition > processors = getDefinition().getOutputs();
		for ( ProcessorDefinition processor : processors ) {
			try {
				processor.createProcessor().process( context );
			} catch ( Exception e ) {
				context.setException( e );
				context.setStatus( ContextStatus.EXCEPTION );
				return true;
			}
		}
		return true;
	}

}
