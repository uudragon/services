/**
 * 
 */
package net.vdrinkup.alpaca.commons.typeconverter.impl;


/**
 * @author pluto.bing.liu
 *
 */
public class ShortConverter extends NumeralConverter {

	@Override
	public Class< ? > defaultType() {
		return Short.class;
	}

}
