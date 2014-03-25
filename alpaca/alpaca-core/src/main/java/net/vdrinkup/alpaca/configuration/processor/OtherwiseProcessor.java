/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.processor;

import java.util.Iterator;

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
	protected void handle( DataContext context ) throws Exception {
		final Iterator< ProcessorDefinition > iter = getDefinition().getElements().iterator();
		ProcessorDefinition definition = null;
		while ( ContextStatus.VALID.equals( context.getStatus() ) && iter.hasNext() ) {
			definition = iter.next();
			definition.createProcessor().process( context );
		}
	}

}
