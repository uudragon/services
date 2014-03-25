/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.definition;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.flow.processor.PipelineProcessor;


/**
 * 顺序流程配置定义
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-11
 */
@XmlRootElement( name = "pipeline" )
public class PipelineDefinition extends ProcessorDefinition {
	@XmlElementRef( type = ProcessorDefinition.class )
	private List< ProcessorDefinition > steps = new LinkedList< ProcessorDefinition >();
	@XmlTransient
	private Processor processor;

	public List< ProcessorDefinition > getSteps() {
		return steps;
	}

	public void setSteps( List< ProcessorDefinition > steps ) {
		this.steps = steps;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new PipelineProcessor( this );
				}
			}
		}
		return processor;
	}

}
