/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model.language;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import net.vdrinkup.alpaca.configuration.Expression;
import net.vdrinkup.alpaca.configuration.Predicate;


/**
 * <p></p>
 * @author bing.liu
 * Date 2012-2-9
 */
@XmlType( name = "expression" )
@XmlAccessorType( XmlAccessType.FIELD )
public abstract class ExpressionDefinition implements Expression, Predicate {
	
	@XmlAttribute
	private String id;
	
	@XmlValue
	private String expression;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId( String id ) {
		this.id = id;
	}

	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public void setExpression( String expression ) {
		this.expression = expression;
	}

}
