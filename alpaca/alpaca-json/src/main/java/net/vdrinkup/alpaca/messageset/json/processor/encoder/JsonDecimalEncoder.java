/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.processor.encoder;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.MessageNode;
import net.vdrinkup.alpaca.messageset.json.processor.JsonEncoder;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-17
 */
public class JsonDecimalEncoder implements JsonEncoder {

	@Override
	public void encode( DataContext context, MessageNode definition )
			throws Exception {
		final DataObject sdo = context.getIn();
		BigDecimal value = sdo.getBigDecimal( definition.getBinding() );
 		final ByteArrayOutputStream baos = context.getOut();
 		baos.write( value.toString().getBytes() );
	}

}
