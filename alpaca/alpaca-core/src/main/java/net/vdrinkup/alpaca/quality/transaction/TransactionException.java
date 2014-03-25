/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.quality.transaction;

/**
 * 事务异常类
 * <p>
 * </p>
 * @author pluto.bing.liu Date 2014-2-6
 */
public class TransactionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TransactionException( String message ) {
		super( message );
	}

	public TransactionException( String message, Throwable cause ) {
		super( message, cause );
	}

}
