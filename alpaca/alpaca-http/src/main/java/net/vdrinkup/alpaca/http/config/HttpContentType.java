package net.vdrinkup.alpaca.http.config;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

import net.vdrinkup.alpaca.protocol.definition.IContentType;

/**
 * HttpContentType
 * 
 * @author pluto.bing.liu
 * 
 */
@XmlEnum(String.class)
public enum HttpContentType implements IContentType {
	@XmlEnumValue( value = "*" ) 
	MEDIA_TYPE_WILDCARD("*"), 
	@XmlEnumValue( value = "*/*" )
	WILDCARD("*/*"), 
	@XmlEnumValue( value = "application/xml" )
	APPLICATION_XML( "application/xml" ), 
	@XmlEnumValue( value = "application/atom+xml" )
	APPLICATION_ATOM_XML("application/atom+xml"), 
	@XmlEnumValue( value = "application/xhtml+xml" )
	APPLICATION_XHTML_XML( "application/xhtml+xml" ), 
	@XmlEnumValue( value = "application/svg+xml" )
	APPLICATION_SVG_XML("application/svg+xml"),
	@XmlEnumValue( value = "application/json" )
	APPLICATION_JSON( "application/json" ), 
	@XmlEnumValue( "application/x-www-form-urlencoded" )
	APPLICATION_FORM_URLENCODED( "application/x-www-form-urlencoded" ), 
	@XmlEnumValue( "multipart/form-data" )
	MULTIPART_FORM_DATA( "multipart/form-data" ), 
	@XmlEnumValue( "application/octet-stream" )
	APPLICATION_OCTET_STREAM( "application/octet-stream" ), 
	@XmlEnumValue( "text/plain" )
	TEXT_PLAIN("text/plain"), 
	@XmlEnumValue( "text/xml" )
	TEXT_XML( "text/xml" ), 
	@XmlEnumValue( "text/html" )
	TEXT_HTML("text/html");

	HttpContentType(String contentType) {
		this.contentType = contentType;
	}

	private String contentType;

	@Override
	public String getContentType() {
		return contentType;
	}

}
