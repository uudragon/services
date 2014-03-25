/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.startup;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import net.vdrinkup.alpaca.Env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * <p>
 * </p>
 * 
 * @author pluto.bing.liu Date 2013-12-14
 */
public class StandardServer implements Runnable {

	private static Logger LOG = LoggerFactory.getLogger( StandardServer.class );
	
	protected static final Object SHUTDOWN = "SHUTDOWN";
	
	private ExecutorService single;
	
	private AtomicBoolean running;
	
	private int port;
	
	private ServerSocket server;

	public StandardServer( int port ) {
		this.port = port;
		this.single = Executors.newSingleThreadExecutor( new ThreadFactory() {
			
			private ThreadGroup group = new ThreadGroup( "StandardServer-ThreadGoup" );

			public Thread newThread( Runnable r ) {
				return new Thread( r, this.group.getName() + "-" + 0 );
			}

		} );
		this.running = new AtomicBoolean( false );
	}

	public synchronized void start() throws Exception {
		if ( isStartup() ) {
			return;
		}
		this.server = new ServerSocket();
		this.server.bind( new InetSocketAddress( "0.0.0.0", this.port ), 1 );
		this.single.execute( this );
		this.running.set( true );
		LOG.info( "The instance [{}] of StandardServer has been started.",
				"0.0.0.0:" + this.port );
	}

	public void run() {
		if ( isStartup() ) {
			try {
				Socket socket = this.server.accept();
				socket.setSoTimeout( 5000 );
				InputStream is = socket.getInputStream();
				byte[] readBuff = new byte[ 1024 ];
				int n = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				if ( ( n = is.read( readBuff ) ) != -1 ) {
					baos.write( readBuff, 0, n );
				}
				String curCommand = new String( baos.toByteArray() );
				baos.reset();
				baos = null;
				socket.close();
				socket = null;
				if ( SHUTDOWN.equals( curCommand ) ) {
					LOG.info( "Command {} has been received.", curCommand );
					stop();
				}
			} catch ( Exception e ) {
				LOG.error( e.getMessage(), e );
			}
		}
	}

	protected synchronized void stop() throws Exception {
		if ( isShutdown() ) {
			return;
		}
		this.running.set( false );
		this.single.shutdown();
		final int shutdownTimeout = Env.getInstance().getProperty(
				Env.Keys.AWAIT_SHUTDOWN_TIMEOUT, Integer.class );
		if ( !( this.single.awaitTermination( shutdownTimeout, TimeUnit.MILLISECONDS ) ) ) {
			this.single.shutdownNow();
		}
		this.server.close();
		LOG.info( "The instance [{}] of StandardServer has been stopped.",
				"0.0.0.0:" + this.port );
		System.exit( 0 );
	}

	public boolean isStartup() {
		return this.running.get();
	}

	public boolean isShutdown() {
		return ! this.running.get();
	}

}
