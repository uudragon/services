/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统环境参数
 * <p>
 * 用于封装系统所需的环境参数。
 * </p>
 * 
 * @author liubing Date Oct 28, 2013
 */
@XmlRootElement( name = "env" )
@XmlAccessorType( XmlAccessType.PROPERTY )
public class Env {
	
	private static volatile Env instance;
	
	private static Logger LOG = LoggerFactory.getLogger( Env.class );
	
	private static String installPath;
	
	private static String confPath;

	private static String libPath;

	private static String logPath;
	
	static {
		installPath = System.getProperty( Keys.INSTALL_PATH );
		confPath = installPath + File.separator + "conf"
				+ File.separator;
		logPath = installPath + File.separator + "logs"
				+ File.separator;
		libPath = installPath + File.separator + "lib" + File.separator;
	}
	
	public static String getInstallPath() {
		return installPath;
	}

	public static String getConfPath() {
		return confPath;
	}

	public static String getLibPath() {
		return libPath;
	}

	public static String getLogPath() {
		return logPath;
	}
	
	private Map< String, Object > properties = new HashMap< String, Object >( 16 );

	private Env() {
	}

	public static Env getInstance() {
		if ( instance == null ) {
			synchronized ( Env.class ) {
				if ( instance == null ) {
					initProperties();
				}
			}
		}
		return instance;
	}

	private static void initProperties() {
		File file = new File( confPath + "env.xml" );
		try {
			JAXBContext context = JAXBContext.newInstance( Env.class );
			instance = ( Env ) context.createUnmarshaller().unmarshal( file );
			instance.properties.put( Keys.INSTALL_PATH, installPath );
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
			e.printStackTrace();
			System.exit( -1 );
		}
	}

	public Map< String, Object > getProperties() {
		return properties;
	}

	public Object getProperty( String key ) {
		return this.properties.get( key );
	}
	
	public < T > T getProperty( String key, Class< T > clazz ) {
		return clazz.cast( this.properties.get( key ) );
	}
	
	@XmlElement( name = "await.port" )
	public void setAwaitPort( int port ) {
		this.properties.put( Keys.AWAIT_PORT, port );
	}
	
	@XmlElement( name = "await.shutdown.timeout" )
	public void setAwaitShutdownTimeout( int timeout ) {
		this.properties.put( Keys.AWAIT_SHUTDOWN_TIMEOUT, timeout );
	}
	
	@XmlElement( name = "input.threadpool.size" )
	public void setInputThreadPoolSize( int size ) {
		this.properties.put( Keys.INPUT_THREAD_POOL_SIZE, size );
	}
	
	@XmlElement( name = "output.threadpool.size" )
	public void setOutputThreadPoolSize( int size ) {
		this.properties.put( Keys.OUTPUT_THREAD_POOL_SIZE, size );
	}
	
	@XmlElement( name = "execute.timeout" )
	public void setExecuteTimeout( int timeout ) {
		this.properties.put( Keys.EXECUTE_TIMEOUT, timeout );
	}
	
	@XmlElement( name = "engine.shutdown.timeout" )
	public void setEngineShutdownTimeout( int timeout ) {
		this.properties.put( Keys.ENGINE_SHUTDOWN_TIMEOUT, timeout );
	}

	public class Keys {
		
		private Keys() {
		}
		/**
		 * 安装路径
		 */
		public static final String INSTALL_PATH = "INSTALL_PATH";
		/**
		 * 监听端口
		 */
		public static final String AWAIT_PORT = "await.port";
		/**
		 * 终止程序超时时间
		 */
		public static final String AWAIT_SHUTDOWN_TIMEOUT = "await.shutdown.timeout";
		/**
		 * 接入线程池的容量
		 */
		public static final String INPUT_THREAD_POOL_SIZE = "input.threadpool.size";
		/**
		 * 接出线程池的容量
		 */
		public static final String OUTPUT_THREAD_POOL_SIZE = "output.threadpool.size";
		/**
		 * 流程执行超时时间
		 */
		public static final String EXECUTE_TIMEOUT = "execute.timeout";
		/**
		 * 流程引擎关闭超时
		 */
		public static final String ENGINE_SHUTDOWN_TIMEOUT = "engine.shutdown.timeout";
	
	}
	
}
