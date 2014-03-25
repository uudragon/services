/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.processor.encoder;

/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-4
 */
public class JsonDoubleEncoder extends JsonNumberEncoder {

	@Override
	protected byte[] encode( Number number ) throws Exception {
		if ( number == null ) {
			number = getDefaultValue();
		} 
		byte[] result = String.valueOf( number.doubleValue() ).getBytes();
		return result;
	}

	@Override
	protected Number getDefaultValue() {
		return 0.00d;
	}

}
