/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.ws.provider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Provider;
import javax.xml.ws.Service.Mode;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;

import net.vdrinkup.alpaca.SchemeConstants;
import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.FlowEngine;
import net.vdrinkup.alpaca.protocol.definition.ProtocolConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 9, 2013
 */
@WebServiceProvider
@ServiceMode( Mode.PAYLOAD )
public class WsProcessSourceProvider implements Provider< Source > {
	
	private static Logger LOG = LoggerFactory.getLogger( WsProcessSourceProvider.class );
	
	private final TransformerFactory tFactory = TransformerFactory.newInstance();
	
	private ProtocolConfig config;
	
	public WsProcessSourceProvider( ProtocolConfig config ) {
		this.config = config;
	}

	/* (non-Javadoc)
	 * @see javax.xml.ws.Provider#invoke(java.lang.Object)
	 */
	@Override
	public Source invoke( Source request ) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			final Transformer tf = tFactory.newTransformer();
			final Result inSourceRet = new StreamResult( baos );
			tf.transform( request, inSourceRet );
			LOG.info( "Current receivecd payload is [{}]", new String( baos.toString( getConfig().getCommons().getCharset() ) ) );
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
		}
		final DataContext context = FlowEngine.INSTANCE.createnNewContext();
		context.setProperty( ContextConstants.CHARSET, getConfig().getCommons().getCharset() );
		context.setProperty( ContextConstants.FROM_NAME, SchemeConstants.Prefix.PROTOCOL_PREFIX.concat( getConfig().getName() ) );
		context.setProperty( ContextConstants.CURRENT_LOCATION, SchemeConstants.Prefix.PROTOCOL_PREFIX.concat( getConfig().getName() ) );
		context.setProperty( ContextConstants.EXECUTE_MODE, getConfig().getMode() );
		context.setIn( baos.toByteArray() );
		final long beginTimestamp = System.currentTimeMillis();
		FlowEngine.INSTANCE.incoming( context );
		final long endTimestamp = System.currentTimeMillis();
		LOG.info( new StringBuffer( "The context [" ).append( context.getId() )
				.append( "] used [" )
				.append( endTimestamp - beginTimestamp )
				.append( "ms]." ).toString() );
		ByteArrayInputStream bais = null;
		if ( context.getOut() instanceof byte[] ) {
			try {
				LOG.info( "Current response of context [{}] is [{}]", context.getId(), new String( ( byte[] )context.getOut(), getConfig().getCommons().getCharset() ) );
				bais = new ByteArrayInputStream( ( byte[] ) context.getOut() );
			} catch ( UnsupportedEncodingException e ) {
				LOG.error( e.getMessage(), e );
			}
		} else if ( context.getOut() instanceof String ) {
			try {
				LOG.info( "Current response of context [{}] is [{}]", context.getId(), context.getOut().toString() );
				bais = new ByteArrayInputStream( ( ( ( String ) context.getOut() ).getBytes( getConfig().getCommons().getCharset() ) ) );
			} catch ( UnsupportedEncodingException e ) {
				LOG.error( e.getMessage(), e );
			}
		}
		return new StreamSource( bais );
	}

	/**
	 * @return the config
	 */
	public ProtocolConfig getConfig() {
		return config;
	}

}
