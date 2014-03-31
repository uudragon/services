/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.processor;

import java.rmi.NoSuchObjectException;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.SchemeConstants;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.definition.ServiceDefinition;
import net.vdrinkup.alpaca.service.InvokeException;
import net.vdrinkup.alpaca.service.ServiceEntry;
import net.vdrinkup.alpaca.service.ServiceManager;

/**
 * 
 * <p>
 * </p>
 * 
 * @author liubing Date Nov 26, 2013
 */
public class ServiceProcessor extends AbstractProcessor< ServiceDefinition > {

	/**
	 * @param t
	 */
	public ServiceProcessor( ServiceDefinition t ) {
		super( t );
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean process( DataContext context, DoneCallback callback ) {
		process: {
			final StringBuffer location = new StringBuffer( context
					.getProperty( ContextConstants.CURRENT_LOCATION )
					.toString() );
			location.append( SchemeConstants.LOCATION_SEPARATOR ).append(
					getDefinition().getId() );
			// 设置当前执行定位信息
			context.setProperty( ContextConstants.CURRENT_LOCATION,
					location.toString() );
			// 查询服务
			final ServiceEntry entry = ServiceManager.INSTANCE
					.lookup( getDefinition().getId() );
			if ( entry == null ) {
				context.setException( new NoSuchObjectException(
						"Can not found service named ["
								+ getDefinition().getId()
								+ "] in the service registry." ) );
				context.setStatus( ContextStatus.EXCEPTION );
				break process;
			}
			try {
				entry.getInstance().invoke( context );
			} catch ( InvokeException e ) {
				context.setException( new NoSuchObjectException(
						"Can not found service named ["
								+ getDefinition().getId()
								+ "] in the service registry." ) );
				context.setStatus( ContextStatus.EXCEPTION );
				break process;
			}
		}
		return true;
	}

}
