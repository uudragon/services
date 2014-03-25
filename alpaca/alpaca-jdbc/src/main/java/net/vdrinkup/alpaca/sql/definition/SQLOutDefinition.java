/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.definition;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.messageset.definition.MessageOutputDefinition;
import net.vdrinkup.alpaca.sql.processor.SQLOutProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Jan 15, 2014
 */
@XmlRootElement( name = "out" )
public class SQLOutDefinition extends MessageOutputDefinition {
	@XmlElementRef
	private AbstractSQLDefinition sqlDef;
	@XmlTransient
	private volatile Processor processor;

	public AbstractSQLDefinition getSqlDef() {
		return sqlDef;
	}

	public void setSqlDef( AbstractSQLDefinition sqlDef ) {
		this.sqlDef = sqlDef;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new SQLOutProcessor( this );
				}
			}
		}
		return processor;
	}

}
