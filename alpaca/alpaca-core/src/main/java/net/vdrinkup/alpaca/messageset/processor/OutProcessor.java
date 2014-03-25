/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.processor;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.messageset.definition.MessageOutputDefinition;

/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 26, 2013
 */
public abstract class OutProcessor< T extends MessageOutputDefinition > extends AbstractProcessor< T > {

	public OutProcessor( T t ) {
		super( t );
	} 
	
}
