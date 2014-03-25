/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.processor;

import java.rmi.NoSuchObjectException;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.definition.DmsDefinition;
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
	protected void handle( DataContext context ) throws Exception {
		//查询服务
		final ServiceEntry entry = ServiceManager.INSTANCE.lookup( getDefinition().getService() );
		if ( entry == null ) {
			throw new NoSuchObjectException( "Can not found service named ["
					+ getDefinition().getService() + "] in the service registry." );
		}
		String preFromName = context.getProperty( ContextConstants.FROM_NAME, String.class );
		String preToName = context.getProperty( ContextConstants.TO_NAME, String.class );
		context.setProperty( ContextConstants.FROM_NAME, preToName );
		context.setProperty( ContextConstants.TO_NAME, getDefinition().getTo() );
		entry.getInstance().invoke( context );
		context.setProperty( ContextConstants.FROM_NAME, preFromName );
		context.setProperty( ContextConstants.TO_NAME, preToName );
	}

}
