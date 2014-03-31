/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;

/**
 * 抽象输出项定义类
 * <p></p>
 * @author liubing
 * Date Mar 25, 2014
 */
public abstract class AbstractOutputsDefinition extends ProcessorDefinition {
	@XmlElementRef( type = ProcessorDefinition.class )
	protected List< ProcessorDefinition > outputs = new LinkedList< ProcessorDefinition >();

	public List< ProcessorDefinition > getOutputs() {
		return outputs;
	}

	public void setOutputs( List< ProcessorDefinition > outputs ) {
		this.outputs = outputs;
	}
	
}
