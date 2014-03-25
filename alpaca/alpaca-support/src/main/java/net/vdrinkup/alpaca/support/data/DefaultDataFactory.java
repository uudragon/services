/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.support.data;

import net.vdrinkup.alpaca.data.DataFactory;
import net.vdrinkup.alpaca.data.DataObject;

/**
 * 默认数据对象工厂
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-23
 */
public class DefaultDataFactory implements DataFactory {

	@Override
	public DataObject create() {
		return new MappedDataObject();
	}

	@Override
	public DataObject create( String type ) {
		return null;
	}

	@Override
	public < T > DataObject create( Class< T > clazz ) {
		return null;
	}

}
