/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.startup;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.commons.log.LogInstanceProxy;
import net.vdrinkup.alpaca.dms.DMSProviderManager;
import net.vdrinkup.alpaca.flow.FlowManager;
import net.vdrinkup.alpaca.messageset.MessageSetConfigManager;
import net.vdrinkup.alpaca.protocol.ProtocolConfigManager;
import net.vdrinkup.alpaca.service.ServiceManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * <p>
 * </p>
 * @author pluto.bing.liu Date 2013-12-14
 */
public class Startup {

	private static Logger LOG = LoggerFactory.getLogger( Startup.class );

	private StandardServer server;

	public void start() {
		if ( this.server == null ) {
			try {
				load();
			} catch ( Exception e ) {
				LOG.error( "System started error.", e );
				System.exit( -1 );
			}
		}
		if ( !this.server.isStartup() ) {
			startServer();
		} else {
			LOG.info( "The system has been started." );
		}
	}

	public void stop() {
		if ( this.server != null ) {
			stopServer();
		} else {
			final int _port = Env.getInstance().getProperty(
					Env.Keys.AWAIT_PORT, Integer.class );
			Socket socket = null;
			try {
				socket = new Socket( "localhost", _port );
				OutputStream os = socket.getOutputStream();
				os.write( "SHUTDOWN".getBytes() );
				os.flush();
			} catch ( Exception e ) {
				LOG.error( e.getMessage(), e );
				LOG.error( "The instance of Sowing has some errors, stopped mandatory." );
				System.exit( -1 );
			} finally {
				if ( socket != null ) {
					try {
						socket.close();
					} catch ( IOException e ) {
					}
					socket = null;
				}
			}
		}
	}

	private void load() throws Exception {
		LogInstanceProxy.getInstance().start();
		ServiceManager.INSTANCE.start();
		FlowManager.INSTANCE.start();
		MessageSetConfigManager.getInstance().start();
		ProtocolConfigManager.getInstance().start();
		DMSProviderManager.getInstance().start();
		Runtime.getRuntime().addShutdownHook( new Thread( new Runnable() {

			@Override
			public void run() {
				try {
					ProtocolConfigManager.getInstance().stop();
					MessageSetConfigManager.getInstance().stop();
					FlowManager.INSTANCE.stop();
					ServiceManager.INSTANCE.stop();
				} catch ( Exception e ) {
					LOG.error( "System stopped error.", e );
					System.exit( -1 );
				}
			}
			
		} ) );
		final int _port = Env.getInstance().getProperty(
				Env.Keys.AWAIT_PORT, Integer.class );
		this.server = new StandardServer( _port );
	}

	private void startServer() {
		try {
			this.server.start();
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
			LOG.error( "The system of Sowing has some error, it will be stopped." );
			System.exit( -1 );
		}
	}

	private void stopServer() {
		try {
			this.server.stop();
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
			LOG.error( "Stop the system error, it will be stopped mandatory." );
			System.exit( -1 );
		}
	}
}
