/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import java.net.URI;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.SchemeConstants;
import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.processor.ToProcessor;

/**
 * 流程跳转节点（TO）定义
 * <p>
 * </p>
 * @author liubing Date 2014-4-8
 */
@XmlRootElement( name = "to" )
public class ToDefinition extends ProcessorDefinition {
	@XmlAttribute( required = true, name = "uri" )
	private String uriStr;
	@XmlTransient
	private volatile Processor processor;
	@XmlTransient
	private URI uri;
	@XmlTransient
	private String toName;

	public String getUriStr() {
		return uriStr;
	}

	public void setUriStr( String uriStr ) {
		this.uriStr = uriStr;
	}

	public URI getUri() {
		return uri;
	}

	public String getToName() {
		return toName;
	}

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					uri = URI.create( uriStr );
					final String specificPath = uri.getSchemeSpecificPart();
					final int index = specificPath.indexOf( SchemeConstants.LOCATION_SEPARATOR );
					if ( index == -1 ) {
						toName = specificPath;
					} else {
						toName = specificPath.substring( 0, index );
					}
					processor = new ToProcessor( this );
				}
			}
		}
		return processor;
	}
	
}
