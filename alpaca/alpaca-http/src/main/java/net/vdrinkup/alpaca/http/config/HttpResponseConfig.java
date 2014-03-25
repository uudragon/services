package net.vdrinkup.alpaca.http.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.protocol.definition.IContentType;
import net.vdrinkup.alpaca.protocol.definition.ResponseConfig;

/**
 * Http响应配置类
 * @author pluto.bing.liu
 *
 */
@XmlRootElement( name = "response" )
public class HttpResponseConfig extends ResponseConfig {
	@XmlAttribute
	private HttpContentType contentType;

	@Override
	public IContentType getContentType() {
		return contentType;
	}

	@Override
	public void setContentType(IContentType contentType) {
		this.contentType = (HttpContentType) contentType;
	}
}
