/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.data;

import net.vdrinkup.alpaca.HelperProvider;

/**
 * DataObject工厂
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-23
 */
public interface DataFactory {
	
	DataFactory INSTANCE = HelperProvider.INSTANCE.getDataFactory();
	/**
	 * 创建数据对象
	 * <p>
	 * 该方法创建一个空的数据对象。
	 * </p>
	 * @return
	 */
	public DataObject create();
	/**
	 * 按照给定的类型创建数据对象
	 * <p>
	 * 所创建的数据对象包含type所指定的metadata描述中包含的elements
	 * </p>
	 * @param type
	 * @return
	 */
	public DataObject create( String type );
	/**
	 * 按照给定的Class创建数据对象
	 * <p>
	 * 所创建的数据对象是clazz实例的包装器。
	 * </p>
	 * @param clazz
	 * @return
	 */
	public < T > DataObject create( Class< T > clazz );

}
