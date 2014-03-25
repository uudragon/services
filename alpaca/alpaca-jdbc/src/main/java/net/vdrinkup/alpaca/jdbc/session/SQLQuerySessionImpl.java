/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.jdbc.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.vdrinkup.alpaca.jdbc.transaction.JdbcTransactionObject;
import net.vdrinkup.alpaca.quality.transaction.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * SQL查询脚本实现类
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-17
 */
public class SQLQuerySessionImpl extends AbstractSQLSession {
	
	private static Logger LOG = LoggerFactory.getLogger( SQLQuerySessionImpl.class );

	public SQLQuerySessionImpl() {
	}
	
	@SuppressWarnings( "unchecked" )
	@Override
	public < R > R run( Transaction transaction ) throws Exception {
		Connection connection = ( ( JdbcTransactionObject ) transaction ).getConnection();
		PreparedStatement ps = connection.prepareStatement( script.toString() );
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Current SQL script is [{}]", script.toString() );
		}
		Object value;
		for ( int i = 0; i < parameters.size(); i++ ) {
			value = parameters.get( i ) ;
			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "Setting value of Parameter [{}] is [{}]", i + 1, value );
			}
			ps.setObject( i + 1, value );
		}
		ResultSet rs = ps.executeQuery();
		return ( R ) rs;
	}

}
