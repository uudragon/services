/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.xml.processor;

import java.io.ByteArrayOutputStream;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.vdrinkup.alpaca.GlobalConstants;
import net.vdrinkup.alpaca.commons.token.TokenParser;
import net.vdrinkup.alpaca.commons.token.impl.DataObjectTokenHandler;
import net.vdrinkup.alpaca.commons.token.impl.DefaultTokenParser;
import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.AbstractCodec;
import net.vdrinkup.alpaca.messageset.MessageSetConfigManager;
import net.vdrinkup.alpaca.messageset.definition.MessageDefinition;
import net.vdrinkup.alpaca.messageset.xml.definition.XMLNestedDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Mar 14, 2014
 */
public class XMLNestedProcessor extends AbstractCodec< XMLNestedDefinition > {
	
	private TokenParser parser = new DefaultTokenParser( "${", "}" );
	
	public XMLNestedProcessor( XMLNestedDefinition t ) {
		super( t );
	}

	@Override
	protected void encode( DataContext context ) throws Exception {
		final StringBuilder keyBuff = new StringBuilder( context.getProperty( ContextConstants.FROM_NAME, String.class ) );
		keyBuff.append( GlobalConstants.UNDERLINE );
		final DataObject sdo = ( DataObject ) context.getIn();
		String nestedKey = parser.parse( getDefinition().getTo(), new DataObjectTokenHandler( sdo ) );
		keyBuff.append( nestedKey );
		final MessageDefinition nestedDefinition = MessageSetConfigManager.getInstance().lookup( keyBuff.toString() );
		final XMLStreamWriter writer = context.getOut();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			context.setOut( baos );
			nestedDefinition.getOut().createProcessor().process( context );
			writer.writeStartElement( getDefinition().getName() );
			writer.writeCharacters( new String( baos.toByteArray() ) );
			writer.writeEndElement();
		} finally {
			baos.reset();
			baos.close();
			context.setOut( writer );
		}
	}

	@Override
	protected void decode( DataContext context ) throws Exception {
		final XMLStreamReader xsr = context.getIn();
		if ( ( ! xsr.isCharacters() ) || xsr.isWhiteSpace() ) {
			return ;
		}
		final String nestedMessage = xsr.getText().trim();
		final Object curIn = context.getIn();
		final String charset = context.getProperty( ContextConstants.CHARSET, String.class, "UTF-8");
		final byte[] nestedBytes = nestedMessage.getBytes( charset );
		context.setIn( nestedBytes );
		final StringBuilder keyBuff = new StringBuilder( context.getProperty( ContextConstants.FROM_NAME, String.class ) );
		keyBuff.append( GlobalConstants.UNDERLINE );
		final DataObject sdo = ( DataObject ) context.getOut();
		String nestedKey = parser.parse( getDefinition().getTo(), new DataObjectTokenHandler( sdo ) );
		keyBuff.append( nestedKey );
		final MessageDefinition nestedDefinition = MessageSetConfigManager.getInstance().lookup( keyBuff.toString() );
		if ( nestedDefinition != null ) {
			nestedDefinition.getIn().createProcessor().process( context );
		}
		context.setOut( context.getIn() );
		context.setIn( curIn );
	} 

}
