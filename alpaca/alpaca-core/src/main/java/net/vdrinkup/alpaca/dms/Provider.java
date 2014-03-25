/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.dms;

import net.vdrinkup.alpaca.quality.transaction.Transaction;

/**
 * 数据中介服务提供者接口
 * <p>
 * 定义数据中介服务提供者包含的方法
 * </p>
 * @author liubing
 * Date Feb 12, 2014
 */
public interface Provider {
	/**
	 * 设置数据源配置 
	 * @return
	 */
	public void setDataSourceDefinition( DataSourceDefinition definition );
	/**
	 * 获取数据源配置
	 * @return
	 */
	public DataSourceDefinition getDataSourceDefinition();
	/**
	 * 启动方法
	 * @throws Exception
	 */
	public void start() throws Exception;
	/**
	 * 停止方法
	 * @throws Exception
	 */
	public void stop() throws Exception;
	/**
	 * 是否启动
	 * @return
	 * @throws Exception
	 */
	public boolean isStartup() throws Exception;
	/**
	 * 是否停止
	 * @return
	 * @throws Exception
	 */
	public boolean isShutdown() throws Exception;
	/**
	 * 获得事务
	 * @param autoCommit 是否自动提交
	 * @return
	 */
	public Transaction createTransaction( boolean autoCommit ) throws Exception;
}
