/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.vdrinkup.alpaca.configuration.ConfigProcessor;
import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;
import net.vdrinkup.alpaca.flow.definition.DefaultFlowDefinition;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2013-12-5
 */
public class FlowConfigProcessor implements ConfigProcessor {
	
	private static final String CONTEXT_PATH;
	
	static {
		final StringBuffer buffer = new StringBuffer( CONFIG_CONTEXT_PATH );
		buffer.append( ":" );
		buffer.append( DefaultFlowDefinition.class.getPackage().getName() );
		CONTEXT_PATH = buffer.toString();
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
		final FlowDefinition definition = ( FlowDefinition ) unmarshaller.unmarshal( is );
		return ( T ) definition;
	}

	@Override
	public < T extends AbstractDefinition > void write( T config, OutputStream os ) throws Exception {
		if ( config == null ) {
			throw new IllegalArgumentException( "The config can not be null." );
		}
		final JAXBContext context = JAXBContext.newInstance( CONTEXT_PATH, 
				this.getClass().getClassLoader() );
		final Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( config, os );
	}

}
