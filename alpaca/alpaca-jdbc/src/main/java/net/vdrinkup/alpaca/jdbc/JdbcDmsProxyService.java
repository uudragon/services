/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.jdbc;

import net.vdrinkup.alpaca.SchemeConstants;
import net.vdrinkup.alpaca.SchemeUtil;
import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.dms.DMSProviderManager;
import net.vdrinkup.alpaca.dms.Provider;
import net.vdrinkup.alpaca.dms.Session;
import net.vdrinkup.alpaca.messageset.MessageDirection;
import net.vdrinkup.alpaca.messageset.MessageSetConfigManager;
import net.vdrinkup.alpaca.messageset.MessageSetConstants;
import net.vdrinkup.alpaca.messageset.definition.MessageDefinition;
import net.vdrinkup.alpaca.quality.transaction.Transaction;
import net.vdrinkup.alpaca.service.InvokeException;
import net.vdrinkup.alpaca.service.ProxyService;
import net.vdrinkup.alpaca.service.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-17
 */
public class JdbcDmsProxyService implements ProxyService {
	
	private static Logger LOG = LoggerFactory.getLogger( JdbcDmsProxyService.class );
	
	@Override
	public void invoke( DataContext context ) throws InvokeException {
		//获得报文配置定义
		final String fromName= context.getProperty( ContextConstants.FROM_NAME, String.class );
		final String toName = context.getProperty( ContextConstants.TO_NAME, String.class );
		final String defId = fromName.concat( "_" ).concat( toName );
		MessageDefinition definition = MessageSetConfigManager.getInstance().lookup( defId );
		context.setProperty( MessageSetConstants.MESSAGE_DIRECTION, MessageDirection.OUT );
		definition.getOut().createProcessor().process( context );
		Transaction curTransaction = null;
		if ( context.isTransacted() ) {
			curTransaction = context.getTransaction();
		} else {
			String providerName = SchemeUtil.getSchemeName( context.getProperty( ContextConstants.TO_NAME, String.class ), SchemeConstants.DMS_SCHEME );
			Provider provider = DMSProviderManager.getInstance().lookup( providerName );
			try {
				curTransaction = provider.createTransaction( true );
			} catch ( Exception e ) {
				if ( curTransaction != null ) {
					try {
						curTransaction.close();
					} catch (Exception ex) {
						LOG.error( ex.getMessage(), ex );
					}
				}
				throw new InvokeException( e );
			}
		}
		try {
			Session session = context.getOut();
			Object obj = session.run( curTransaction );
			context.setOut( context.getIn() );
			context.setIn( obj );
			context.setProperty( MessageSetConstants.MESSAGE_DIRECTION, MessageDirection.IN );
			definition.getIn().createProcessor().process( context );
			context.setIn( context.getOut() );
			context.setOut( null );
			if ( ! context.isTransacted() ) {
				curTransaction.commit();
			}
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
			try {
				if ( ! curTransaction.isCompleted() ) {
					curTransaction.rollback();
					LOG.error( "The transaction is rollback." );
					curTransaction.close();
				}
			} catch ( Exception ex ) {
				LOG.error( ex.getMessage(), ex );
			}
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
