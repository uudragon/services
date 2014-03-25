/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.processor.encoder;

import java.io.ByteArrayOutputStream;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.MessageNode;
import net.vdrinkup.alpaca.messageset.json.processor.JsonEncoder;


/**
 *
 * <p></p>
 * @author liubing
 * Date Mar 17, 2014
 */
public class JsonBooleanEncoder implements JsonEncoder {

	@Override
	public void encode( DataContext context, MessageNode definition )
			throws Exception {
		final DataObject sdo = context.getIn();
		Boolean value = sdo.getBoolean( definition.getBinding() );
 		final ByteArrayOutputStream baos = context.getOut();
		baos.write( value.toString().getBytes() );
	}

}
