/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.jdbc.session;

import java.util.LinkedList;
import java.util.List;

import net.vdrinkup.alpaca.dms.Session;
import net.vdrinkup.alpaca.jdbc.transaction.JdbcTransactionObject;
import net.vdrinkup.alpaca.quality.transaction.Transaction;


/**
 * SQL批量处理更新方法
 * <p>
 * </p>
 * @author liubing Date Feb 19, 2014
 */
public class SQLBatchUpdateSessionImpl implements Session {

	private List< AbstractSQLSession > sessions = new LinkedList< AbstractSQLSession >();

	public List< AbstractSQLSession > getSessions() {
		return sessions;
	}

	public void addSession( AbstractSQLSession session ) {
		sessions.add( session );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < R > R run( Transaction transaction ) throws Exception {
		JdbcTransactionObject tx = ( ( JdbcTransactionObject ) transaction );
		Integer[] result = null;
		try {
			if ( tx.getConnection().getAutoCommit() ) {
				tx.getConnection().setAutoCommit( false );
			}
			List< Integer > effectCounts = new LinkedList< Integer >();
			for ( AbstractSQLSession session : sessions ) {
				effectCounts.add( ( Integer ) session.run( tx ) );
			}
			tx.getConnection().commit();
			result = new Integer[ effectCounts.size() ];
			effectCounts.toArray( result );
		} catch ( Exception e ) {
			tx.getConnection().rollback();
		}
		
		return ( R ) result;
	}

}
