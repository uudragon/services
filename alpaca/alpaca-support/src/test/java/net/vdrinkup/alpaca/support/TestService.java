/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.support;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.service.InvokeException;
import net.vdrinkup.alpaca.service.Service;
import net.vdrinkup.alpaca.service.ServiceException;

/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 25, 2013
 */
public class TestService implements Service {
	
	public TestService() {
		System.out.println( "New atomic instance has been created." );
	}

	@Override
	public void invoke( DataContext context ) throws InvokeException {
		System.out.println( "=========================run here, " + this.getClass().getName() );
	}

	@Override
	public void start() throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStartup() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isShutdown() {
		// TODO Auto-generated method stub
		return false;
	}

}
