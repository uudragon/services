/**
 * 
 */
package net.vdrinkup.alpaca.commons.typeconverter.impl;

/**
 * @author bing
 *
 */
public class IntegerConverter extends NumeralConverter {
	@Override
	public Class< ? > defaultType() {
		return Integer.class;
	}

}
