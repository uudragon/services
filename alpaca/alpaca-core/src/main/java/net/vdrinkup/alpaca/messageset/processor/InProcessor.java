/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.processor;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.messageset.definition.MessageInputDefinition;

/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 26, 2013
 */
public abstract class InProcessor< T extends MessageInputDefinition > extends AbstractProcessor< T > {

	/**
	 * @param t
	 */
	public InProcessor( T t ) {
		super( t );
	}

}
