/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml.processor.decode;

import java.util.List;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.MessageSetConstants;
import net.vdrinkup.alpaca.messageset.xml.definition.XMLElementDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * <p>
 * </p>
 * 
 * @author pluto.bing.liu Date 2014-2-25
 */
public class EndElementTypeProcessor implements ElementTypeProcessor {
	
	private static Logger LOG = LoggerFactory.getLogger( EndElementTypeProcessor.class );

	@SuppressWarnings( "unchecked" )
	@Override
	public void process( XMLElementDefinition definition, DataContext context ) {
		process: {
			if ( definition.getBinding() == null
					|| "".equals( definition.getBinding() ) ) {
				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "The element [{}] has no configuration binding name.It will be ignore.", definition.getName() );
				}
				break process;
			}
			DataObject sdo = context.getOut();
			if ( definition.getElements() != null
					&& definition.getElements().size() != 0 ) {
				final List< DataObject > dataStack = context.getProperty(
						MessageSetConstants.DATA_STACK, List.class );
				if ( dataStack.size() == 0 ) {
					break process;
				}
				final DataObject p = dataStack.remove( 0 );
				if ( definition.isList() ) {
					List< String > pathStack = context.getProperty(
							MessageSetConstants.PATH_STACK, List.class );
					String dataPath = pathStack.remove( 0 );
					List< DataObject > dataObjects = p.getList( dataPath );
					dataObjects.add( sdo );
				} else {
					p.setDataObject( definition.getBinding(), sdo );
				}
				sdo = p;
				context.setOut( sdo );
			}
		}
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "The element [{}] parsed successfully.",
					definition.getPath() );
		}
	}

}
