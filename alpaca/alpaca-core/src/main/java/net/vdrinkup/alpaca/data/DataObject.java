/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据对象接口
 * <p></p>
 * @author liubing
 * Date Feb 21, 2014
 */
public interface DataObject extends Cloneable {
	/**
	 * 按照path获得对应值
	 * @param path
	 * @return
	 */
	public Object get( String path );
	/**
	 * 设置path对应的值
	 * @param path
	 * @param value
	 */
	public void set( String path, Object value );
	/**
	 * 获得path对应的byte数组
	 * @param path
	 * @return
	 */
	public byte[] getBytes( String path );
	/**
	 * 设置path对应的byte数组
	 * @param path
	 * @param value
	 */
	public void setBytes( String path, byte[] value );
	/**
	 * 按Path获取对应的boolean值
	 * @param path
	 * @return
	 */
	public boolean getBoolean( String path );
	/**
	 * 设置path对应的boolean值
	 * @param path
	 * @param value
	 */
	public void setBoolean( String path, boolean value );
	/**
	 * 获得Path对应的int类型值
	 * @param path
	 * @return
	 */
	public int getInt( String path );
	/**
	 * 设置path对应的int类型值
	 * @param path
	 * @param value
	 */
	public void setInt( String path, int value );
	/**
	 * 获得path对应的long类型值
	 * @param path
	 * @return
	 */
	public long getLong( String path );
	/**
	 * 设置path对应的long类型值
	 * @param path
	 * @param value
	 */
	public void setLong( String path, long value );
	/**
	 * 获得path对应的Float类型值
	 * @param path
	 * @return
	 */
	public float getFloat( String path );
	/**
	 * 设置path对应的float类型值
	 * @param path
	 * @param value
	 */
	public void setFloat( String path, float value );
	/**
	 * 获得path对应的double类型值
	 * @param path
	 * @return
	 */
	public double getDouble( String path );
	/**
	 * 设置path对应的double类型值
	 * @param path
	 * @param value
	 */
	public void setDouble( String path, double value );
	/**
	 * 获得path对应的Date类型值
	 * @param path
	 * @return
	 */
	public Date getDate( String path );
	/**
	 * 设置path对应的Date类型值
	 * @param path
	 * @param date
	 */
	public void setDate( String path, Date date );
	/**
	 * 获得path对应的BigDecimal类型值
	 * @param path
	 * @return
	 */
	public BigDecimal getBigDecimal( String path );
	/**
	 * 设置path对应的BigDecimal类型值
	 * @param path
	 * @param value
	 */
	public void setBigDecimal( String path, BigDecimal value );
	/**
	 * 获得path对应的short类型值
	 * @param path
	 * @return
	 */
	public short getShort( String path );
	/**
	 * 设置path对应的short类型值
	 * @param path
	 * @param value
	 */
	public void setShort( String path, short value );
	
	/**
	 * 获得path对应的String类型值
	 * @param path
	 * @return
	 */
	public String getString( String path );
	/**
	 * 设置path对应的String值类型
	 * @param path
	 * @param value
	 */
	public void setString( String path, String value );
	/**
	 * 获得path对应的List对象
	 * @param path
	 * @return
	 */
	public < T > List< T > getList( String path );
	/**
	 * 设置path所对应的List对象
	 * @param path
	 * @param value
	 */
	public < T > void setList( String path, List< T > value );
	/**
	 * 获得path对应的DataObject对象
	 * @param path
	 * @return
	 */
	public DataObject getDataObject( String path );
	/**
	 * 设置path对应的DataObject对象
	 * @param path
	 * @param value
	 */
	public void setDataObject( String path, DataObject value );
	/**
	 * 获取path对应的Number类型值
	 * @param path
	 * @return
	 */
	public Number getNumber( String path );
	/**
	 * 设置path对应的Number类型值
	 * @param path
	 * @param number
	 */
	public void setNumber( String path, Number number );

}
