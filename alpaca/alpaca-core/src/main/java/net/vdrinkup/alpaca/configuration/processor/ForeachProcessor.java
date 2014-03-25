/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.processor;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ForeachDefinition;
import net.vdrinkup.alpaca.context.DataContext;


/**
 * <p></p>
 * @author bing.liu
 * Date 2012-2-6
 */
public class ForeachProcessor extends AbstractProcessor< ForeachDefinition > {

	/**
	 * @param definition
	 */
	public ForeachProcessor( ForeachDefinition definition ) {
		super( definition );
	}

	@Override
	public void handle( DataContext context ) throws Exception {
	}

}
