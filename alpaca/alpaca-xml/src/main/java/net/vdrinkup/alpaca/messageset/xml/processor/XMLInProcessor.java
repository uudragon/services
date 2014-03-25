/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml.processor;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.messageset.MessageNode;
import net.vdrinkup.alpaca.messageset.MessageSetConstants;
import net.vdrinkup.alpaca.messageset.xml.definition.XMLInDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 26, 2013
 */
public class XMLInProcessor extends AbstractProcessor< XMLInDefinition > {
	
	private static Logger LOG = LoggerFactory.getLogger( XMLInProcessor.class );
	
	public XMLInProcessor( XMLInDefinition t ) {
		super( t );
	}

	@Override
	public void handle( DataContext context ) throws Exception {
		if ( ! ( context.getIn() instanceof byte[] ) ) {
			throw new IllegalArgumentException( "The input must be an instance of byte[]" );
		}
		final byte[] in = context.getIn();
		final ByteArrayInputStream bais = new ByteArrayInputStream( in );
		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader xsr = null;
		try {
			xsr = xif.createXMLStreamReader( bais );
			context.setIn( xsr );
			StringBuilder buffer = new StringBuilder( "" );
			int curNodeType = xsr.next();
			MessageNode definition = null;
			List< MessageNode > stack = new LinkedList< MessageNode >();
			final List< Object > dataStack = new LinkedList< Object >();
			context.setProperty( MessageSetConstants.DATA_STACK, dataStack );
			final List< String > pathStack = new LinkedList< String >();
			context.setProperty( MessageSetConstants.PATH_STACK, pathStack );
			do {
				if ( curNodeType == XMLStreamReader.START_ELEMENT ) {
					if ( definition == null ) {
						definition = getDefinition();
					} else {
						stack.add( 0, definition );
					}
					String name = xsr.getLocalName();
					if ( LOG.isDebugEnabled() ) {
						LOG.debug( "Element [{}] will be disassembled.", name );
					}
					definition = definition.findSub( name );
					( ( ProcessorDefinition ) definition ).createProcessor().process( context );
				} else if ( curNodeType == XMLStreamReader.END_ELEMENT ) {
					String curName = xsr.getLocalName();
					( ( ProcessorDefinition ) definition ).createProcessor().process( context );
					int index = buffer.lastIndexOf( "/" );
					if ( index != -1  ) {
						buffer = buffer.delete( index, buffer.length() );
					}
					if ( LOG.isDebugEnabled() ) {
						LOG.debug( "Element [{}] assembled completed.", curName );
					}
					if ( ! stack.isEmpty() ) {
						definition = stack.remove( 0 );
					}
				} else if ( curNodeType == XMLStreamReader.CHARACTERS || curNodeType == XMLStreamReader.CDATA ) {
					if ( definition == null ) {
						LOG.warn( "Characters or CData will be assembled, but no processor is defined." );
						continue ;
					}
					( ( ProcessorDefinition ) definition ).createProcessor().process( context );
				} else if ( curNodeType == XMLStreamReader.COMMENT ) {
					if ( LOG.isDebugEnabled() ) {
						LOG.debug( "Current position is comment, will be skipped." );
					}
				}
				curNodeType = xsr.next();
			} while ( xsr.hasNext() ) ;
		} finally {
			if ( xsr != null ) {
				xsr.close();
				xsr = null;
			}
		}
	}

}
