/**
 * 
 */
package net.vdrinkup.alpaca.commons.typeconverter.impl;

import net.vdrinkup.alpaca.commons.typeconverter.AbstractConverter;

/**
 * @author bing
 * 
 */
public class BooleanConverter extends AbstractConverter {

	@Override
	public Class< ? > defaultType() {
		return Boolean.class;
	}

	protected Object convertToType( Object value ) throws Exception {
		Boolean result = null;
		if ( value != null ) {
			if ( value.getClass().equals( defaultType() ) ) {
				result = ( Boolean ) value;
			} else {
				result = Boolean.valueOf( value.toString() );
			}
		}
		return result;
	}

}
