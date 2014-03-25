package net.vdrinkup.alpaca.protocol.definition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;


@XmlRootElement( name = "property" )
public class PropertyConfig extends AbstractDefinition {
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String value;
	@XmlAttribute
	private String type;

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue( String value ) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType( String type ) {
		this.type = type;
	}

}
