/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.definition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.sql.processor.SQLForeachProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 20, 2014
 */
@XmlRootElement( name = "foreach" )
public class SQLForeachDefinition extends AbstractSQLDefinition {
	@XmlAttribute
	private String separator;
	@XmlTransient
	private volatile Processor processor;

	public String getSeparator() {
		return separator;
	}

	public void setSeparator( String separator ) {
		this.separator = separator;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new SQLForeachProcessor( this );
				}
			}
		}
		return processor;
	}

}
