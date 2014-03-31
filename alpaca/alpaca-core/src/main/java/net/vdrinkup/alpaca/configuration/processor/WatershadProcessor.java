/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.processor;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.WatershadDefinition;
import net.vdrinkup.alpaca.context.DataContext;

/**
 * 分水岭处理器
 * <p>
 * 该类的功能是将当前传入的Context的输出设置成输入，并清空当前输出。
 * <pre>
 * Context.IN = Context.Out
 * Context.Out = null
 * </pre>
 * 在后续的流程中，将当前的输出值作为输入值进行处理，而当前的输入值会被丢弃。
 * </p>
 * @author pluto.bing.liu
 * Date 2013-12-5
 */
public class WatershadProcessor extends AbstractProcessor< WatershadDefinition > {

	/**
	 * @param t
	 */
	public WatershadProcessor( WatershadDefinition t ) {
		super( t );
	}

	@Override
	public boolean process( DataContext context, DoneCallback callback ) {
		context.setIn( context.getOut() );
		context.setOut( null );
		return true;
	}

}
