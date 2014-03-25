/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.definition;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;

import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.messageset.definition.AbstractBaseAttrDefinition;


/**
 * SQL配置定义抽象类
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-1-27
 */
public abstract class AbstractSQLDefinition extends AbstractBaseAttrDefinition {
	/**
	 * 子元素定义
	 */
	@XmlMixed	
	@XmlElementRefs( {
		@XmlElementRef( type = ProcessorDefinition.class )
	} )
	protected List< ProcessorDefinition > elements = new LinkedList< ProcessorDefinition >();

	public List< ProcessorDefinition > getElements() {
		return elements;
	}

	public void setElements( List< ProcessorDefinition > elements ) {
		this.elements = elements;
	}
	
	public boolean isLeaf() {
		boolean isLeaf = false;
		if ( elements == null || elements.size() == 0 ) {
			isLeaf = true;
		}
		return isLeaf;
	}

}
