/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.jdbc.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import net.vdrinkup.alpaca.jdbc.transaction.JdbcTransactionObject;
import net.vdrinkup.alpaca.quality.transaction.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-17
 */
public class SQLUpdateSessionImpl extends AbstractSQLSession {
	
	private static Logger LOG = LoggerFactory.getLogger( SQLUpdateSessionImpl.class );
	
	private boolean isBatch;

	@SuppressWarnings( "unchecked" )
	@Override
	public < R > R run( Transaction transaction ) throws Exception {
		Connection connection = ( ( JdbcTransactionObject ) transaction ).getConnection();
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Current SQL script is [{}]", script.toString() );
			LOG.debug( "Current parameters are {}", parameters.toString() );
		}
		R result;
		if ( isBatch() ) {
			result = ( R ) executeBatch( connection );
		} else {
			result = ( R ) executeUpdate( connection );
		}
		
		return ( R ) result;
	}

	/**
	 * @param connection
	 */
	private Integer executeUpdate( Connection connection ) throws Exception {
		PreparedStatement ps = connection.prepareStatement( script.toString() );
		Object value;
		for ( int i = 0; i < parameters.size(); i++ ) {
			value = parameters.get( i ) ;
			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "Setting value of Parameter [{}] is [{}]", i + 1, value );
			}
			ps.setObject( i + 1, value );
		}
		Integer count = ps.executeUpdate();
		if ( ps != null ) {
			ps.close();
		}
		return count;
	}

	/**
	 * @param connection
	 */
	@SuppressWarnings( "unchecked" )
	private int[] executeBatch( Connection connection ) throws Exception {
		boolean autoCommit = connection.getAutoCommit();
		connection.setAutoCommit( false );
		PreparedStatement ps = connection.prepareStatement( script.toString() );
		List< Object > value;
		Object obj;
		for ( int i = 0; i < parameters.size(); i++ ) {
			value = ( List< Object > ) parameters.get( i );
			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "Setting value of Parameter [{}] is [{}]", i, value.toString() );
			}
			for ( int j = 0; j < value.size(); j++ ) {
				obj = value.get( j );
				ps.setObject( j + 1, obj );
			}
			ps.addBatch();
		}
		int[] counts = ps.executeBatch();
		if ( autoCommit ) {
			connection.commit();
			connection.setAutoCommit( autoCommit );
		}
		if ( ps != null ) {
			ps.close();
		}
		return counts; 
	}

	public boolean isBatch() {
		return isBatch;
	}

	public void setBatch( boolean isBatch ) {
		this.isBatch = isBatch;
	}
	
}
