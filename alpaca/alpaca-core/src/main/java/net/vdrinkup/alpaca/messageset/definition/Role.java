/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.definition;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-6
 */
@XmlEnum( String.class )
public enum Role {
	@XmlEnumValue( value = "embedded" )
	EMBEDDED;

}
