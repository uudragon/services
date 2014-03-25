/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.definition;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.sql.processor.SQLBatchUpdateProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 19, 2014
 */
@XmlRootElement( name = "batchUpdate" )
public class SQLBatchUpdateDefinition extends ProcessorDefinition {
	@XmlElementRef( type = AbstractSQLDefinition.class )
	private List< AbstractSQLUpdateDefinition > elements = new LinkedList< AbstractSQLUpdateDefinition >();
	@XmlTransient
	private volatile Processor processor;
	
	public List< AbstractSQLUpdateDefinition > getElements() {
		return elements;
	}

	public void setElements( List< AbstractSQLUpdateDefinition > elements ) {
		this.elements = elements;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new SQLBatchUpdateProcessor( this );
				}
			}
		}
		return processor;
	}

}
