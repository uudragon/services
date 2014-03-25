/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml.processor;

import java.util.List;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.AbstractCodec;
import net.vdrinkup.alpaca.messageset.MessageNode;
import net.vdrinkup.alpaca.messageset.xml.definition.XMLElementDefinition;
import net.vdrinkup.alpaca.messageset.xml.processor.decode.ElementTypeProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * <p></p>
 * @author liubing
 * Date Jan 16, 2014
 */
public class XMLElementProcessor extends AbstractCodec< XMLElementDefinition > {
	
	private static Logger LOG = LoggerFactory.getLogger( XMLElementProcessor.class );
	
	public XMLElementProcessor( XMLElementDefinition t ) {
		super( t );
	}

	@Override
	protected void encode( DataContext context ) throws Exception {
		final XMLStreamWriter xsw = context.getOut();
		if ( getDefinition().isLeaf() ) {
			final DataObject sdo = context.getIn();
			if ( sdo == null ) {
				return ;
			}																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		
			leaf : if ( getDefinition().isList() ) {
				List< Object > list = sdo.getList( getDefinition().getBinding() );
				if ( list == null ) {
					break leaf;
				}
				for ( Object obj : list ) {
					String prefix = getDefinition().getPrefix() == null ? "" : getDefinition().getPrefix();
					String namespace = getDefinition().getNamespace() == null ? "" : getDefinition().getNamespace();
					xsw.writeStartElement( prefix, getDefinition().getName(), namespace );
					if ( ! "".equals( namespace ) ) {
						xsw.writeNamespace( prefix, namespace );
					}
					xsw.writeCharacters( obj == null ? getDefinition().getDefaultValue() : obj.toString() );
					xsw.writeEndElement();
				}
			} else {
				Object obj = sdo.get( getDefinition().getBinding() );
				String prefix = getDefinition().getPrefix() == null ? "" : getDefinition().getPrefix();
				String namespace = getDefinition().getNamespace() == null ? "" : getDefinition().getNamespace();
				xsw.writeStartElement( prefix, getDefinition().getName(), namespace );
				if ( ! "".equals( namespace ) ) {
					xsw.writeNamespace( prefix, namespace );
				}
				xsw.writeCharacters( obj == null ? getDefinition().getDefaultValue() : obj.toString() );
				xsw.writeEndElement();
			}
		} else {
			final DataObject sdo = context.getIn();
			if ( sdo == null ) {
				return ;
			}
			if ( getDefinition().getBinding() == null || "".equals( getDefinition().getBinding() ) ) {
				String prefix = getDefinition().getPrefix() == null ? "" : getDefinition().getPrefix();
				String namespace = getDefinition().getNamespace() == null ? "" : getDefinition().getNamespace();
				xsw.writeStartElement( prefix, getDefinition().getName(), namespace );
				if ( ! "".equals( namespace ) ) {
					xsw.writeNamespace( prefix, namespace );
				}
				for ( MessageNode element : getDefinition().getElements() ) {
					( ( ProcessorDefinition ) element ).createProcessor().process( context );
				}
				xsw.writeEndElement();
				return ;
			}
			middle : if ( getDefinition().isList() ) {
				List< DataObject > list = sdo.getList( getDefinition().getBinding() );
				if ( list == null ) {
					break middle;
				}
				String prefix = getDefinition().getPrefix() == null ? "" : getDefinition().getPrefix();
				String namespace = getDefinition().getNamespace() == null ? "" : getDefinition().getNamespace();
				xsw.writeStartElement( prefix, getDefinition().getName(), namespace );
				if ( ! "".equals( namespace ) ) {
					xsw.writeNamespace( prefix, namespace );
				}
				for ( DataObject newSdo : list ) {
					context.setIn( newSdo );
					for ( MessageNode element : getDefinition().getElements() ) {
						( ( ProcessorDefinition ) element ).createProcessor().process( context );
					}
				}
				xsw.writeEndElement();
				context.setIn( sdo );
			} else {
				DataObject newSdo = sdo.getDataObject( getDefinition().getBinding() );
				context.setIn( newSdo );
				String prefix = getDefinition().getPrefix() == null ? "" : getDefinition().getPrefix();
				String namespace = getDefinition().getNamespace() == null ? "" : getDefinition().getNamespace();
				xsw.writeStartElement( prefix, getDefinition().getName(), namespace );
				if ( ! "".equals( namespace ) ) {
					xsw.writeNamespace( prefix, namespace );
				}
				xsw.writeEndElement();
				for ( MessageNode element : getDefinition().getElements() ) {
					( ( ProcessorDefinition ) element ).createProcessor().process( context );
				}
			}
			context.setIn( sdo );
		}
	}
	
	@Override
	protected void decode( DataContext context ) throws Exception {
		XMLStreamReader xsr = context.getIn();
		int type = xsr.getEventType();
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Thetype of current node is [{}]", type );
		}
		ElementTypeProcessor processor = ElementTypeProcessorFactory.getInstance().lookup( type );
		processor.process( getDefinition(), context );
	}

}
