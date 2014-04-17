/**
 * 
 */
package net.vdrinkup.alpaca.commons.typeconverter;

/**
 * @author pluto.bing.liu
 *
 */
public class ConversionException extends RuntimeException {

	private static final long serialVersionUID = 7578091773238892389L;

	public ConversionException() {
		super();
	}
	
	public ConversionException( String message ) {
		super( message );
	}
	
}
