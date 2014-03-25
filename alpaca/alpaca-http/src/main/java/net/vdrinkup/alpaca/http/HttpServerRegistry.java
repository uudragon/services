package net.vdrinkup.alpaca.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.server.Server;

/**
 * Http Server(jetty)注册器
 * @author pluto.bing.liu
 *
 */
public class HttpServerRegistry {

	private static volatile HttpServerRegistry _instance;
	
	private HttpServerRegistry() {
	}
	
	public static HttpServerRegistry getInstance() {
		if ( _instance == null ) {
			synchronized ( HttpServerRegistry.class ) {
				if ( _instance == null ) {
					_instance = new HttpServerRegistry();
				}
			}
		}
		return _instance;
	}
	
	private Map< Integer, Entry > registry = new ConcurrentHashMap< Integer, Entry >( 16 );
	
	public void register( Entry entry ) {
		if ( entry == null ) {
			throw new IllegalArgumentException( "The entry can not be null." );
		}
		if ( registry.get( entry.getPort() ) != null ) {
			throw new IllegalArgumentException( "The entry of port [" + entry.getPort() + "] has exist." );
		}
		registry.put( entry.getPort(), entry );
	}
	
	public Entry getEntry( int port ) {
		return registry.get( port );
	}

	public void unregister( Entry entry ) {
		if ( entry == null ) {
			return ;
		}
		registry.remove( entry.getPort() );
	}
	
	public class Entry {
		/**
		 * 端口号
		 */
		private int port;
		/**
		 * 对应Jetty Server instance
		 */
		private Server server;
		/**
		 * 引用计数
		 */
		private int count;
		
		public Entry() {
		}
		
		public Entry( int port, Server server, int count ) {
			this.port = port;
			this.server = server;
			this.count = count;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public Server getServer() {
			return server;
		}

		public void setServer(Server server) {
			this.server = server;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
		
	}
	
}
