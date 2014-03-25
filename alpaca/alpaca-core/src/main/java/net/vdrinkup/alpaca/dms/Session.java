/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.dms;

import net.vdrinkup.alpaca.quality.transaction.Transaction;

/**
 * DMS执行会话
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-17
 */
public interface Session {
	/**
	 * 运行脚本
	 * <p>
	 * 运行当前脚本
	 * </p>
	 * @param script DMS执行脚本
	 * @throws Exception
	 */
	public < R > R run( Transaction transaction ) throws Exception;

}
