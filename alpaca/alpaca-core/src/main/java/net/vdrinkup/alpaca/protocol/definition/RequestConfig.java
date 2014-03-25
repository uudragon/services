package net.vdrinkup.alpaca.protocol.definition;

import javax.xml.bind.annotation.XmlType;

/**
 * 请求配置抽象基类
 * <p>
 * 所有不同协议的请求配置都继承该类
 * </p>
 * @author pluto.bing.liu
 *
 */
@XmlType
public abstract class RequestConfig extends AbstractAttributeDefinition {
}
