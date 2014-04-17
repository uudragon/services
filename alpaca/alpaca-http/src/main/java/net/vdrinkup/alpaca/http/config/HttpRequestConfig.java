package net.vdrinkup.alpaca.http.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.protocol.definition.IContentType;
import net.vdrinkup.alpaca.protocol.definition.RequestConfig;

/**
 * Http请求配置类
 * @author pluto.bing.liu
 *
 */
@XmlRootElement( name = "request" )
public class HttpRequestConfig extends RequestConfig {
	
	@XmlAttribute( name = "contentType" )
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
