/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.definition;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.sql.processor.SQLStringProcessor;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-1-27
 */
public class SQLStringDefinition extends ProcessorDefinition {
	
	private String sql;
	
	private volatile Processor processor;
	
	public SQLStringDefinition( String sql ) {
		this.sql = sql;
	}
	
	public String getSql() {
		return sql;
	}

	public void setSql( String sql ) {
		this.sql = sql;
	}

	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new SQLStringProcessor( this );
				}
			}
		}
		return processor;
	}

}
