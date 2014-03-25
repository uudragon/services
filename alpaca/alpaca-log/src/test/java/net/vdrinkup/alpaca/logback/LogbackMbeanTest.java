/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.logback;

import java.util.List;

import junit.framework.Assert;

import net.vdrinkup.alpaca.commons.log.LogInfo;
import net.vdrinkup.alpaca.logback.LogbackMBean;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import ch.qos.logback.classic.Level;

/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 13, 2013
 */
public class LogbackMbeanTest {
	@Before
	public void setup() {
		System.setProperty( "INSTALL_PATH", "/home/bjyfliubing/Test/server_conf" );
		System.setProperty( "PROJECT_NAME", "sowing" );
	}
	@Test
	public void testMBean() {
		LogbackMBean mBean = new LogbackMBean();
		Logger log =  LoggerFactory.getLogger( LogbackMbeanTest.class );
		Logger log1 = LoggerFactory.getLogger( "net.vdrinkup.alpaca.logback" );
		List< LogInfo > list = mBean.list();
		for ( LogInfo info : list ) {
			System.out.println( "logger name :" + info.getName() + ", level :" + info.getLevel() );
		}
		log1.debug( "log1 - debug----------------------------------------------" );
		log1.info( "log1 - info------------------------------------------------" );
		log.debug( "debug=======================================================" );
		log.info( "info========================================================" );
		Assert.assertTrue( log.isInfoEnabled() );
		mBean.setLevel( "com.jd.wms", Level.DEBUG_INT );
		Assert.assertTrue( log.isDebugEnabled() );
		log1.debug( "log1 - debug----------------------------------------------" );
		log1.info( "log1 - info------------------------------------------------" );
		log.debug( "debug=======================================================" );
		log.info( "info========================================================" );
		mBean.setLevel( "net.vdrinkup.alpaca.logback.LogbackMbeanTest", Level.ERROR_INT );
		log1.debug( "log1 - debug----------------------------------------------" );
		log1.info( "log1 - info------------------------------------------------" );
		log.debug( "debug=======================================================" );
		log.info( "info========================================================" );
	}

}
