/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration;

/**
 * 无效配置异常类
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-3
 */
public class IllegalDefinitionException extends Exception {

	private static final long serialVersionUID = 9124113561912141219L;
	
	public IllegalDefinitionException( String message ) {
		super( message );
	}

}
