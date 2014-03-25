/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.messageset.definition.MessageDefinition;
import net.vdrinkup.alpaca.service.InvokeException;

/**
 * 消息集输出代理服务
 * <p>
 * 该类是报文组装的入口。通过调用该类，将内部数据对象组装成对应接口所需的报文。
 * </p>
 * @author liubing
 * Date Jan 2, 2014
 */
public class MessageSetOutProxyService extends AbstractCodecProxyService {

	@Override
	protected void process( MessageDefinition definition, DataContext context )
			throws InvokeException {
		if ( definition == null ) {
			throw new InvokeException( "Current MessageSet definition is null." );
		}
		try {
			context.setProperty( MessageSetConstants.MESSAGE_DIRECTION, MessageDirection.OUT );
			definition.getOut().createProcessor().process( context );
			context.removeProperty( MessageSetConstants.MESSAGE_DIRECTION );
		} catch ( Exception e ) {
			throw new InvokeException( e );
		}
	}

}
