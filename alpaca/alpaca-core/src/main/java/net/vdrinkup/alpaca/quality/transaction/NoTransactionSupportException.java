/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.quality.transaction;

/**
 * 无事务支持异常类
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-6
 */
public class NoTransactionSupportException extends Exception {

	private static final long serialVersionUID = 6139960661407199936L;

	public NoTransactionSupportException( String message ) {
		super( message );
	}

	public NoTransactionSupportException( String message, Throwable caused ) {
		super( message, caused );
	}
	
}
