/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.commons.log;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 *
 * <p></p>
 * @author liubing
 * Date Jan 14, 2014
 */
public class LogInstanceProxy implements LogMBean {
	
	private static final String RESOURCE_NAME = "META-INF/services/net.vdrinkup.alpaca.log";
	
	private static volatile LogInstanceProxy _instance;

	private LogMBean mBean;
	
	private LogInstanceProxy() {
	}
	
	public static LogInstanceProxy getInstance() {
		if ( _instance == null ) {
			synchronized ( LogInstanceProxy.class ) {
				if ( _instance == null ) {
					_instance = new LogInstanceProxy();
				}
			}
		}
		return _instance;
	}

	@SuppressWarnings( "unchecked" )
	public void start() throws Exception {
		final URL url = this.getClass().getClassLoader().getResource( RESOURCE_NAME );
		Properties properties = new Properties();
		try {
			properties.load( url.openStream() );
			String clazzName = properties.getProperty( "class" );
			Class< LogMBean > clazz = ( Class< LogMBean > ) Class.forName( clazzName, false, this.getClass().getClassLoader() );
			this.mBean = clazz.newInstance();
		} catch ( IOException e ) {
			throw e;
		}
	}

	@Override
	public List< LogInfo > list() {
		return this.mBean.list();
	}

	@Override
	public void setLevel( String paramString, int paramInt ) {
		this.mBean.setLevel( paramString, paramInt );
	}

}
