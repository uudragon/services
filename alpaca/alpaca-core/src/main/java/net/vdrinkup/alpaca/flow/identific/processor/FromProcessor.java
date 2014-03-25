/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.identific.processor;

import java.util.Iterator;

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
	protected void handle( DataContext context ) throws Exception {
		final Iterator< ProcessorDefinition > iter = getDefinition().getElements().iterator();
		ProcessorDefinition definition = null;
		while ( ContextStatus.VALID.equals( context.getStatus() ) && iter.hasNext() ) {
			definition = iter.next();
			definition.createProcessor().process( context );
		}
	}

}
