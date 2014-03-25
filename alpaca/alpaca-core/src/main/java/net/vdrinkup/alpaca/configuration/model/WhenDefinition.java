/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.language.ExpressionDefinition;
import net.vdrinkup.alpaca.configuration.processor.WhenProcessor;


/**
 * <p></p>
 * @author bing.liu
 * Date 2012-2-10
 */
@XmlRootElement( name = "when" ) 
public class WhenDefinition extends AbstractElementsDefinition {
	@XmlElementRef
	private ExpressionDefinition expression;
	@XmlTransient
	private volatile Processor processor;

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
					processor = new WhenProcessor( this );
				}
			}
		}
		return processor;
	}
	
}
