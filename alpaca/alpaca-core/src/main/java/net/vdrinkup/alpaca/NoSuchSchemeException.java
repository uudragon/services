/**
 * 
 */
package net.vdrinkup.alpaca;

/**
 * 无对应Scheme异常
 * @author pluto.bing.liu
 *
 */
public class NoSuchSchemeException extends Exception {

	private static final long serialVersionUID = -2048851267781522559L;
	
	public NoSuchSchemeException() {
		super();
	}
	
	public NoSuchSchemeException( String message ) {
		super( message );
	}

}
