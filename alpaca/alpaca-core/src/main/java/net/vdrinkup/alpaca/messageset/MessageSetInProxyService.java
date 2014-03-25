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
 * 消息集输入代理服务类
 * <p>
 * 该类是报文解析的入口类。通过调用该类，实现将接口传递来的报文解析成内部数据对象。
 * </p>
 * @author liubing
 * Date Jan 2, 2014
 */
public class MessageSetInProxyService extends AbstractCodecProxyService {

	@Override
	protected void process( MessageDefinition definition, DataContext context ) throws InvokeException {
		if ( definition == null ) {
			throw new InvokeException( "Current MessageSet definition is null." );
		}
		try {
			context.setProperty( MessageSetConstants.MESSAGE_DIRECTION, MessageDirection.IN );
			definition.getIn().createProcessor().process( context );
			context.removeProperty( MessageSetConstants.MESSAGE_DIRECTION );
			context.setIn( context.getOut() );
			context.setOut( null );
		} catch ( Exception e ) {
			throw new InvokeException( e );
		}
	}

}
