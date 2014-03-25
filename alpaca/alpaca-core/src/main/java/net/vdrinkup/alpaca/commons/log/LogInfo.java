/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.commons.log;

import java.io.Serializable;

/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2013-12-14
 */
public class LogInfo implements Serializable {

	private static final long serialVersionUID = -962752923975748868L;
	
	private String name;
	
	private int level;

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel( int level ) {
		this.level = level;
	}

}
