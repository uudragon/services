/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml.processor;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamReader;

import net.vdrinkup.alpaca.messageset.xml.processor.decode.CharactorTypeProcessor;
import net.vdrinkup.alpaca.messageset.xml.processor.decode.ElementTypeProcessor;
import net.vdrinkup.alpaca.messageset.xml.processor.decode.EndElementTypeProcessor;
import net.vdrinkup.alpaca.messageset.xml.processor.decode.StartElementTypeProcessor;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-25
 */
public class ElementTypeProcessorFactory {
	
	private static final Map< Integer, ElementTypeProcessor > PROCESSORS;
	
	private static final ElementTypeProcessorFactory INSTANCE = new ElementTypeProcessorFactory();
	
	private ElementTypeProcessorFactory() {
	}

	static {
		PROCESSORS = new HashMap< Integer, ElementTypeProcessor >( 16 );
		PROCESSORS.put( XMLStreamReader.START_ELEMENT, new StartElementTypeProcessor() );
		PROCESSORS.put( XMLStreamReader.END_ELEMENT, new EndElementTypeProcessor() );
		PROCESSORS.put( XMLStreamReader.CHARACTERS, new CharactorTypeProcessor() );
	}
	
	public static ElementTypeProcessorFactory getInstance() {
		return INSTANCE;
	}
	
	public ElementTypeProcessor lookup( int type ) {
		return PROCESSORS.get( type );
	}
	
}
