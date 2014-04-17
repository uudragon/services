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
import net.vdrinkup.alpaca.messageset.MessageNode;
import net.vdrinkup.alpaca.messageset.json.processor.JsonEncoder;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-5
 */
public class JsonNullEncoder implements JsonEncoder {
	
	public static final String NULL = "null";

	@Override
	public void encode( DataContext context, MessageNode definition )
			throws Exception {
 		final ByteArrayOutputStream baos = context.getOut();
 		final String charset = context.getProperty( ContextConstants.CHARSET, String.class );
		baos.write( NULL.getBytes( charset ) );
	}

}
