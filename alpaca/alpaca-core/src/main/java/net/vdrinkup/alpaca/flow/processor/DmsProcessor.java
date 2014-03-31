/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.processor;

import java.rmi.NoSuchObjectException;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.definition.DmsDefinition;
import net.vdrinkup.alpaca.service.InvokeException;
import net.vdrinkup.alpaca.service.ServiceEntry;
import net.vdrinkup.alpaca.service.ServiceManager;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 17, 2014
 */
public class DmsProcessor extends AbstractProcessor< DmsDefinition > {

	/**
	 * @param t
	 */
	public DmsProcessor( DmsDefinition t ) {
		super( t );
	}

	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		//查询服务
		final ServiceEntry entry = ServiceManager.INSTANCE.lookup( getDefinition().getService() );
		if ( entry == null ) {
			context.setException( new NoSuchObjectException( "Can not found service named ["
					+ getDefinition().getService() + "] in the service registry." ) );
			context.setStatus( ContextStatus.EXCEPTION );
			return true;
		}
		String preFromName = context.getProperty( ContextConstants.FROM_NAME, String.class );
		String preToName = context.getProperty( ContextConstants.TO_NAME, String.class );
		context.setProperty( ContextConstants.FROM_NAME, preToName );
		context.setProperty( ContextConstants.TO_NAME, getDefinition().getTo() );
		try {
			entry.getInstance().invoke( context );
		} catch ( InvokeException e ) {
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
			return true;
			
		}
		context.setProperty( ContextConstants.FROM_NAME, preFromName );
		context.setProperty( ContextConstants.TO_NAME, preToName );
		return true;
	}

}
