/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml.processor;

import java.io.ByteArrayOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.xml.definition.XMLOutDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Dec 26, 2013
 */
public class XMLOutProcessor extends AbstractProcessor< XMLOutDefinition > {

	/**
	 * @param t
	 */
	public XMLOutProcessor( XMLOutDefinition t ) {
		super( t );
	}

	@Override
	public boolean process( DataContext context, DoneCallback callback ) {
		if ( ! ( context.getIn() instanceof DataObject ) ) {
			throw new IllegalArgumentException( "The input must be an instance of DataObject." );
		}
		XMLOutputFactory xof = XMLOutputFactory.newFactory();
		xof.setProperty( XMLOutputFactory.IS_REPAIRING_NAMESPACES, false );
		XMLStreamWriter xsw = null;
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			xsw = xof.createXMLStreamWriter( baos );
			xsw.writeStartDocument();
			context.setOut( xsw );
			for ( ProcessorDefinition definition : getDefinition().getElements() ) {
				definition.createProcessor().process( context );
			}
			xsw.writeEndDocument();
		} catch ( Exception e ) {
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
		} finally {
			if ( xsw != null ) {
				try {
					xsw.close();
				} catch ( XMLStreamException e ) {
					LOG.error( e.getMessage(), e );
				}
				xsw = null;
			}
		};
		context.setOut( baos.toByteArray() );
		callback.done( true );
		return true;
	}

}
