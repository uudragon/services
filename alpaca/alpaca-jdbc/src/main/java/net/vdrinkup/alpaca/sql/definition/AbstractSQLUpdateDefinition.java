/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.definition;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.sql.processor.SQLUpdateProcessor;



/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-17
 */
public abstract class AbstractSQLUpdateDefinition extends AbstractSQLDefinition {

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new SQLUpdateProcessor( this );
				}
			}
		}
		return processor;
	}

}
