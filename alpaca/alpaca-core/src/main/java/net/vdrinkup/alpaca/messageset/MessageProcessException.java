/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset;

/**
 * 报文处理异常类
 * <p></p>
 * @author liubing
 * Date Jan 2, 2014
 */
public class MessageProcessException extends Exception {

	private static final long serialVersionUID = -8811530311016959617L;
	
	public MessageProcessException( String message ) {
		super( message );
	}

}
