/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;
import net.vdrinkup.alpaca.configuration.model.adapter.NodeConvertAdapter;
import net.vdrinkup.alpaca.messageset.MessageSetConfigProcessor;
import net.vdrinkup.alpaca.messageset.definition.MessageDefinition;
import net.vdrinkup.alpaca.sql.definition.SQLInDefinition;
import net.vdrinkup.alpaca.sql.definition.adapter.SQLNodeConvertAdapter;


/**
 * SQL报文配置处理器
 * <p>
 * </p>
 * 
 * @author liubing Date Jan 15, 2014
 */
public class SQLMsgConfigProcessor extends MessageSetConfigProcessor {

	private static final String CONTEXT_PATH;

	static {
		StringBuilder builder = new StringBuilder( MESSAGESET_CONTEXT_PATH );
		builder.append( ":" ).append(
				SQLInDefinition.class.getPackage().getName() );
		CONTEXT_PATH = builder.toString();
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends AbstractDefinition > T read( InputStream is )
			throws Exception {
		final JAXBContext context = JAXBContext.newInstance( CONTEXT_PATH, this
				.getClass().getClassLoader() );
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		final SQLNodeConvertAdapter adapter = new SQLNodeConvertAdapter();
		unmarshaller.setAdapter( NodeConvertAdapter.class, adapter );
		MessageDefinition definition = ( MessageDefinition ) unmarshaller
				.unmarshal( is );
		return ( T ) definition;
	}

	@Override
	public < T extends AbstractDefinition > void write( T config,
			OutputStream os ) throws Exception {
		final JAXBContext context = JAXBContext.newInstance( CONTEXT_PATH, this
				.getClass().getClassLoader() );
		final Marshaller marshaller = context.createMarshaller();
		final SQLNodeConvertAdapter adapter = new SQLNodeConvertAdapter();
		marshaller.setAdapter( NodeConvertAdapter.class, adapter );
		marshaller.marshal( config, os );
	}

}
