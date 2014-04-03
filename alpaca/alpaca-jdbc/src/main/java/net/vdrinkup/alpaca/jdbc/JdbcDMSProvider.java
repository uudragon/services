/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.jdbc;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.vdrinkup.alpaca.commons.typeconverter.Converter;
import net.vdrinkup.alpaca.commons.typeconverter.TypeConverterSimpleFactory;
import net.vdrinkup.alpaca.configuration.model.PropertyDefinition;
import net.vdrinkup.alpaca.dms.DataSourceDefinition;
import net.vdrinkup.alpaca.dms.Provider;
import net.vdrinkup.alpaca.jdbc.transaction.JdbcTransactionObject;
import net.vdrinkup.alpaca.quality.transaction.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 12, 2014
 */
public class JdbcDMSProvider implements Provider {
	
	private static Logger LOG = LoggerFactory.getLogger( JdbcDMSProvider.class );
	
	private boolean running;
	
	private DataSource dataSource;
	
	private DataSourceDefinition definition;
	
	@Override
	public void setDataSourceDefinition( DataSourceDefinition definition ) {
		this.definition = definition;
	}

	@Override
	public DataSourceDefinition getDataSourceDefinition() {
		return definition;
	}

	@Override
	public synchronized void start() throws Exception {
		if ( isStartup() ) {
			LOG.warn( "The instance of [{}] has been started.", getDataSourceDefinition().getName() );
			return ;
		}
		dataSource = new ComboPooledDataSource( null );
		( ( ComboPooledDataSource ) dataSource ).setJdbcUrl( getDataSourceDefinition().getUri() );
		Class< ComboPooledDataSource > clazz = ComboPooledDataSource.class;
		final List< PropertyDefinition > properties = getDataSourceDefinition().getProperties();
		final Map< String, Method > methods = new HashMap< String, Method >( 16 );
		for ( Method method : clazz.getMethods() ) {
			if ( method.getName().indexOf( "set" ) != -1 ) {
				methods.put( method.getName(), method );
			}
		}
		String propertyName;
		String methodName;
		Method method;
		Converter converter = null;
		if ( properties != null ) {
			for ( PropertyDefinition property : properties ) {
				propertyName = property.getName().substring( 0, 1 ).toUpperCase() 
						+ property.getName().substring( 1 );
				methodName = "set" + propertyName;
				method = methods.get( methodName );
				Class< ? > typeClazz = method.getParameterTypes()[ 0 ];
				converter = TypeConverterSimpleFactory.getInstance().getConverter( typeClazz.getSimpleName() );
				method.invoke( dataSource, new Object[]{ converter.convert( property.getValue() ) } );
			}
		}
		running = true;
		LOG.info( "The instance of [{}] is started successfully.", getDataSourceDefinition().getName() );
	}

	@Override
	public synchronized void stop() throws Exception {
		if ( isShutdown() ) {
			LOG.warn( "The instance of [{}] has been stopped.", getDataSourceDefinition().getName() );
			return ;
		}
		running = false;
		if ( dataSource == null ) {
			return ;
		}
		( ( ComboPooledDataSource ) dataSource ).close();
		dataSource = null;
		LOG.info( "The instance of [{}] is stopped successfully.", getDataSourceDefinition().getName() );
	}

	@Override
	public boolean isStartup() throws Exception {
		return running;
	}

	@Override
	public boolean isShutdown() throws Exception {
		return ! running;
	}

	@Override
	public Transaction createTransaction( boolean autoCommit ) throws Exception {
		Connection conn = dataSource.getConnection();
		conn.setAutoCommit( autoCommit );
		return new JdbcTransactionObject( conn );
	}

}
