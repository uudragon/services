/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.definition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;


/**
 * 消息集配置定义类
 * <p>
 * </p>
 * 
 * @author liubing Date Dec 25, 2013
 */
@XmlRootElement( name = "messageset" )
public class MessageDefinition extends AbstractDefinition {
	@XmlAttribute
	protected String from;
	@XmlAttribute
	protected String to;
	@XmlAttribute
	protected String type;
	@XmlElementRef
	protected MessageInputDefinition in;
	@XmlElementRef
	protected MessageOutputDefinition out;
	@XmlAttribute
	protected Role role;

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom( String from ) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo( String to ) {
		this.to = to;
	}

	/**
	 * @return the type
	 */	@XmlAttribute
		protected String nested;
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType( String type ) {
		this.type = type;
	}

	/**
	 * @return the in
	 */
	public MessageInputDefinition getIn() {
		return in;
	}

	/**
	 * @param in
	 *            the in to set
	 */
	public void setIn( MessageInputDefinition in ) {
		this.in = in;
	}

	/**
	 * @return the out
	 */
	public MessageOutputDefinition getOut() {
		return out;
	}

	/**
	 * @param out
	 *            the out to set
	 */
	public void setOut( MessageOutputDefinition out ) {
		this.out = out;
	}

	public Role getRole() {
		return role;
	}

	public void setRole( Role role ) {
		this.role = role;
	}

}
