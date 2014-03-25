/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.processor;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.definition.MulticastDefinition;

/**
 * 组播流程配置处理器
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-12
 */
public class MulticastProcessor extends AbstractProcessor< MulticastDefinition > {

	public MulticastProcessor( MulticastDefinition t ) {
		super( t );
	}

	@Override
	protected void handle( DataContext context ) throws Exception {
		
	}

}
