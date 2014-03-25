/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.definition;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.flow.FlowDefinition;
import net.vdrinkup.alpaca.flow.processor.FlowProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 26, 2013
 */
@XmlRootElement( name = "flow" )
public class DefaultFlowDefinition extends FlowDefinition {
	@XmlTransient
	private String id;
	@XmlTransient
	private volatile FlowProcessor processor;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId( String id ) {
		this.id = id;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new FlowProcessor( this );
				}
			}
		}
		return processor;
	}

}
