/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.processor;

import java.awt.List;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonBooleanEncoder;
import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonDateEncoder;
import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonDecimalEncoder;
import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonDoubleEncoder;
import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonFloatEncoder;
import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonIntEncoder;
import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonListEncoder;
import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonLongEncoder;
import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonNullEncoder;
import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonObjectEncoder;
import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonShortEncoder;
import net.vdrinkup.alpaca.messageset.json.processor.encoder.JsonStringEncoder;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-4
 */
public class JsonEncoderSimpleFactory {
	
	private static final JsonEncoderSimpleFactory INSTANCE;
	
	private Map< String, JsonEncoder > encoders = new HashMap< String, JsonEncoder >( 16 );
	
	static {
		INSTANCE = new JsonEncoderSimpleFactory();
		INSTANCE.encoders.put( String.class.getSimpleName().toLowerCase(), new JsonStringEncoder() );
		INSTANCE.encoders.put( List.class.getSimpleName().toLowerCase(), new JsonListEncoder() );
		INSTANCE.encoders.put( Object.class.getSimpleName().toLowerCase(), new JsonObjectEncoder() );
		INSTANCE.encoders.put( Integer.class.getSimpleName().toLowerCase(), new JsonIntEncoder() );
		INSTANCE.encoders.put( int.class.getSimpleName().toLowerCase(), new JsonIntEncoder() );
		INSTANCE.encoders.put( Float.class.getSimpleName().toLowerCase(), new JsonFloatEncoder() );
		INSTANCE.encoders.put( Double.class.getSimpleName().toLowerCase(), new JsonDoubleEncoder() );
		INSTANCE.encoders.put( Short.class.getSimpleName().toLowerCase(), new JsonShortEncoder() );
		INSTANCE.encoders.put( Long.class.getSimpleName().toLowerCase(), new JsonLongEncoder() );
		INSTANCE.encoders.put( JsonNullEncoder.NULL, new JsonNullEncoder() );
		INSTANCE.encoders.put( Boolean.class.getSimpleName().toLowerCase(), new JsonBooleanEncoder() );
		INSTANCE.encoders.put( BigDecimal.class.getSimpleName().toLowerCase(), new JsonDecimalEncoder() );
		INSTANCE.encoders.put( JsonDecimalEncoder.SIMPLE_NAME.toLowerCase(), new JsonDecimalEncoder() );
		INSTANCE.encoders.put( Date.class.getSimpleName().toLowerCase(), new JsonDateEncoder() );
	}
	
	private JsonEncoderSimpleFactory() {
	}

	/**
	 * @return
	 */
	public static JsonEncoderSimpleFactory getInstance() {
		return INSTANCE;
	}

	public JsonEncoder getEncoder( String type ) {
		JsonEncoder encoder = null;
		if ( type == null ) {
			type = String.class.toString();
		}
		encoder = encoders.get( type.toLowerCase() );
		if ( encoder == null ) {
			encoder = encoders.get( String.class.getSimpleName().toLowerCase() );
		}
		return encoder;
	}
	
}
