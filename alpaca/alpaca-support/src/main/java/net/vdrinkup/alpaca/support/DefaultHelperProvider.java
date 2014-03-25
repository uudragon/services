/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.support;

import net.vdrinkup.alpaca.HelperProvider;
import net.vdrinkup.alpaca.commons.log.LogMBean;
import net.vdrinkup.alpaca.context.DataContextFactory;
import net.vdrinkup.alpaca.data.DataFactory;
import net.vdrinkup.alpaca.flow.FlowEngine;
import net.vdrinkup.alpaca.flow.FlowManager;
import net.vdrinkup.alpaca.service.ServiceManager;
import net.vdrinkup.alpaca.support.context.DefaultDataContextFactory;
import net.vdrinkup.alpaca.support.data.DefaultDataFactory;
import net.vdrinkup.alpaca.support.flow.DefaultFlowEngine;
import net.vdrinkup.alpaca.support.flow.DefaultFlowManager;
import net.vdrinkup.alpaca.support.service.DefaultServiceManager;



/**
 *                                                                                                                                                                                                                                                                                            
 * <p></p>
 * @author liubing
 * Date 2013-11-19
 */
public class DefaultHelperProvider extends HelperProvider {
	
	protected ServiceManager serviceManager;
	
	protected FlowManager flowManager;
	
	protected FlowEngine flowEngine;
	
	protected LogMBean logMBean;
	
	protected DataFactory dataFactory = new DefaultDataFactory();
	
	protected DataContextFactory contextFactory = new DefaultDataContextFactory();
			
	public DefaultHelperProvider() {
		init();
	}

	private void init() {
		serviceManager = new DefaultServiceManager();
		flowManager = new DefaultFlowManager();
		flowEngine = new DefaultFlowEngine();
		flowEngine.start();
	}

	@Override
	public ServiceManager getServiceManager() {
		return serviceManager;
	}

	@Override
	public FlowManager getFlowManager() {
		return flowManager;
	}

	@Override
	public FlowEngine getFlowEngine() {
		return flowEngine;
	}
	
	public LogMBean getLogMBean() {
		return logMBean;
	}

	@Override
	public DataFactory getDataFactory() {
		return dataFactory;
	}

	@Override
	public DataContextFactory getContextFactory() {
		return contextFactory;
	}

}
