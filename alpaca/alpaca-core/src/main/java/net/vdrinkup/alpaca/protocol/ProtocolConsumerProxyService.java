/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.protocol;

import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.service.InvokeException;
import net.vdrinkup.alpaca.service.ProxyService;
import net.vdrinkup.alpaca.service.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 11, 2014
 */
public class ProtocolConsumerProxyService implements ProxyService {

	private static Logger LOG = LoggerFactory.getLogger( ProtocolConsumerProxyService.class );

	@Override
	public void invoke( DataContext context ) throws InvokeException {
		String toName = context.getProperty( ContextConstants.TO_NAME, String.class );
		if ( toName == null || "".equals( toName ) ) {
			throw new InvokeException( "Can not found 'toName' at current context." );
		}
		String name = toName.substring( toName.indexOf( ":" ) + 1 );
		Connection connection = ProtocolInstanceManager.getInstance().lookup( name );
		try {
			connection.send( context );
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
			throw new InvokeException( e );
		}
	}

	@Override
	public void start() throws ServiceException {
	}

	@Override
	public void stop() throws ServiceException {
	}

	@Override
	public boolean isStartup() {
		return true;
	}

	@Override
	public boolean isShutdown() {
		return false;
	}

}
