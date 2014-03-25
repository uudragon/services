package net.vdrinkup.alpaca.http.config;

import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.protocol.definition.CommonsConfig;

/**
 * Http通用配置类
 * @author pluto.bing.liu
 *
 */
@XmlRootElement( name = "commons" )
public class HttpCommonsConfig extends CommonsConfig {
}
