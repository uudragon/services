/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.processor.WatershadProcessor;


/**
 * 分水岭配置定义
 * <p>
 * 该类是分水岭标签<watershad/>的映射类。
 * 正如分水岭的自然解释一样，在配置中分水岭就是标识在此标签之后的流程与之前是反向对应的。
 * 例如：
 * 接收数据-->解析请求-->业务处理
 *                          |
 * 发送回应<--封装回应<-- 分水岭
 *                       
 * </p>
 * @author pluto.bing.liu
 * Date 2013-12-5
 */
@XmlRootElement( name = "watershad" )
public class WatershadDefinition extends ProcessorDefinition {
	@XmlTransient
	private volatile WatershadProcessor processor;

	@Override
	public Processor createProcessor() {
		if ( processor == null ) {
			synchronized ( this ) {
				if ( processor == null ) {
					processor = new WatershadProcessor( this );
				}
			}
		}
		return processor;
	}

}
