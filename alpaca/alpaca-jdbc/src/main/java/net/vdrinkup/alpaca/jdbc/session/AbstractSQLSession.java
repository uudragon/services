/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.jdbc.session;

import java.io.CharArrayWriter;
import java.util.LinkedList;
import java.util.List;

import net.vdrinkup.alpaca.dms.Session;

/**
 * 抽象SQL脚本
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-17
 */
public abstract class AbstractSQLSession implements Session {
	
	protected CharArrayWriter script = new CharArrayWriter();
	
	protected List< Object > parameters = new LinkedList< Object >();
	
	protected List< Integer > targetTypes = new LinkedList< Integer >();
	/**
	 * 是否写入脚本，此标识用于批量插入时控制后续脚本的写入
	 */
	protected boolean writeScript = true;
	
	public CharArrayWriter getScript() {
		return script;
	}

	public List< Object > getParameters() {
		return parameters;
	}

	public List< Integer > getTargetTypes() {
		return targetTypes;
	}

	public void setTargetTypes( List< Integer > targetTypes ) {
		this.targetTypes = targetTypes;
	}

	public void setParameters( List< Object > parameters ) {
		this.parameters = parameters;
	}

	public void setScript( CharArrayWriter script ) {
		this.script = script;
	}

	public boolean isWriteScript() {
		return writeScript;
	}

	public void setWriteScript( boolean writeScript ) {
		this.writeScript = writeScript;
	}

}
