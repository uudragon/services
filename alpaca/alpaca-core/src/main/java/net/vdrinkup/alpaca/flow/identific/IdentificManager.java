/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.identific;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.configuration.ConfigProcessor;
import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.identific.definition.FromDefinition;
import net.vdrinkup.alpaca.flow.identific.definition.IdentificDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 28, 2013
 */
public class IdentificManager {
	
	private static Logger LOG = LoggerFactory.getLogger( IdentificManager.class );
	
	private ConfigProcessor configProcessor = new IdentificConfigProcessor();
	
	private static volatile IdentificManager _instance;
	
	private IdentificManager() {
	}
	
	public static IdentificManager getInstance() {
		if ( _instance == null ) {
			synchronized ( IdentificManager.class ) {
				if ( _instance == null ) {
					_instance = new IdentificManager();
					_instance.init();
				}
			}
		}
		return _instance;
	}
	
	private void init() {
		final File file = new File( Env.getConfPath().concat( "identific.xml" ) );
		FileInputStream fis = null;
		IdentificDefinition definition = null;
		try {
			fis = new FileInputStream( file );
			definition = configProcessor.read( fis );
			registry.putAll( definition.toMap() );
		} catch ( Exception e ) {
			LOG.error( "IdentificManager parse configuration file error.", e );
		} finally {
			if ( fis != null ) {
				try {
					fis.close();
				} catch ( IOException e ) {
				}
				fis = null;
			}
			definition =  null;
		}
		LOG.info( "IdentificManager started successfully." );
	}
	
	private Map< String, FromDefinition > registry = new HashMap< String, FromDefinition >( 16 );

	public void identify( DataContext context ) {
		String fromName = context.getProperty( ContextConstants.FROM_NAME, String.class );
		FromDefinition fromDef = registry.get( fromName );
		if ( fromDef == null ) {
			throw new NoSuchElementException( "No such definition named [" + fromName + "]" );
		}
		try {
			fromDef.createProcessor().process( context );
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
		}
	}
}
