package net.vdrinkup.alpaca.protocol.definition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import net.vdrinkup.alpaca.configuration.model.AbstractDefinition;


/**
 * 通用配置类
 * @author pluto.bing.liu
 *
 */
@XmlType
public class CommonsConfig extends AbstractDefinition {
	
	@XmlAttribute
	protected String charset;

	public String getCharset() {
		return charset;
	}

	public void setCharset( String charset ) {
		this.charset = charset;
	}
	
}
