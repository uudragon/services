/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.processor;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.configuration.model.WhenDefinition;
import net.vdrinkup.alpaca.context.DataContext;

/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 18, 2014
 */
public class WhenProcessor extends AbstractProcessor< WhenDefinition > {

	/**
	 * @param t
	 */
	public WhenProcessor( WhenDefinition t ) {
		super( t );
	}

	@Override
	protected void handle( DataContext context ) throws Exception {
		for ( ProcessorDefinition processor : getDefinition().getElements() ) {
			processor.createProcessor().process( context );
		}
	}

}
