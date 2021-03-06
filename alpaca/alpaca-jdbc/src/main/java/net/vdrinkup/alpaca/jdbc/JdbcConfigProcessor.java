/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.jdbc;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.vdrinkup.alpaca.configuration.ConfigProcessor;
import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;
import net.vdrinkup.alpaca.jdbc.definition.JdbcDataSourceDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 12, 2014
 */
public class JdbcConfigProcessor implements ConfigProcessor {
	
	private static final String CONTEXT_PATH;
	
	static {
		final StringBuilder sb = new StringBuilder( 
				CONFIG_CONTEXT_PATH );
		sb.append( ":" ).append( JdbcDataSourceDefinition.class.getPackage().getName() );
		CONTEXT_PATH = sb.toString();
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends AbstractDefinition > T read( InputStream is )
			throws Exception {
		if ( is == null ) {
			throw new IllegalArgumentException( "The input stream can not be null." );
		}
		final JAXBContext context = JAXBContext.newInstance( CONTEXT_PATH, 
				this.getClass().getClassLoader() );
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		final JdbcDataSourceDefinition config = ( JdbcDataSourceDefinition ) unmarshaller.unmarshal( is );
		return ( T ) config;
	}

	@Override
	public < T extends AbstractDefinition > void write( T config,
			OutputStream os ) throws Exception {
		if ( config == null ) {
			throw new IllegalArgumentException( "The config can not be null." );
		}
		final JAXBContext context = JAXBContext.newInstance( CONTEXT_PATH, 
				this.getClass().getClassLoader() );
		final Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( config, os );
	}

}
