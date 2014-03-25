/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca;

/**
 * 管理器接口
 * <p>
 * 定义所有管理器所包含的公共方法
 * </p>
 * @author liubing
 * Date Nov 25, 2013
 */
public interface Manager {
	/**
	 * 管理器启动方法
	 * <p>
	 * 该方法用于启动管理器。启动管理器包括初始化其内部相关属性。
	 * 在启动期间，如出现异常，则会将该异常抛出并终止管理器的启动。
	 * </p>
	 * @throws Exception 启动过程中出现的异常
	 */
	public void start() throws Exception;
	/**
	 * 管理器停止方法
	 * <p>
	 * 该方法用于停止管理器。停止管理器包括销毁其内部已经初始化的属性实例。
	 * 如果停止过程中出现异常，则强行停止该管理器实例并销毁其内部属性。
	 * </p>
	 * @throws Exception 停止过程中出现的异常
	 */
	public void stop() throws Exception;
	/**
	 * 获取管理器启动状态
	 * @return 如果管理器实例已经启动则返回true;否则，返回false。
	 */
	public boolean isStartup();
	/**
	 * 获取管理器停止状态
	 * @return  如果管理器实例已经停止则返回true;否则，返回false；
	 */
	public boolean isShutdown();

}
