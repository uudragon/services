/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.quality.transaction;

/**
 * 事务接口
 * <p>
 * </p>
 * @author pluto.bing.liu
 * Date 2013-12-6
 */
public interface Transaction {
	/**
	 * 事务回滚
	 * @throws Exception
	 */
	public void rollback() throws Exception;
	/**
	 * 事务提交
	 * @throws Exception
	 */
	public void commit() throws Exception;
	/**
	 * 设置事务的隔离级别
	 * @param level 事务隔离级别
	 */
	public void setIsoLation( int level ) throws Exception;
	/**
	 * 事务关闭
	 * @throws Exception
	 */
	public void close() throws Exception;
	/**
	 * 当前事务是否为只读事物
	 * @return
	 * @throws Exception
	 */
	public boolean isReadOnly() throws Exception;
	/**
	 * 当前事务是否是自动提交
	 * @return
	 * @throws Exception
	 */
	public boolean isAutoCommit() throws Exception;
	/**
	 * 当前事务是否关闭
	 * @return
	 */
	public boolean isClosed() throws Exception;
	/**
	 * 当前事务是否完成
	 * @return
	 */
	public boolean isCompleted();
	
}
