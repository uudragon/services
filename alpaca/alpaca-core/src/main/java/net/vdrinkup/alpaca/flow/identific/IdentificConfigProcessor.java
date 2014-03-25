/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.identific;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.vdrinkup.alpaca.configuration.ConfigProcessor;
import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;
import net.vdrinkup.alpaca.flow.identific.definition.IdentificDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 16, 2013
 */
public class IdentificConfigProcessor implements ConfigProcessor {
	
	private static final String CONFIG_PATH;
	
	static {
		StringBuffer path = new StringBuffer( CONFIG_CONTEXT_PATH ).append( ":" )
				.append( IdentificDefinition.class.getPackage().getName() );
		CONFIG_PATH = path.toString();
		path = null;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends AbstractDefinition > T read( InputStream is )
			throws Exception {
		final JAXBContext context = JAXBContext.newInstance( CONFIG_PATH, this.getClass().getClassLoader() );
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		final IdentificDefinition definition = ( IdentificDefinition ) unmarshaller.unmarshal( is );
		return ( T ) definition;
	}

	@Override
	public < T extends AbstractDefinition > void write( T config,
			OutputStream os ) throws Exception {
		final JAXBContext context = JAXBContext.newInstance( CONFIG_PATH );
		final Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( config, os );
	}

}
