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
 * 抽象任意输出元素定义类
 * <p>
 * </p>
 * @author liubing Date 2013-11-13
 */
public abstract class AbstractAnyOutputsDefinition extends ProcessorDefinition {
	@XmlAnyElement( lax = true )
	protected List< ProcessorDefinition > outputs = new LinkedList< ProcessorDefinition >();

	public AbstractAnyOutputsDefinition() {
	}

	public List< ProcessorDefinition > getOutputs() {
		return outputs;
	}

	public void setOutputs( List< ProcessorDefinition > outputs ) {
		this.outputs = outputs;
	}

}
