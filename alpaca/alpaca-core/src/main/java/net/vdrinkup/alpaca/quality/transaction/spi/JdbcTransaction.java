/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.quality.transaction.spi;

import java.sql.Connection;
import java.sql.SQLException;

import net.vdrinkup.alpaca.quality.transaction.Transaction;


/**
 * JDBC事务
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-6
 */
public interface JdbcTransaction extends Transaction {
	/**
	 * 获得JDBC链接器
	 * @return
	 */
	public Connection getConnection() throws SQLException;

}
