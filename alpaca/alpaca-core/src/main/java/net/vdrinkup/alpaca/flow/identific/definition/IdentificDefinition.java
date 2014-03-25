/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.identific.definition;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 28, 2013
 */
@XmlRootElement( name = "identific" )
public class IdentificDefinition extends AbstractDefinition {
	@XmlElementRef
	private List< FromDefinition > froms = new LinkedList< FromDefinition >();
	
	public List< FromDefinition > getFroms() {
		return froms;
	}

	public void setFroms( List< FromDefinition > froms ) {
		this.froms = froms;
	}
	
	public Map< String, FromDefinition > toMap() {
		Map< String, FromDefinition > fromMap = new HashMap< String, FromDefinition >();
		if ( getFroms() != null && getFroms().size() != 0 ) {
			for ( FromDefinition from : getFroms() ) {
				fromMap.put( from.getUri(), from );
			}
		}
		return fromMap;
	}
	
}
