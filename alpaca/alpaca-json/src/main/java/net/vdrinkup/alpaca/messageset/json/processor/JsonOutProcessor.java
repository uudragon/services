/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.processor;

import java.io.ByteArrayOutputStream;
import java.util.List;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.json.definition.JsonOutDefinition;
import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonListEncoder;
import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonObjectEncoder;


/**
 * JSON报文输出配置处理类
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-27
 */
public class JsonOutProcessor extends AbstractProcessor< JsonOutDefinition > {
	
	public JsonOutProcessor( JsonOutDefinition t ) {
		super( t );
	}

	@Override
	protected void handle( DataContext context ) throws Exception {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		context.setOut( baos );
		if ( getDefinition().getBinding() != null && !"".equals( getDefinition().getBinding() ) ) {
			final DataObject sdo = context.getIn();
			final Object obj = sdo.get( getDefinition().getBinding() );
			context.setIn( obj );
		}
		JsonEncoder encoder = null;
		if ( context.getIn() instanceof List ) {
			encoder = new JsonListEncoder();
		} else {
			encoder = new JsonObjectEncoder();
		}
		encoder.encode( context, getDefinition() );
		context.setOut( baos.toByteArray() );
	}

}
