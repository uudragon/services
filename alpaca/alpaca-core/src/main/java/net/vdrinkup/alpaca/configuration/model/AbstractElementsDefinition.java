/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;


/**
 * 抽象元素集定义类
 * <p>
 * </p>
 * @author liubing Date 2013-11-13
 */
public abstract class AbstractElementsDefinition extends ProcessorDefinition {
	@XmlAnyElement( lax = true )
	protected List< ProcessorDefinition > elements = new LinkedList< ProcessorDefinition >();

	public AbstractElementsDefinition() {
	}

	public List< ProcessorDefinition > getElements() {
		return this.elements;
	}

	public void setElements( List< ProcessorDefinition > elements ) {
		this.elements = elements;
	}

}
