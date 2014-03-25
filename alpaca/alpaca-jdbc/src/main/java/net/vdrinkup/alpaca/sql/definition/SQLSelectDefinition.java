/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.definition;

import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.sql.processor.SQLSelectProcessor;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-1-27
 */
@XmlRootElement( name = "select" )
public class SQLSelectDefinition extends AbstractSQLDefinition {
	
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new SQLSelectProcessor( this );
				}
			}
		}
		return processor;
	}
	
	@Override
	public boolean isLeaf() {
		boolean isLeaf = false;
		if ( elements == null || elements.size() == 0 ) {
			isLeaf = true;
		}
		return isLeaf;
	}
	
}
