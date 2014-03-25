/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.commons.typeconverter.impl;

import java.math.BigDecimal;

/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-8
 */
public class BigDecimalConverter extends NumeralConverter {
	
	public static final String DECIMAL_SIMPLE_NAME = "decimal";

	@Override
	public Class< ? > defaultType() {
		return BigDecimal.class;
	}

}
