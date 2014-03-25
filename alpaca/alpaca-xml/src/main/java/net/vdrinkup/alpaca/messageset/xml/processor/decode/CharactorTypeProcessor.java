/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml.processor.decode;

import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import net.vdrinkup.alpaca.commons.typeconverter.Converter;
import net.vdrinkup.alpaca.commons.typeconverter.TypeConverterSimpleFactory;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.xml.definition.XMLElementDefinition;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-25
 */
public class CharactorTypeProcessor implements ElementTypeProcessor {

	@Override
	public void process( XMLElementDefinition definition, DataContext context ) {
		final XMLStreamReader xsr = context.getIn();
		process : {
			if ( xsr.isWhiteSpace() ) {
				break process;
			}
			final String curText = xsr.getText().trim();
			final DataObject sdo = context.getOut();
			final Converter converter = TypeConverterSimpleFactory.getInstance().getConverter( definition.getType() );
			Object curValue = converter.convert( curText );
			if ( definition.isList() ) {
				List< Object > list = sdo.getList( definition.getBinding() );
				if ( list == null ) {
					list = new LinkedList< Object >();
					sdo.setList( definition.getBinding(), list );
				}
				list.add( curValue );
			} else {
				sdo.set( definition.getBinding(), curValue );
			}
		}
	}


}
