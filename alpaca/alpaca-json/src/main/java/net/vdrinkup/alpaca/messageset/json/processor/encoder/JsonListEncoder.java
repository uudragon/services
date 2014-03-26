/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.processor.encoder;

import java.io.ByteArrayOutputStream;
import java.util.List;

import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.MessageNode;
import net.vdrinkup.alpaca.messageset.json.JsonConstants;
import net.vdrinkup.alpaca.messageset.json.processor.JsonEncoder;
import net.vdrinkup.alpaca.messageset.json.processor.JsonEncoderSimpleFactory;


/**
 * Json报文数组编码器
 * <p>
 * </p>
 * 
 * @author pluto.bing.liu Date 2014-3-4
 */
public class JsonListEncoder implements JsonEncoder {
	
	private InnerObjectEncoder innerEncoder = new InnerObjectEncoder();

	@Override
	public void encode( DataContext context, MessageNode definition )
			throws Exception {
		if ( definition == null ) {
			throw new IllegalArgumentException(
					"The definition of field can not be null." );
		}
		final ByteArrayOutputStream baos = context.getOut();
		baos.write( JsonConstants.L_BRACKET );
		List< Object > list;
		if ( context.getIn() instanceof List ) {
			list = context.getIn();
		} else {
			DataObject sdo = context.getIn();
			list = sdo.getList( definition.getBinding() );
		}
		JsonEncoder encoder = null;
		if ( definition.isLeaf() ) {
			( ( ProcessorDefinition ) definition ).createProcessor().process(
					context );
		} else {
			if ( list != null ) {
				Object item = null;
				for ( int i = 0; i < list.size(); i++ ) {
					if ( i != 0 ) {
						baos.write( JsonConstants.COMMA );
					}
					item = ( Object ) list.get( i );
					context.setIn( item );
					if ( item instanceof DataObject ) {
						encoder = innerEncoder;
					} else if ( item instanceof List ) {
						encoder = JsonEncoderSimpleFactory.getInstance().getEncoder( "list" );
					}
					encoder.encode( context, definition );
				}
			}
		}
		context.setIn( list );
		baos.write( JsonConstants.R_BRACKET );
	}

	private class InnerObjectEncoder implements JsonEncoder {

		@Override
		public void encode( DataContext context, MessageNode definition ) throws Exception {
			final ByteArrayOutputStream baos = context.getOut();
			baos.write( JsonConstants.L_BRACES );
			MessageNode el = null;
			for ( int i = 0; i < definition.getElements().size(); i++ ) {
				if ( i != 0 ) {
					baos.write( JsonConstants.COMMA );
				}
				el = definition.getElements().get( i );
				( ( ProcessorDefinition ) el ).createProcessor().process( context );
			}
			baos.write( JsonConstants.R_BRACES );
		}
	}

}
