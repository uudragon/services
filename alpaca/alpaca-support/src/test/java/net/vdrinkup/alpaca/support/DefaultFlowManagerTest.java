/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.support;

import junit.framework.Assert;

import net.vdrinkup.alpaca.context.DataContextFactory;
import net.vdrinkup.alpaca.flow.FlowDefinition;
import net.vdrinkup.alpaca.flow.FlowManager;
import net.vdrinkup.alpaca.service.ServiceManager;

import org.junit.Before;
import org.junit.Test;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 26, 2013
 */
public class DefaultFlowManagerTest {
	
	@Before
	public void setUp() {
		System.setProperty( "INSTALL_PATH", "/home/bjyfliubing/Resources/sowing" );
		ServiceManager sm = ServiceManager.INSTANCE;
		try {
			sm.start();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	@Test
	public void testManager() throws Exception {
		FlowManager fm = FlowManager.INSTANCE;
		fm.start();
		Assert.assertNotNull( fm.lookup( "test" ) );
		FlowDefinition fd = fm.lookup( "test" );
		try {
			fd.createProcessor().process( DataContextFactory.INSTANCE.create() );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

}
