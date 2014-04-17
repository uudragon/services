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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStartup() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public < R > R receive() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public < T, R > R send( T t ) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
