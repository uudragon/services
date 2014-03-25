/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.commons.token.impl;

import java.util.NoSuchElementException;
import java.util.Properties;

import net.vdrinkup.alpaca.commons.token.TokenHandler;


/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 16, 2013
 */
public class PropertiesTokenHandler implements TokenHandler {
	
	private Properties properties;
	
	public PropertiesTokenHandler( Properties properties ) {
		this.properties = properties;
	}

	@Override
	public String handleToken( String token ) throws Exception {
		String content = null;
		if ( properties != null && properties.containsKey( token ) ) {
			content = properties.getProperty( token );
		} else  {
			throw new NoSuchElementException( "No such element named [" + token + "]" );
		}
		return content;
	}

}
