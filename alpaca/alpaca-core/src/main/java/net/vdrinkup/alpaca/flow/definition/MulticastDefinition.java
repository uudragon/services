/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.definition;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.flow.processor.MulticastProcessor;


/**
 * 组播流程配置定义类
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-3-11
 */
@XmlRootElement( name = "multicast" )
public class MulticastDefinition extends ProcessorDefinition {
	@XmlAttribute
	private boolean parallel;
	@XmlElement( name = "pipeline" )
	private List< PipelineDefinition > pipelines = new LinkedList< PipelineDefinition >();
	@XmlTransient
	private Processor processor;

	public boolean isParallel() {
		return parallel;
	}

	public void setParallel( boolean parallel ) {
		this.parallel = parallel;
	}

	public List< PipelineDefinition > getPipelines() {
		return pipelines;
	}

	public void setPipelines( List< PipelineDefinition > pipelines ) {
		this.pipelines = pipelines;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new MulticastProcessor( this );
				}
			}
		}
		return processor;
	}

}
