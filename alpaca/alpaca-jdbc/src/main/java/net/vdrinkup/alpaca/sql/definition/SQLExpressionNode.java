/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.definition;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.configuration.model.language.ExpressionDefinition;
import net.vdrinkup.alpaca.sql.processor.SQLExpressionProcessor;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-1-29
 */
public class SQLExpressionNode extends ProcessorDefinition {
	@XmlElementRef
	protected ExpressionDefinition expression;
	@XmlMixed
	@XmlElementRefs( {
		@XmlElementRef( type = ProcessorDefinition.class )
	} )
	protected List< ProcessorDefinition > elements = new LinkedList< ProcessorDefinition >();
	@XmlTransient
	protected volatile Processor processor; 

	public ExpressionDefinition getExpression() {
		return expression;
	}

	public void setExpression( ExpressionDefinition expression ) {
		this.expression = expression;
	}

	public List< ProcessorDefinition > getElements() {
		return elements;
	}

	public void setElements( List< ProcessorDefinition > elements ) {
		this.elements = elements;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new SQLExpressionProcessor( this );
				}
			}
		}
		return processor;
	}

}
