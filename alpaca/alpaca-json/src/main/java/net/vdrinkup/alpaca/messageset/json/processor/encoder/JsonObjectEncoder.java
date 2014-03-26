/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.processor.encoder;

import java.io.ByteArrayOutputStream;

import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.MessageNode;
import net.vdrinkup.alpaca.messageset.json.JsonConstants;
import net.vdrinkup.alpaca.messageset.json.processor.JsonEncoder;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-4
 */
public class JsonObjectEncoder implements JsonEncoder {
	
	public void encode( DataContext context, MessageNode definition ) throws Exception {
		DataObject sdo = context.getIn();
		if ( definition.getBinding() != null && !"".equals( definition.getBinding() ) ) {
			DataObject subSdo = sdo.getDataObject( definition.getBinding() );
			context.setIn( subSdo );
		}
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
		context.setIn( sdo );
	}

}
