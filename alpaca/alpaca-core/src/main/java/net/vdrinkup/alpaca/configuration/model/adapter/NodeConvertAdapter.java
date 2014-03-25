/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 节点转换适配器
 * <p>
 * 该适配器用与对节点进行转换，在不需要转换特殊节点时，使用该类自身，不对解组或者编组进行干涉。
 * 在需要进行特殊处理的地方，通过继承该类，实现自己的转换功能。基于{@XmlAdapter}在JAXB中的处理方式，
 * 1.解组时，{@Unmarshaller}的setAdapter(NodeConvertAdapter.class, 子类的实例)。
 * 2.编组时，{@Marshaller}的setAdapter(NodeConvertAdapter.class, 子类的实现)。
 * </p>
 * @author pluto.bing.liu Date 2013-12-13
 */
public class NodeConvertAdapter extends XmlAdapter< Object, Object > {

	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public Object unmarshal( Object v ) throws Exception {
		return v;
	}

	/* (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public Object marshal( Object v ) throws Exception {
		return v;
	}

}
