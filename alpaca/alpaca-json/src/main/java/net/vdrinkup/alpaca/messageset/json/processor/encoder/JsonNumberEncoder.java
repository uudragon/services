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
import net.vdrinkup.alpaca.messageset.json.processor.JsonEncoder;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-4
 */
public abstract class JsonNumberEncoder implements JsonEncoder {
	
	protected Number defaultValue;

	@Override
	public void encode( DataContext context, MessageNode definition )
			throws Exception {
		final DataObject sdo = context.getIn();
		Number value = sdo.getNumber( definition.getBinding() );
 		final ByteArrayOutputStream baos = context.getOut();
 		byte[] bytes = encode( value );
 		baos.write( bytes );
	}
	
	protected abstract byte[] encode( Number number ) throws Exception;
	
	protected abstract Number getDefaultValue();

}
