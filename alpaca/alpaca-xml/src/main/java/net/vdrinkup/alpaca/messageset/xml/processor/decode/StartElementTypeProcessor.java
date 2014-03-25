/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml.processor.decode;

import java.util.LinkedList;
import java.util.List;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataFactory;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.MessageSetConstants;
import net.vdrinkup.alpaca.messageset.xml.definition.XMLElementDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * <p>
 * </p>
 * @author pluto.bing.liu Date 2014-2-25
 */
public class StartElementTypeProcessor implements ElementTypeProcessor {

	private static Logger LOG = LoggerFactory
			.getLogger( StartElementTypeProcessor.class );

	@SuppressWarnings( "unchecked" )
	@Override
	public void process( XMLElementDefinition definition, DataContext context ) {
		DataObject sdo = null;
		process: {
			if ( definition.getBinding() == null
					|| "".equals( definition.getBinding() ) ) {
				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "The element [{}] has no configuration binding name.It will be ignore.", definition.getName() );
				}
				break process;
			}
			if ( ! context.hasOut() ) {
				sdo = DataFactory.INSTANCE.create();
				context.setOut( sdo );
				break process;
			}
			if ( definition.getElements() != null
					&& definition.getElements().size() != 0 ) {
				sdo = context.getOut();
				if ( definition.isList() ) {
					sdo.setList( definition.getBinding(), new LinkedList< DataObject >() );
					List< String > pathStack = context.getProperty( MessageSetConstants.PATH_STACK, List.class );
					pathStack.add( definition.getBinding() );
				}
				List< Object > dataStack = context.getProperty(
						MessageSetConstants.DATA_STACK, List.class );
				dataStack.add( 0, ( DataObject ) context.getOut() );
				sdo = DataFactory.INSTANCE.create();
				context.setOut( sdo );
			}
		}
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Parsing the element [{}].", definition.getPath() );
		}
	}

}
