/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * 运行模式枚举类
 * <p>
 * 定义运行模式，包括同步、异步、伪异步模式
 * </p>
 * @author liubing
 * Date Nov 28, 2013
 */
@XmlEnum( String.class )
public enum Mode {
	@XmlEnumValue( value = "s" )
	SYNC,
	@XmlEnumValue( value = "a" )
	ASYNC,
	@XmlEnumValue( value = "p" )
	PSEUDO
}
