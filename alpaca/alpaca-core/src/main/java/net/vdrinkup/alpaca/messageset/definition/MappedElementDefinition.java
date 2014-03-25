/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.definition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlTransient;

import net.vdrinkup.alpaca.messageset.MessageNode;


/**
 * 消息映射抽象类
 * <p></p>
 * @author liubing
 * Date Jan 16, 2014
 */
public abstract class MappedElementDefinition extends AbstractBaseAttrDefinition implements MessageNode {
	@XmlElementRef( name = "element", type = MappedElementDefinition.class )
	protected List< MessageNode > elements;
	@XmlTransient
	protected Map< String, MessageNode > mappers;

	public void setElements( List< MessageNode > elements ) {
		this.elements = elements;
	}

	protected void afterUnmarshal( Unmarshaller unmarshaller, Object parent ) {
		this.mappers = new HashMap< String, MessageNode >( 16 );
		if ( getElements() == null || getElements().size() == 0 ) {
			return ;
		}
		for ( MessageNode element : getElements() ) {
			mappers.put( element.getName(), element );
		}
	}

	/**
	 * 是否叶子节点
	 * @return
	 */
	public boolean isLeaf() {
		boolean isLeaf = true;
		if ( elements != null && elements.size() != 0 ) {
			isLeaf = false;
		}
		return isLeaf;
	}

	@Override
	public List< MessageNode > getElements() {
		return elements;
	}

	@Override
	public MessageNode findSub( String key ) {
		MessageNode definition = null;
		if ( mappers != null ) {
			definition = mappers.get( key );
		}
		return definition;
	}
	
}
