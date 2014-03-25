/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.ws;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.vdrinkup.alpaca.configuration.ConfigProcessor;
import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;
import net.vdrinkup.alpaca.protocol.definition.ProtocolConfig;
import net.vdrinkup.alpaca.ws.config.WsProtocolConfig;



/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 1, 2013
 */
public class WsConfigProcessor implements ConfigProcessor {
	
	private static final String CONTEXT_PATH;
	
	static {
		final StringBuilder sb = new StringBuilder( 
				CONFIG_CONTEXT_PATH );
		sb.append( ":" ).append( WsProtocolConfig.class.getPackage().getName() );
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
		final ProtocolConfig config = ( WsProtocolConfig ) unmarshaller.unmarshal( is );
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
