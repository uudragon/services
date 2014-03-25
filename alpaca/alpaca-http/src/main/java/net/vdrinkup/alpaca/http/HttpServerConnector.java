package net.vdrinkup.alpaca.http;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

import net.vdrinkup.alpaca.http.HttpServerRegistry.Entry;
import net.vdrinkup.alpaca.http.config.HttpProtocolConfig;
import net.vdrinkup.alpaca.http.config.HttpRequestConfig;
import net.vdrinkup.alpaca.protocol.Connection;
import net.vdrinkup.alpaca.protocol.definition.ProtocolConfig;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServerConnector implements Connection {
	
	private static Logger LOG = LoggerFactory.getLogger( HttpServerConnector.class );
	
	private AtomicBoolean running = new AtomicBoolean( false );
	
	private HttpProtocolConfig config;
	
	private HttpContextHandler handler;

	@Override
	public <T extends ProtocolConfig> void setConfig(T config) {
		this.config = (HttpProtocolConfig) config;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ProtocolConfig> T getConfig() {
		return (T) this.config;
	}

	@Override
	public boolean isShutdown() {
		return ! running.get();
	}

	@Override
	public boolean isStartup() {
		return running.get();
	}

	@Override
	public <R> R receive() throws Exception {
		return null;
	}

	@Override
	public <T, R> R send(T t) throws Exception {
		return null;
	}

	@Override
	public synchronized void start() throws Exception {
		if ( isStartup() ) {
			LOG.warn( "The instance of [{}] has been started.", getConfig().getName() );
			return;
		}
		final HttpRequestConfig request = ( HttpRequestConfig ) this.config.getRequest();
		final URI uri = URI.create( request.getUri() );
		final int port = uri.getPort();
		Entry entry = HttpServerRegistry.getInstance().getEntry( port );
		Server server = null;
		if ( entry == null ) {
			server = new Server();
			final HandlerCollection chain = new HandlerCollection();
			server.setHandler( chain );
			loadConnector( server, uri.getHost(), uri.getPort() );
			entry = HttpServerRegistry.getInstance().new Entry( port, server, 1 );
			HttpServerRegistry.getInstance().register( entry ); 
		} else {
			int count = entry.getCount();
			entry.setCount( ++ count );
		}
		loadHandler( server, uri.getPath() );
		if ( ! server.isStarted() ) {
			server.setStopAtShutdown( true );
			server.start();
		}
		running.set( true );
		LOG.info( "The instance of [{}] started successfully.", getConfig().getName() );
	}
	
	private void loadHandler( Server server, String contextPath ) {
		this.handler = new HttpContextHandler( this.config );
		if ( contextPath == null || "".equals( contextPath ) ) {
			contextPath = "/";
		}
		this.handler.setContextPath( contextPath );
		this.handler.setClassLoader( this.getClass().getClassLoader() );
		( ( HandlerCollection ) server.getHandler() ).addHandler( this.handler );
	}

	private void loadConnector( Server server, String host, int port ) {
		SelectChannelConnector scc = new SelectChannelConnector();
		scc.setPort( port );
		scc.setHost( host );
		scc.setReuseAddress( true );
		scc.setServer( server );
		server.setConnectors( new Connector[] { scc } );
	}
	
	@Override
	public void stop() throws Exception {
		if ( isShutdown() ) {
			LOG.info( "The instance of [{}] has been stopped.", getConfig().getName() );
			return;
		}
		running.set( false );
		if ( handler != null ) {
			this.handler.stop();
		}
		final HttpRequestConfig request = ( HttpRequestConfig ) this.config.getRequest();
		final URI uri = URI.create( request.getUri() );
		final int port = uri.getPort();
		final Entry entry = HttpServerRegistry.getInstance().getEntry( port );
		if ( entry == null ) {
			throw new NoSuchElementException( "No entry named [" + port + "] in the registry." );
		}
		final Server server = entry.getServer();
		final HandlerCollection chain = ( HandlerCollection ) server.getHandler();
		chain.removeHandler( handler );
		int count = entry.getCount();
		if ( count-- <= 0 ) {
			HttpServerRegistry.getInstance().unregister( entry );
		} else {
			entry.setCount( count );
		}
		LOG.info( "The instance of [{}] stopped successfully.", getConfig().getName() );
	}

}
