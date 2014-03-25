/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.jdbc.transaction;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicBoolean;

import net.vdrinkup.alpaca.quality.transaction.Transaction;


/**
 * JDBC事务对象
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-17
 */
public class JdbcTransactionObject implements Transaction {
	
	private Connection connection;
	
	private AtomicBoolean completed = new AtomicBoolean( false );
		
	public JdbcTransactionObject( Connection connection ) {
		this.connection = connection;
	}

	public void setConnection( Connection connection ) {
		this.connection = connection;
	}
	
	public Connection getConnection() {
		return connection;
	}

	@Override
	public void rollback() throws Exception {
		rollback : {
			if ( isClosed() || isCompleted() ) {
				break rollback;
			}
			if ( ! isAutoCommit() ) {
				connection.rollback();
			}
			close();
			completed.set( true );
		}
	}

	@Override
	public void commit() throws Exception {
		commit : {
			if ( isClosed() || isCompleted() ) {
				break commit;
			}
			if ( ! isAutoCommit() ) {
				connection.commit();
			}
			close();
			completed.set( true );
		}
	}

	@Override
	public void setIsoLation( int level ) throws Exception {
		this.connection.setTransactionIsolation( level );
	}

	@Override
	public void close() throws Exception {
		this.connection.close();
	}

	@Override
	public boolean isReadOnly() throws Exception {
		return this.connection.isReadOnly();
	}

	@Override
	public boolean isAutoCommit() throws Exception {
		return this.connection.getAutoCommit();
	}

	@Override
	public boolean isClosed() throws Exception {
		return this.connection.isClosed();
	}

	@Override
	public boolean isCompleted() {
		return completed.get();
	}

}
