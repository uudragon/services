/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.vdrinkup.alpaca.configuration.Processor;
import net.vdrinkup.alpaca.configuration.model.adapter.NodeConvertAdapter;


/**
 *
 * <p></p>
 * @author liubing
 * Date Nov 10, 2013
 */
@XmlJavaTypeAdapter( NodeConvertAdapter.class )
public abstract class ProcessorDefinition extends AbstractDefinition {
	
	public abstract Processor createProcessor();

}
