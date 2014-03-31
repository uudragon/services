/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.language.ExpressionDefinition;
import net.vdrinkup.alpaca.configuration.processor.CatchProcessor;
import net.vdrinkup.alpaca.flow.identific.definition.ToDefinition;

/**
 * Catch配置定义类
 * <p></p>
 * @author liubing
 * Date Mar 25, 2014
 */
@XmlRootElement( name = "doCatch" )
public class CatchDefinition extends ProcessorDefinition {
	@XmlElement( name = "exception", required = true )
	private List< String > exceptions = new LinkedList< String >();
	@XmlElementRef
	private ExpressionDefinition onWhen;
	@XmlElement( name = "handled" )
	private ToDefinition handled;
	@XmlTransient
	private volatile Processor processor;
	
	public List< String > getExceptions() {
		return exceptions;
	}

	public void setExceptions( List< String > exceptions ) {
		this.exceptions = exceptions;
	}

	public ExpressionDefinition getOnWhen() {
		return onWhen;
	}

	public void setOnWhen( ExpressionDefinition onWhen ) {
		this.onWhen = onWhen;
	}

	public ToDefinition getHandled() {
		return handled;
	}

	public void setHandled( ToDefinition handled ) {
		this.handled = handled;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new CatchProcessor( this );
				}
			}
		}
		return processor;
	}

}
