/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.identific.definition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.flow.FlowDefinition;
import net.vdrinkup.alpaca.flow.identific.processor.FromProcessor;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 28, 2013
 */
@XmlRootElement( name = "from" )
public class FromDefinition extends FlowDefinition {
	@XmlID
	private String id;
	@XmlAttribute
	private String uri;
	@XmlTransient
	private volatile FromProcessor processor;

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

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new FromProcessor( this );
				}
			}
		}
		return processor;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId( String id ) {
		this.id = id;
	}

}
