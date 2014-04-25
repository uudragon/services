/**
 * 
 */
package net.vdrinkup.alpaca.http;

import net.vdrinkup.alpaca.protocol.Connection;
import net.vdrinkup.alpaca.protocol.definition.ProtocolConfig;

/**
 * @author pluto.bing.liu
 *
 */
public class HttpClientConnector implements Connection {
	
	private ProtocolConfig config;
	
	private boolean running;

	@Override
	public < T extends ProtocolConfig > void setConfig( T config ) {
		this.config = config;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends ProtocolConfig > T getConfig() {
		return ( T ) this.config;
	}

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public < T, R > R send( T t ) throws Exception {
		return null;
	}

	@Override
	public synchronized void start() throws Exception {
		
	}

	@Override
	public synchronized void stop() throws Exception {
		
	}

}
