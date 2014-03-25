/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.logback;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.commons.log.LogInfo;
import net.vdrinkup.alpaca.commons.log.LogMBean;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;


/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 13, 2013
 */
public class LogbackMBean implements LogMBean {
	
	private static final LoggerContext context = ( LoggerContext ) LoggerFactory.getILoggerFactory();

	static {
		String config = Env.getConfPath() + "logback.xml";
		File configFile = new File( config );
		if ( ! configFile.exists() ) {
			throw new RuntimeException(  "Logback Config File Parameter does not reference a file that exists." );
		} else if ( ! configFile.isFile() ) {
			throw new RuntimeException( "Logback Config File Parameter exists, but does not reference a file." );
		} else if ( ! configFile.canRead() ) {
			throw new RuntimeException( "Logback Config File exists and is a file, but can not be read." );
		} else {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext( context );
			context.reset();
			try {
				configurator.doConfigure( configFile );
				StatusPrinter.printInCaseOfErrorsOrWarnings( context );
			} catch ( JoranException e ) {
				throw new RuntimeException( e );
			}
		}
	}
	
	@Override
	public void setLevel( String name, int level ) {
		Logger logger = context.getLogger( name );
		logger.setLevel( Level.toLevel( level ) );
	}

	@Override
	public List< LogInfo > list() {
		List< LogInfo > logInfos = new LinkedList< LogInfo >();
		List< Logger > loggers = context.getLoggerList();
		for ( Logger l : loggers ) {
			LogInfo info = new LogInfo();
			info.setName( l.getName() );
			if ( l.getLevel() != null ) {
				info.setLevel( l.getLevel().levelInt );
			}
			logInfos.add( info );
		}
		return ( List< LogInfo > ) logInfos;
	}

}
