package net.vdrinkup.alpaca.protocol.definition;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;


public abstract class AbstractAttributeDefinition extends AbstractDefinition {
	@XmlAttribute
	protected String uri;
	
	@XmlElement( name = "property" )
	protected List< PropertyConfig > properties = new LinkedList< PropertyConfig >();

	public List< PropertyConfig > getProperties() {
		return properties;
	}

	public void setProperties( List< PropertyConfig > properties ) {
		this.properties = properties;
	}

	public String getUri() {
		return uri;
	}

	public void setUri( String uri ) {
		this.uri = uri;
	}

	public abstract IContentType getContentType();

	public abstract void setContentType(IContentType contentType);

}
