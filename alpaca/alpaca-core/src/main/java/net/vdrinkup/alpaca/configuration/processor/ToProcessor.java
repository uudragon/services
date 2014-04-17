/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.processor;

import java.net.MalformedURLException;
import java.net.URI;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.NoSuchSchemeException;
import net.vdrinkup.alpaca.SchemeConstants;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ToDefinition;
import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.FlowDefinition;
import net.vdrinkup.alpaca.flow.FlowManager;
import net.vdrinkup.alpaca.protocol.Connection;
import net.vdrinkup.alpaca.protocol.ProtocolInstanceManager;
import net.vdrinkup.alpaca.service.ServiceEntry;
import net.vdrinkup.alpaca.service.ServiceManager;

/**
 * 流程跳转配置处理器
 * <p></p>
 * @author liubing
 * Date 2014-4-8
 */
public class ToProcessor extends AbstractProcessor< ToDefinition > {


	public ToProcessor( ToDefinition t ) {
		super( t );
	}

	private void invoke( URI uri, String toName, DataContext context ) throws Exception {
		final String scheme = uri.getScheme();
		if ( SchemeConstants.FLOW_SCHEME.equals( scheme ) ) {
		 	final FlowDefinition flow = FlowManager.INSTANCE.lookup( toName );
		 	flow.createProcessor().process( context );
		} else if ( SchemeConstants.PROTOCOL_SCHEME.equals( scheme ) ) {
			Connection connector = ProtocolInstanceManager.getInstance().lookup( toName );
			connector.send( context );
		} else if ( SchemeConstants.DMS_SCHEME.equals( scheme ) ) {
			//TODO
		} else if ( SchemeConstants.SERVICE_SCHEME.equals( scheme ) ) {
			final ServiceEntry entry = ServiceManager.INSTANCE.lookup( toName );
			entry.getInstance().invoke( context );
		} else {
			throw new NoSuchSchemeException( "Can not found scheme named [" + scheme + "]" );
		}
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Current [{}] will be process.", uri.toString() );
		}
	}

	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		final URI uri = getDefinition().getUri();
		final String oldToName = context.getProperty( ContextConstants.TO_NAME, String.class );
		final String toName = getDefinition().getToName();
		context.setProperty( ContextConstants.TO_NAME, getDefinition().getUriStr() );
		try {
			invoke( uri, toName, context );
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
		}
		context.setProperty( ContextConstants.TO_NAME, oldToName );
		callback.done( true );
		return true;
	}

}
