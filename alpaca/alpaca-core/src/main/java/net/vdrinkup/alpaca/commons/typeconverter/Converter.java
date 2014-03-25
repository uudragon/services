/**
 * 
 */
package net.vdrinkup.alpaca.commons.typeconverter;

/**
 * @author bing
 *
 */
public interface Converter {

	public Object convert( Object object );
	
	public Class< ? > defaultType();
	
}
