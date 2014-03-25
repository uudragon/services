/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml.processor.decode;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.messageset.xml.definition.XMLElementDefinition;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-25
 */
public interface ElementTypeProcessor {
	
	public void process( XMLElementDefinition definition, DataContext context );

}
