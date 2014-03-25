package net.vdrinkup.alpaca.protocol.definition;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum( String.class )
public enum IODirection {
	
	@XmlEnumValue( value = "i" ) IN,
	
	@XmlEnumValue( value = "o" ) OUT,
	
	@XmlEnumValue( value = "i/o" ) INOUT

}
