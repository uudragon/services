/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.jdbc;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;

import junit.framework.Assert;

import net.vdrinkup.alpaca.dms.DataSourceDefinition;
import net.vdrinkup.alpaca.jdbc.JdbcConfigProcessor;
import net.vdrinkup.alpaca.jdbc.JdbcDMSProvider;
import net.vdrinkup.alpaca.jdbc.definition.JdbcDataSourceDefinition;
import net.vdrinkup.alpaca.jdbc.transaction.JdbcTransactionObject;
import net.vdrinkup.alpaca.quality.transaction.Transaction;

import org.junit.Test;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-7
 */
public class JdbcClientConnectorTest {

	@Test
	public void testJdbc() throws Exception {
		URL url = JdbcClientConnectorTest.class.getClassLoader().getResource( "testSQL.xml" );
		JdbcConfigProcessor processor = new JdbcConfigProcessor();
		DataSourceDefinition definition = processor.read( url.openStream() );
		Assert.assertNotNull( definition );
		Assert.assertTrue( definition instanceof JdbcDataSourceDefinition );
		JdbcDMSProvider provider = new JdbcDMSProvider();
		provider.setDataSourceDefinition( definition );
		provider.start();
		Assert.assertNotNull( provider );
		Assert.assertTrue( provider.isStartup() );		
		Transaction transaction = provider.createTransaction(true);
		Connection connection = ( ( JdbcTransactionObject ) transaction ).getConnection();
		ResultSet rs = connection.prepareStatement( "select 1" ).executeQuery();
		System.out.println( rs.first() );
		System.out.println( rs.getString( 1 ) );
	}

}
