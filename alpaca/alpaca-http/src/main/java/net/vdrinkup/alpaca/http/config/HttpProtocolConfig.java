package net.vdrinkup.alpaca.http.config;

import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.protocol.definition.ProtocolConfig;

/**
 * Http协议配置类
 * @author pluto.bing.liu
 */
@XmlRootElement( name = "protocol.http" ) 
public class HttpProtocolConfig extends ProtocolConfig {
}
