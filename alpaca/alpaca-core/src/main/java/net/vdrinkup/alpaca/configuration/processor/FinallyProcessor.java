/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.processor;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.FinallyDefinition;
import net.vdrinkup.alpaca.context.DataContext;

/**
 *
 * <p></p>
 * @author liubing
 * Date Mar 26, 2014
 */
public class FinallyProcessor extends AbstractProcessor< FinallyDefinition > {

	public FinallyProcessor( FinallyDefinition t ) {
		super( t );
	}

	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		return true;
	}

}
