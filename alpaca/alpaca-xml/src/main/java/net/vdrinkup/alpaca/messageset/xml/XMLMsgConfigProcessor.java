/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;
import net.vdrinkup.alpaca.messageset.MessageSetConfigProcessor;
import net.vdrinkup.alpaca.messageset.definition.MessageDefinition;
import net.vdrinkup.alpaca.messageset.xml.definition.XMLInDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 26, 2013
 */
public class XMLMsgConfigProcessor extends MessageSetConfigProcessor {
	
	private static final String CONTEXT_PATH;
	
	static {
		StringBuilder builder = new StringBuilder( MESSAGESET_CONTEXT_PATH );
		builder.append( ":" ).append( XMLInDefinition.class.getPackage().getName() );
		CONTEXT_PATH = builder.toString();
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends AbstractDefinition > T read( InputStream is )
			throws Exception {
		final JAXBContext context = JAXBContext.newInstance( CONTEXT_PATH, this.getClass().getClassLoader() );
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		MessageDefinition definition = ( MessageDefinition ) unmarshaller.unmarshal( is );
		return ( T ) definition;
	}

	@Override
	public < T extends AbstractDefinition > void write( T config,
			OutputStream os ) throws Exception {
		final JAXBContext context = JAXBContext.newInstance( CONTEXT_PATH, this.getClass().getClassLoader() );
		final Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( config, os );
	}

}
