/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.quality.transaction;

/**
 * 事务管理器接口
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-6
 */
public interface TransactionManager {
	/**
	 * 获得事务对象
	 * @return
	 */
	public Transaction getTransaction();
	/**
	 * 提交给定的事务对象
	 * @param transaction
	 * @throws TransactionException
	 */
	public void commit( Transaction transaction ) throws TransactionException;
	/**
	 * 回滚给定的事务对象
	 * @param transaction
	 */
	public void rollback( Transaction transaction );
	
}
