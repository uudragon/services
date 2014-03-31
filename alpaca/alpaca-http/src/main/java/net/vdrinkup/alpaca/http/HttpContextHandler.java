package net.vdrinkup.alpaca.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.vdrinkup.alpaca.SchemeConstants;
import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.FlowEngine;
import net.vdrinkup.alpaca.http.config.HttpProtocolConfig;

import org.eclipse.jetty.http.HttpMethods;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义Http上下文处理器
 * @author pluto.bing.liu
 *
 */
public class HttpContextHandler extends ContextHandler {
	
	private static Logger LOG = LoggerFactory.getLogger( HttpContextHandler.class );
	
	private HttpProtocolConfig config;
	
	public HttpContextHandler( HttpProtocolConfig config ) {
		this.config = config;
	}

	@Override
	public void doHandle( String target, Request baseRequest,
			final HttpServletRequest request, HttpServletResponse response )
			throws IOException, ServletException {
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Current target is [{}]", target );
		}
		String method = request.getMethod();
		byte[] message;
		if ( method.equals( HttpMethods.GET ) ) {
			final String queryString = request.getQueryString();
			message = queryString.getBytes( getConfig().getCommons().getCharset() );
		} else {
			final int messageLen = request.getContentLength();
			InputStream is = request.getInputStream();
			if ( messageLen <= 0 ) {
				byte[] buff = new byte[ 1024 ];
				int n = 0;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ( ( n = is.read( buff ) ) != -1 ) {
					baos.write( buff, 0, n );
				}
				message = baos.toByteArray();
			} else { 
				message = new byte[ messageLen ];
				is.read( message );
			}
		}
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Current received message is [{}].", new String( message, getConfig().getCommons().getCharset() ) );
		}
		final DataContext context = FlowEngine.INSTANCE.createnNewContext();
		context.setIn( message );
		context.setProperty( ContextConstants.CHARSET, getConfig().getCommons().getCharset() );
		StringBuilder fromName = new StringBuilder( SchemeConstants.Prefix.PROTOCOL_PREFIX );
		fromName.append( getConfig().getName() );
		context.setProperty( ContextConstants.CURRENT_LOCATION, fromName.toString() );
		fromName.append( target );
		context.setProperty( ContextConstants.FROM_NAME, fromName.toString() );
		context.setProperty( ContextConstants.EXECUTE_MODE, getConfig().getMode() );
		final long beginTimestamp = System.currentTimeMillis();
		FlowEngine.INSTANCE.incoming( context );
		final long endTimestamp = System.currentTimeMillis();
		LOG.info( new StringBuffer( "The context [" ).append( context.getId() )
				.append( "] used [" )
				.append( endTimestamp - beginTimestamp )
				.append( "ms]." ).toString() );
		String respMessage = "";
		if ( context.getOut() instanceof byte[] ) {
			final byte[] bytes = context.getOut();
			respMessage = new String( bytes, getConfig().getCommons().getCharset() );
		} else if ( context.getOut() instanceof String ) {
			respMessage = context.getOut();
		} else {
			throw new ServletException( "No type of message supported." );
		}
		response.addHeader( "Access-Control-Allow-Origin", "*" );
		response.setCharacterEncoding( getConfig().getCommons().getCharset() );
		final StringBuilder contentType = new StringBuilder( getConfig().getResponse().getContentType().getContentType() );
		contentType.append( ";charset=" ).append( getConfig().getCommons().getCharset() );
		response.setContentType( contentType.toString() );
		response.setContentLength( respMessage.length() );
		response.getWriter().write( respMessage );
	}

	public HttpProtocolConfig getConfig() {
		return config;
	}

}
