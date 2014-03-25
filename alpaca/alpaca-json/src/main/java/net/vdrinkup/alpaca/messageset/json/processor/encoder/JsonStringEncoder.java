/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.processor.encoder;

import java.io.ByteArrayOutputStream;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.MessageNode;
import net.vdrinkup.alpaca.messageset.json.JsonConstants;


/**
 * Json报文字符串编码器
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-4
 */
public class JsonStringEncoder extends JsonNullEncoder {

	@Override
	public void encode( DataContext context, MessageNode definition ) throws Exception {
		final DataObject sdo = context.getIn();
		String value = sdo.getString( definition.getBinding() );
 		final ByteArrayOutputStream baos = context.getOut();
		if ( value == null ) {
			super.encode( context, definition );
		} else {
			baos.write( JsonConstants.D_QUOTATION_MARK );
			baos.write( value.getBytes() );
			baos.write( JsonConstants.D_QUOTATION_MARK );
		}
	}

}
