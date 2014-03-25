/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.processor.ForeachProcessor;


/**
 * <p></p>
 * @author bing.liu
 * Date 2012-2-1
 */
@XmlRootElement( name = "foreach" )
public class ForeachDefinition extends AbstractElementsDefinition {
	@XmlAttribute
	private String count;
	@XmlAttribute
	private String binding;
	@XmlAttribute( required = true )
	private String toType;
	@XmlTransient
	private volatile ForeachProcessor processor;

	public String getCount() {
		return count;
	}

	public void setCount( String count ) {
		this.count = count;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding( String binding ) {
		this.binding = binding;
	}

	public String getToType() {
		return toType;
	}

	public void setToType( String toType ) {
		this.toType = toType;
	}

	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new ForeachProcessor( this );
				}
			}
		}
		return processor;
	}

}
