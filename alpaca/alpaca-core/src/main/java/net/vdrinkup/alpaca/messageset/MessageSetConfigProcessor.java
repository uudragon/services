/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset;

import net.vdrinkup.alpaca.configuration.ConfigProcessor;
import net.vdrinkup.alpaca.messageset.definition.MessageDefinition;

/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 26, 2013
 */
public abstract class MessageSetConfigProcessor implements ConfigProcessor {
	
	protected static final String MESSAGESET_CONTEXT_PATH;
	
	static {
		StringBuilder builder = new StringBuilder( CONFIG_CONTEXT_PATH );
		builder.append( ":" ).append( MessageDefinition.class.getPackage().getName() );
		MESSAGESET_CONTEXT_PATH = builder.toString();
	}

}
