/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.ws.proxy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.protocol.Connection;
import net.vdrinkup.alpaca.protocol.definition.ProtocolConfig;
import net.vdrinkup.alpaca.ws.config.WsEndpointConfig;
import net.vdrinkup.alpaca.ws.config.WsProtocolConfig;
import net.vdrinkup.alpaca.ws.config.WsRequestConfig;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 5, 2013
 */
public class WsConnectorProxy implements Connection {
	
	private boolean running = false;
	
	private WsProtocolConfig config;
	
	private Service service;
	
	private Dispatch< Source > dispatch;
	
//	private JaxWsClientFactoryBean client;
//		
//	private Client proxy;
	
	public WsConnectorProxy() {
//		this.client = new JaxWsClientFactoryBean();
	}

	/* (non-Javadoc)
	 * @see com.jd.wms.servicebus.protocol.Connection#isShutdown()
	 */
	@Override
	public boolean isShutdown() {
		return ! running;
	}

	/* (non-Javadoc)
	 * @see com.jd.wms.servicebus.protocol.Connection#isStartup()
	 */
	@Override
	public boolean isStartup() {
		return running;
	}

	/* (non-Javadoc)
	 * @see com.jd.wms.servicebus.protocol.Connection#receive()
	 */
	@Override
	public < R > R receive() throws Exception {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jd.wms.servicebus.protocol.Connection#send(java.lang.Object)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public < T, R > R send( T t ) throws Exception {
		if ( isShutdown() ) {
			throw new IOException( "The instance[" + getConfig().getName() + "] has been stopped. The message can not be sent." );
		}
		final DataContext context = ( DataContext ) t;
		final Source source = new StreamSource( new ByteArrayInputStream( ( byte[] ) context.getOut() ) );
		return ( R ) dispatch.invoke( source );
	}

	/* (non-Javadoc)
	 * @see com.jd.wms.servicebus.protocol.Connection#start()
	 */
	@Override
	public void start() throws Exception {
		if ( isStartup() ) {
			return ;
		}
		final WsRequestConfig request = ( ( WsRequestConfig ) getConfig().getRequest() );
		final WsEndpointConfig endpointConfig = request.getEndpoint();
		final URI uri = URI.create( endpointConfig.getWsdlLocation() );
		service = Service.create( uri.toURL(), new QName( endpointConfig.getNamespace(), endpointConfig.getName() ) );
		dispatch = service.createDispatch( new QName( endpointConfig.getNamespace(), endpointConfig.getPort() ), Source.class, Mode.PAYLOAD );
		/*ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
		Bus bus;
		try {
			Thread.currentThread().setContextClassLoader( BusFactory.class.getClassLoader() );
			bus = BusFactory.newInstance().createBus();
			client.setBus( bus );
		} finally {
			Thread.currentThread().setContextClassLoader( oldCL );
		}
		final SoapBindingFactory sfb = new SoapBindingFactory();
		sfb.setActivationNamespaces( WsConstants.ACTIVATION_NAMESPACES );
		sfb.setBus( bus );
		client.setBindingFactory( sfb );
		final SoapTransportFactory stf =  new SoapTransportFactory();
		stf.setTransportIds( WsConstants.SERVER_HTTP_TRANSPORT_IDS );
		stf.setBus( bus );
		client.setDestinationFactory( stf );
		final WsRequestConfig request = ( ( WsRequestConfig ) getConfig().getRequest() );
		client.setAddress( request.getUri() );
		if ( request.getResource() == null || "".equals( request.getResource() ) ) {
			throw new IllegalArgumentException(  );
		}
		final String resource = request.getResource();
		final Class< ? > clazz = Class.forName( resource, false, this.getClass().getClassLoader() );
		client.setServiceClass( clazz );
		client.getInInterceptors().add( new LoggingInInterceptor() );
		client.getOutInterceptors().add( new LoggingOutInterceptor() );
		proxy = client.create();*/
		running = true;
	}

	/* (non-Javadoc)
	 * @see com.jd.wms.servicebus.protocol.Connection#stop()
	 */
	@Override
	public void stop() throws Exception {
		if ( isShutdown() ) {
			return ;
		}
		dispatch = null;
		service = null;
		running = false;
	}

	/* (non-Javadoc)
	 * @see com.jd.wms.servicebus.protocol.Connection#getConfig()
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends ProtocolConfig > T getConfig() {
		return ( T ) this.config;
	}

	/* (non-Javadoc)
	 * @see com.jd.wms.servicebus.protocol.Connection#setConfig(com.jd.wms.servicebus.protocol.config.ProtocolConfig)
	 */
	@Override
	public < T extends ProtocolConfig > void setConfig( T t ) {
		this.config = ( WsProtocolConfig ) t;
	}

}
