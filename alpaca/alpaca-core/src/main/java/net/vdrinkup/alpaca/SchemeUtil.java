/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca;

/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-17
 */
public class SchemeUtil {
	
	public static String getSchemeName( String uri, String scheme ) {
		int index = uri.indexOf( SchemeConstants.SCHEME_SEPARATOR );
		String curScheme = uri.substring( 0, index );
		if ( ! curScheme.equalsIgnoreCase( scheme ) ) {
			throw new IllegalArgumentException( "Scheme error, expected '" + scheme + "' actual '" + curScheme + "'" );
		}
		return uri.substring( index + 1, uri.indexOf( SchemeConstants.LOCATION_SEPARATOR ) );
	}

}
