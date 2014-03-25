/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 26, 2013
 */
public class FlowNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2433962671488465552L;

	public FlowNotFoundException( String message ) {
		super( message );
	}
	
}
