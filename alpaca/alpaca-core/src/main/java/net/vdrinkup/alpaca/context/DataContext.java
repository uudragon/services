/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.context;

import java.io.Serializable;
import java.util.Map;

import net.vdrinkup.alpaca.flow.FlowDefinition;
import net.vdrinkup.alpaca.quality.transaction.Transaction;


/**
 * 数据交换上下文接口
 * @author liubing
 * Date Feb 21, 2014
 */
public interface DataContext extends Serializable {
	/**
	 * 上下文附件
	 * @return
	 */
	public < T > T getAttach();
	/**
	 * 获得Context的唯一标识号（ID）
	 * @return
	 */
	public String getId();
	/**
	 * 获得上下文状态
	 * @return 当前上下文的状态{@link ContextStatus}
	 */
	public ContextStatus getStatus();
	/**
	 * 设置上下文状态
	 * @param status 当前上下文状态
	 */
	public void setStatus( ContextStatus status );
	/**
	 * 当前获取Context全部属性
	 * @return
	 */
	public Map< String, Object > getProperties();
	/**
	 * 向Context中添加属性
	 * <p>
	 * 添加属性时，相同Key制定的value兼备新的value所覆盖。不允许空键（Null）。
	 * </>
	 * @param key
	 * @param value
	 */
	public void setProperty( String key, Object value );
	/**
	 * 获取key对应的属性值
	 * <p>如果当前无key对应的属性值，则返回NULL</p>
	 * @param key
	 * @return
	 */
	public Object getProperty( String key );
	/**
	 * 获取Key对应的属性值并转换成Class所指定的类型
	 * @param key
	 * @param clazz
	 * @return
	 */
	public < T > T getProperty( String key, Class< T > clazz );
	/**
	 * 获取Key对应的属性值
	 * <p>
	 * 如果当前key无对应属性值，则返回defaultValue
	 * </p>
	 * @param key
	 * @param clazz
	 * @param defaultValue
	 * @return
	 */
	public < T > T getProperty( String key, Class< T > clazz, T defaultValue );
	/**
	 * 删除key指定的属性值
	 * @param key
	 * @return
	 */
	public Object removeProperty( String key );
	/**
	 * 设置当前上下文异常
	 * @param e
	 */
	public void setException( Throwable t );
	/**
	 * 获得当前上下文异常
	 */
	public Throwable getException();
	/**
	 * 返回当前上下文是否具有事务
	 * @return
	 */
	public boolean isTransacted();
	/**
	 * 设置当前上下文是否具有事务
	 * @param isTransacted
	 */
	public void setTransacted( boolean isTransacted );
	/**
	 * 获得当前事务
	 * @return
	 */
	public Transaction getTransaction();
	/**
	 * 设置当前事务
	 * @param transaction
	 */
	public void setTransaction( Transaction transaction );
	/**
	 * 设置流程定义
	 * <p>
	 * 设置当前需要执行的流程定义
	 * </p>
	 * @param definition
	 */
	public void setFlowDefinition( FlowDefinition definition );
	/**
	 * 获取流程定义
	 * <p>
	 * 获取当前需要执行的流程定义
	 * </p>
	 * @return
	 */
	public FlowDefinition getFlowDefinition();
	/**
	 * 拷贝当前上下文
	 * <p>
	 * 拷贝上下文不包括当前上下文中所包含的的事务信息。
	 * </p>
	 * @return
	 */
	public DataContext copy();
	
	public < T > T getIn();
	
	public < T > void setIn( T t );
	
	public boolean hasOut();
	
	public < T > T getOut();
	
	public < T > void setOut( T t );
	
}
