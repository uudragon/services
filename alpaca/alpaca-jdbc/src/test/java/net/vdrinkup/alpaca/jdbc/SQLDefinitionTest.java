/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.jdbc;

import java.io.InputStream;

import net.vdrinkup.alpaca.messageset.definition.MessageDefinition;
import net.vdrinkup.alpaca.sql.SQLMsgConfigProcessor;

import org.junit.Test;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 17, 2014
 */
public class SQLDefinitionTest {
	@Test
	public void testSQL() throws Exception {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream( "testSQL1.xml" );
		SQLMsgConfigProcessor cp = new SQLMsgConfigProcessor();
		MessageDefinition definition = cp.read( is );
	}

}
