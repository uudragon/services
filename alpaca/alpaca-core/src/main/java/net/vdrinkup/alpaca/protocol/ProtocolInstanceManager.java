/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.protocol;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import net.vdrinkup.alpaca.protocol.definition.ProtocolConfig;
import net.vdrinkup.alpaca.protocol.definition.SideEnum;
import net.vdrinkup.alpaca.protocol.plugin.ProtocolPlugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 协议实例管器
 * <p>
 * 该类用于保存运行时协议实例。
 * </p>
 * @author liubing
 * Date Nov 4, 2013
 */
public class ProtocolInstanceManager {
	
	private static Logger LOG = LoggerFactory.getLogger( ProtocolInstanceManager.class );
	
	public static volatile ProtocolInstanceManager _instance;
	
	private ProtocolInstanceManager() {
	}
	
	public static ProtocolInstanceManager getInstance() {
		if ( _instance == null ) {
			synchronized ( ProtocolInstanceManager.class ) {
				if ( _instance == null ) {
					_instance = new ProtocolInstanceManager();
				}
			}
		}
		return _instance;
	}

	private Map< String, Connection > registry = new ConcurrentHashMap< String, Connection >( 16 );
	
	/**
	 * 加载连接器
	 * <p>
	 * 此方法按给定的协议配置，读取{@link ProtocolPluginManager}中对应的配置，生成启动器实例。
	 * 如果当前配置设定了autoRun属性，则在生成实例后对启动该实例。
	 * </p>
	 * @param config 按给定的协议配置，加载连接器实例
	 * @throws Exception
	 */
	@SuppressWarnings( "unchecked" )
	public void load( ProtocolConfig config ) throws Exception {
		if ( registry.containsKey( config.getName() ) ) {
			throw new IllegalArgumentException( "The instance named [" + config.getName() + "] has exist." );
		}
		final ProtocolPlugin plugin = ProtocolPluginManager.getInstance().lookup( config.getProtocolName() );
		Class< Connection > clazz = null;
		if ( config.getSide().equals( SideEnum.SERVER ) ) {
			clazz = ( Class< Connection > ) Class.forName( plugin.getServerConnection().trim() );
		} else {
			clazz = ( Class< Connection > ) Class.forName( plugin.getClientConnection().trim() );
		}
		Connection connection = clazz.newInstance();
		connection.setConfig( config );
		registry.put( config.getName(), connection );
		if ( config.isAutoRun() ) {
			startConnection( config.getName() );
		}
	}
	
	public Map< String, Connection > getAllInstance() {
		return registry;
	}

	/**
	 * @return
	 */
	public int size() {
		return registry.size();
	}

	/**
	 * 按给定的名称查询对应的通讯链接器实例
	 * @param string
	 */
	public Connection lookup( String key ) {
		if ( ! registry.containsKey( key ) ) {
			throw new NoSuchElementException( "No such element named [" + key + "] exist." );
		}
		return registry.get( key );
	}
	
	/**
	 * 启动连接器，按给定的Key启动对应的Connection（如果该链接器存在的话）
	 * @param key 连接器名称
	 * @return 启动成功返回true，否则返回false
	 * @throws ConnectionException
	 */
	public boolean startConnection( String key ) throws ConnectionException {
		boolean result = false;
		try {
			Connection connection = lookup( key );
			connection.start();
			result = connection.isStartup();
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
			throw new ConnectionException( e );
		}
		return result;
	}
	
	/**
	 * 停止连接器，按给定的Key停止对应的Connection（如果该连接器存在的话）
	 * @param key 连接器名称
	 * @return 停止成功返回true，否则返回false
	 * @throws ConnectionException
	 */
	public boolean stopConnection( String key ) throws ConnectionException {
		boolean result = false;
		try {
			Connection connection = lookup( key );
			connection.stop();
			result = connection.isShutdown();
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
			throw new ConnectionException( e );
		}
		return result;
	}
	
}
