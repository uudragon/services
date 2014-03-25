/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.dms;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.service.InvokeException;
import net.vdrinkup.alpaca.service.ProxyService;
import net.vdrinkup.alpaca.service.ServiceException;

/**
 * DMS代理服务
 * <p></p>
 * @author liubing
 * Date Feb 13, 2014
 */
public class DMSProxyService implements ProxyService {

	@Override
	public void invoke( DataContext context ) throws InvokeException {
	}

	@Override
	public void start() throws ServiceException {
		
	}

	@Override
	public void stop() throws ServiceException {
		
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
