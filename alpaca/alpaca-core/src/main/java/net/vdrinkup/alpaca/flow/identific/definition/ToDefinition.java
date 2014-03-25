/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.identific.definition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.Mode;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.flow.identific.processor.ToProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 28, 2013
 */
@XmlRootElement( name = "to" )
public class ToDefinition extends ProcessorDefinition {
	@XmlAttribute
	private String uri;
	@XmlAttribute
	private Mode mode;
	@XmlTransient
	private volatile ToProcessor processor;

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri( String uri ) {
		this.uri = uri;
	}

	/**
	 * @return the mode
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode( Mode mode ) {
		this.mode = mode;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new ToProcessor( this );
				}
			}
		}
		return processor;
	}

}
