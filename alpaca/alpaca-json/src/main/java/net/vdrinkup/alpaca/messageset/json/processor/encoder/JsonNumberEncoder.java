/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.processor.encoder;

import java.io.ByteArrayOutputStream;

import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.MessageNode;
import net.vdrinkup.alpaca.messageset.json.processor.JsonEncoder;

/**
 * 
 * <p>
 * </p>
 * 
 * @author pluto.bing.liu Date 2014-3-4
 */
public abstract class JsonNumberEncoder implements JsonEncoder {

	protected Number defaultValue;

	@Override
	public void encode( DataContext context, MessageNode definition )
			throws Exception {
		final DataObject sdo = context.getIn();
		Number value = sdo.getNumber( definition.getBinding() );
		final ByteArrayOutputStream baos = context.getOut();
		final String charset = context.getProperty( ContextConstants.CHARSET,
				String.class );
		byte[] bytes = encode( value, charset );
		baos.write( bytes );
	}

	protected abstract byte[] encode( Number number, String charset )
			throws Exception;

	protected abstract Number getDefaultValue();

}
