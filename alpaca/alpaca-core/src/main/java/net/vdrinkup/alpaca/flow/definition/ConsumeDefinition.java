/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.definition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.flow.processor.ConsumeProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 11, 2014
 */
@XmlRootElement( name = "consume" )
public class ConsumeDefinition extends ProcessorDefinition {
	@XmlAttribute
	private String id;
	@XmlAttribute
	private boolean async;
	@XmlTransient
	private volatile Processor processor;
	
	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync( boolean async ) {
		this.async = async;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new ConsumeProcessor( this );
				}
			}
		}
		return processor;
	}

}
