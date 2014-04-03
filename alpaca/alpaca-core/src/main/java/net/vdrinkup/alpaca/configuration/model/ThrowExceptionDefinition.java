/**
 * 
 */
package net.vdrinkup.alpaca.configuration.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.processor.ThrowExceptionProcessor;

/**
 * 抛出配置定义类
 * @author pluto.bing.liu
 */
@XmlRootElement( name = "throw" )
public class ThrowExceptionDefinition extends ProcessorDefinition {
	@XmlAttribute( name = "class" )
	private String clazz;
	@XmlTransient
	private Processor processor = new ThrowExceptionProcessor( this );
	
	public String getClazz() {
		return clazz;
	}

	public void setClazz( String clazz ) {
		this.clazz = clazz;
	}

	@Override
	public Processor createProcessor() {
		return processor;
	}

}
