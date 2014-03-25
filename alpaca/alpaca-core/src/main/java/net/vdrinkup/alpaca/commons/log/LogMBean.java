/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.commons.log;

import java.util.List;

/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2013-12-14
 */
public interface LogMBean {
		
	public abstract List<LogInfo> list();

	public abstract void setLevel(String paramString, int paramInt);

}
