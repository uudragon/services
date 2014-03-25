/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.support;

import net.vdrinkup.alpaca.service.ServiceManager;
import net.vdrinkup.alpaca.support.service.DefaultServiceManager;

import org.junit.Before;
import org.junit.Test;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 25, 2013
 */
public class DefaultServiceManagerTest {
	@Before
	public void setUp() {
		System.setProperty( "INSTALL_PATH", "/home/bjyfliubing/Resources/sowing" );
	}
	@Test
	public void testManager() throws Exception {
		ServiceManager sm = new DefaultServiceManager();
		sm.start();
		System.out.println( sm.lookup( "test" ) );
	}

}
