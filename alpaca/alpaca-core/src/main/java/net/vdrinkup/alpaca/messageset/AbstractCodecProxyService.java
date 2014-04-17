/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.messageset.definition.MessageDefinition;
import net.vdrinkup.alpaca.service.InvokeException;
import net.vdrinkup.alpaca.service.ProxyService;
import net.vdrinkup.alpaca.service.ServiceException;

/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 25, 2013
 */
public abstract class AbstractCodecProxyService implements ProxyService {
	
	protected static Logger LOG = LoggerFactory.getLogger( AbstractCodecProxyService.class );

	@Override
	public void invoke( DataContext context ) throws InvokeException {
		final String fromName = context.getProperty( ContextConstants.FROM_NAME, String.class );
		if ( fromName == null || "".equals( fromName ) ) {
			throw new InvokeException( "From Name can not be null." );
		}
		final String toName = context.getProperty( ContextConstants.TO_NAME, String.class );
		if ( toName == null || "".equals( toName ) ) {
			throw new InvokeException( "To Name con not be null." );
		}
		final String key = fromName.concat( "_" ).concat( toName );
		final MessageDefinition definition = MessageSetConfigManager.getInstance().lookup( key );
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Current sdo is [{}]", context.getIn() );
		}
		process( definition, context );
	}

	protected abstract void process( MessageDefinition definition, DataContext context ) throws InvokeException;

	@Override
	public void start() throws ServiceException {
		
	}

	@Override
	public void stop() throws ServiceException {
		
	}

	@Override
	public boolean isStartup() {
		return false;
	}

	@Override
	public boolean isShutdown() {
		return false;
	}

}
