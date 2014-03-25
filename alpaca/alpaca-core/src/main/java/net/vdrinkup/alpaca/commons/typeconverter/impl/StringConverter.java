/**
 * 
 */
package net.vdrinkup.alpaca.commons.typeconverter.impl;

import net.vdrinkup.alpaca.commons.typeconverter.AbstractConverter;

/**
 * @author bing
 *
 */
public class StringConverter extends AbstractConverter {
	@Override
	public Class< ? > defaultType() {
		return String.class;
	}

	protected Object convertToType( Object value ) throws Exception {
		String result = null;
		if ( value != null ) {
			result = value.toString();
		}
		return result;
	}

}
