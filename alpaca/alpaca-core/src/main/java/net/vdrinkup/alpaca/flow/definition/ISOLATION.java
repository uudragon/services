/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.definition;

import java.sql.Connection;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 19, 2014
 */
@XmlEnum( String.class )
public enum ISOLATION {
	@XmlEnumValue( value = "ISOACTION_DEFAULT" )
	ISOLATION_DEFAULT( -1 ),
	@XmlEnumValue( value = "ISOLATION_READ_UNCOMMITTED" )
	ISOLATION_READ_UNCOMMITTED( Connection.TRANSACTION_READ_UNCOMMITTED ),
	@XmlEnumValue( value = "ISOLATION_READ_COMMITTED" )
	ISOLATION_READ_COMMITTED( Connection.TRANSACTION_READ_COMMITTED ),
	@XmlEnumValue( value = "ISOLATION_REPEATABLE_READ" )
	ISOLATION_REPEATABLE_READ( Connection.TRANSACTION_REPEATABLE_READ ),
	@XmlEnumValue( value = "ISOLATION_SERIALIZABLE" )
	ISOLATION_SERIALIZABLE( Connection.TRANSACTION_SERIALIZABLE );
	
	private int value;
	
	private ISOLATION( int value ) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
