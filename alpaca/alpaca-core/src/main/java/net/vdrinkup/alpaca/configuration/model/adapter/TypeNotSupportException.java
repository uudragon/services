/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model.adapter;

/**
 * 不支持转换类型异常
 * <p>
 * </p>
 * @author pluto.bing.liu Date 2013-12-13
 */
public class TypeNotSupportException extends Exception {

	private static final long serialVersionUID = 7538976866572725783L;

	public TypeNotSupportException( String message ) {
		super( message );
	}

}
