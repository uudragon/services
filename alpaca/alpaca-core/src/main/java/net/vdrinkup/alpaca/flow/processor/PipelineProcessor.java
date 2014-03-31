/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.processor;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.definition.PipelineDefinition;

/**
 * 顺序流程配置处理器
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-12
 */
public class PipelineProcessor extends AbstractProcessor< PipelineDefinition > {

	public PipelineProcessor( PipelineDefinition t ) {
		super( t );
	}

	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		return true;
	}

}
