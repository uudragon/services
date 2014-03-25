/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.language.ExpressionDefinition;
import net.vdrinkup.alpaca.configuration.processor.ExpressionNodeProcessor;


/**
 * <p>
 * </p>
 * 
 * @author bing.liu Date 2012-2-10
 */
public class ExpressionNode extends AbstractElementsDefinition {
	@XmlElementRef
	private ExpressionDefinition expression;
	@XmlTransient
	private volatile ExpressionNodeProcessor processor;

	public ExpressionDefinition getExpression() {
		return expression;
	}

	public void setExpression( ExpressionDefinition expression ) {
		this.expression = expression;
	}
	
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new ExpressionNodeProcessor( this );
				}
			}
		}
		return processor;
	}

}
