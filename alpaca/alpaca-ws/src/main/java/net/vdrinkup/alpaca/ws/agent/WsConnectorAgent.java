/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.ws.agent;

import javax.xml.namespace.QName;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.commons.token.TokenParser;
import net.vdrinkup.alpaca.commons.token.impl.DefaultTokenParser;
import net.vdrinkup.alpaca.commons.token.impl.MappedTokenHandler;
import net.vdrinkup.alpaca.protocol.Connection;
import net.vdrinkup.alpaca.protocol.definition.ProtocolConfig;
import net.vdrinkup.alpaca.ws.WsConstants;
import net.vdrinkup.alpaca.ws.config.WsEndpointConfig;
import net.vdrinkup.alpaca.ws.config.WsProtocolConfig;
import net.vdrinkup.alpaca.ws.config.WsRequestConfig;
import net.vdrinkup.alpaca.ws.provider.WsProcessSourceProvider;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.soap.SoapBindingFactory;
import org.apache.cxf.binding.soap.SoapTransportFactory;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * WebService服务端代理
 * <p>
 * 该类实现{@link Connection}接口，用于实现WebServer服务接入
 * </p>
 * @author liubing Date Nov 3, 2013
 */
public class WsConnectorAgent implements Connection {
	
	private static Logger LOG = LoggerFactory.getLogger( WsConnectorAgent.class );
	
	private WsProtocolConfig config;
	
	private JaxWsServerFactoryBean server;

	private boolean running = false;
	
	private TokenParser parser = new DefaultTokenParser( "${", "}" );

	@Override
	public boolean isShutdown() {
		return ! running;
	}

	@Override
	public boolean isStartup() {
		return running;
	}

	@Override
	public < R > R receive() throws Exception {
		return null;
	}

	@Override
	public < T, R > R send( T t ) throws Exception {
		return null;
	}

	@Override
	public synchronized void start() throws Exception {
		if ( isStartup() ) {
			return ;
		}
		if ( server == null ) {
			server = new JaxWsServerFactoryBean();
		}
		Bus bus = null;
		final ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader( BusFactory.class.getClassLoader() );
			bus = BusFactory.newInstance().createBus();
		} finally {
			Thread.currentThread().setContextClassLoader( oldCL );
		}
		server.setBus( bus );
		final SoapBindingFactory sfb = new SoapBindingFactory();
		sfb.setActivationNamespaces( WsConstants.ACTIVATION_NAMESPACES );
		sfb.setBus( bus );
		server.setBindingFactory( sfb );
		final SoapTransportFactory stf = new SoapTransportFactory();
		stf.setTransportIds( WsConstants.SERVER_HTTP_TRANSPORT_IDS );
		stf.setBus( bus );
		server.setDestinationFactory( stf );
		server.setAddress( getConfig().getRequest().getUri() );
		server.setServiceBean( new WsProcessSourceProvider( getConfig() ) );
		final WsRequestConfig request = ( WsRequestConfig ) getConfig().getRequest() ;
		final WsEndpointConfig endpointConfig = request.getEndpoint();
		String wsdl = endpointConfig.getWsdlLocation();
		wsdl = parser.parse( wsdl, new MappedTokenHandler( Env.getInstance().getProperties() ) );
		server.setServiceName( new QName( endpointConfig.getNamespace(), endpointConfig.getName() ) );
		server.setWsdlLocation( wsdl );
		server.setStart( true );
		server.create();
		running = true;
		LOG.info( "The instance of Protocol [{}] started successfully.", getConfig().getName() );
	}

	@Override
	public synchronized void stop() throws Exception {
		if ( isShutdown() ) {
			return ;
		}
//		endpoint.stop();
//		endpoint = null;
		server.getBus().shutdown( true );
		server.destroy();
		running = false;
		LOG.info( "The instance of Protocol [{}] stopped successfully.", getConfig().getName() );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends ProtocolConfig > T getConfig() {
		return ( T ) this.config;
	}

	@Override
	public < T extends ProtocolConfig > void setConfig( T t ) {
		this.config = ( WsProtocolConfig ) t;
	}

}
