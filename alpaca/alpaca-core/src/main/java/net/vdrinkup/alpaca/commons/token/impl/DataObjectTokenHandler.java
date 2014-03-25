/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.commons.token.impl;

import java.util.NoSuchElementException;

import net.vdrinkup.alpaca.commons.token.TokenHandler;
import net.vdrinkup.alpaca.data.DataObject;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-25
 */
public class DataObjectTokenHandler implements TokenHandler {
	
	private DataObject sdo;
	
	public DataObjectTokenHandler( DataObject sdo ) {
		this.sdo = sdo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dc.appengine.node.tools.TokenHandler#handleToken(java.lang.String)
	 */
	public String handleToken( String token ) throws Exception {
		String content = null;
		content = sdo.get( token ).toString();
		if ( content == null ) {
			throw new NoSuchElementException( "No such element named [" + token + "]" );
		}
		return content;
	}

}
