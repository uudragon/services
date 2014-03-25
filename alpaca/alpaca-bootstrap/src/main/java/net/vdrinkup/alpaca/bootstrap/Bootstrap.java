/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.bootstrap;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 系统独立启动器
 * <p>
 * </p>
 * @author liubing Date Dec 12, 2013
 */
public class Bootstrap {
	
	private static final String START = "START";

	private static final String STOP = "STOP";
	
	private static Bootstrap daemon;

	private ClassLoader commonsLoader;
	
	private ClassLoader alpacaLoader;
		
	private Object alpacaDaemon;
	
	private ClassLoader createClassLoader( URL[] urls, ClassLoader parent ) {
		URLClassLoader newLoader = null;
		if ( parent != null ) {
			newLoader = new URLClassLoader( urls, parent );
		} else {
			newLoader = new URLClassLoader( urls );
		}
		return newLoader;
	}
	
	public void init() {
		String installPath = getSystemProperty( "INSTALL_PATH" );
		if ( installPath == null || "".equals( installPath ) ) {
			installPath = getCurrentPath();
		}
		System.out.println( "INFO:Current install path is [".concat( installPath ).concat( "]" ) );
		setSystemProperty( "INSTALL_PATH", installPath );
		final String libPath = installPath + File.separator + "lib";
		final File libDir = new File( libPath );
		if ( ! libDir.exists() ) {
			System.err.println( "ERROR:Can not find the lib directory." );
			System.exit( -1 );
		} else if ( libDir.isFile() ) {
			System.err.println( "ERROR:The lib is not a directory." );
			System.exit( -1 );
		}
		try {
			createClassLoaders( libDir );
			createAlpacaDaemon();
		} catch ( Exception e ) {
			System.err.println( "ClassLoader created error." );
			error( e );
		}
	}
	
	/**
	 * 
	 */
	private void createAlpacaDaemon() throws Exception {
		final Class< ? > clazz = Class.forName( "net.vdrinkup.alpaca.startup.Startup", false, alpacaLoader );
		alpacaDaemon = clazz.newInstance();
	}

	/**
	 * @param libDir
	 */
	private void createClassLoaders( File libDir ) throws Exception {
		File[] files = libDir.listFiles();
		List< URL > commonsURLs = new LinkedList< URL >();
		List< URL > alpacaURLs = new LinkedList< URL >();
		JarFile jarFile = null;
		for ( File file : files ) {
			if ( file.getName().endsWith( ".jar" ) ) {
				jarFile = new JarFile( file );
				JarEntry entry = jarFile.getJarEntry( "META-INF/services/com/jd/wms/sowing/component" );
				if ( entry == null ) {
					commonsURLs.add( file.toURI().toURL() );
				} else {
					alpacaURLs.add( file.toURI().toURL() );
				}
			}
		}
		final URL[] commonsArray = new URL[ commonsURLs.size() ];
		commonsURLs.toArray( commonsArray );
		final URL[] alpacaArray = new URL[ alpacaURLs.size() ];
		alpacaURLs.toArray( alpacaArray );
		commonsLoader = createClassLoader( commonsArray, ClassLoader.getSystemClassLoader() );
		if ( alpacaArray.length > 0 ) {
			alpacaLoader = createClassLoader( alpacaArray, commonsLoader );
		} else {
			alpacaLoader = commonsLoader;
		}
	}
	
	/**
	 * @return
	 */
	private String getCurrentPath() {
		final SecurityManager sm = System.getSecurityManager();
		if ( sm != null ) {
			sm.checkPermission( new RuntimePermission( "getProtectionDomain" ) );
		}
		final URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
		File codeSource = null;
		try {
			codeSource = new File( url.toURI() );
		} catch ( URISyntaxException e ) {
			error( e );
		}
		return codeSource.getParentFile().getAbsolutePath();
	}

	/**
	 * @param string
	 */
	private String getSystemProperty( String key ) {
		return System.getProperty( key );
	}

	/**
	 * @param installPath
	 * @param string
	 */
	private void setSystemProperty( String key, String value ) {
		System.setProperty( key, value );
	}

	/**
	 * @param e
	 */
	private void error( Exception e ) {
		e.printStackTrace( System.err );
		System.err.println( "Runtime start error, will be Interrupted." );
		System.exit( -1 );
	}
	
	public static void main( String... args ) throws Exception {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.init();
		daemon = bootstrap;
		processCommand( args, daemon );
	}

	/**
	 * @param args
	 * @param bootstrap
	 */
	private static void processCommand( String[] args, Bootstrap bootstrap ) {
		String command = "START";
		if ( args != null ) {
			command = args[ args.length - 1 ];
		}
		if ( START.equalsIgnoreCase( command ) ) {
			bootstrap.start();
		} else if ( STOP.equalsIgnoreCase( command ) ) {
			bootstrap.stop();
		} else {
			System.out.println( "Current system can not supported command [" + args[ 0 ] + "]" );
			System.exit( -1 );
		}
	}
	
	/**
	 * @throws Exception 
	 * @throws ] 
	 * 
	 */
	private void stop() {
		invoke( alpacaDaemon.getClass(), "stop", ( Class< ? >[] ) null, alpacaDaemon, ( Object[] ) null );
	}

	/**
	 * @throws Exception 
	 * @throws SecurityException 
	 * 
	 */
	private void start() {
		invoke( alpacaDaemon.getClass(), "start", ( Class< ? >[] ) null, alpacaDaemon, ( Object[] ) null );
	}
	
	private void invoke( Class< ? extends Object > clazz, String methodName, Class< ? extends Object >[] paramTypes, Object obj, Object... params ) {
		Method method;
		try {
			method = clazz.getDeclaredMethod( methodName, paramTypes );
			method.invoke( alpacaDaemon, params );
		} catch ( Exception e ) {
			error( e );
		}
	}

}
