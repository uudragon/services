/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json.processor;

import java.io.ByteArrayOutputStream;

import net.vdrinkup.alpaca.commons.typeconverter.Converter;
import net.vdrinkup.alpaca.commons.typeconverter.TypeConverterSimpleFactory;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.AbstractCodec;
import net.vdrinkup.alpaca.messageset.json.JsonConstants;
import net.vdrinkup.alpaca.messageset.json.definition.JsonElementDefinition;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-27
 */
public class JsonElementProcessor extends AbstractCodec< JsonElementDefinition > {

	public JsonElementProcessor( JsonElementDefinition t ) {
		super( t );
	}

	@Override
	protected void encode( DataContext context ) throws Exception {
		ByteArrayOutputStream baos = context.getOut();
		baos.write( JsonConstants.D_QUOTATION_MARK );
		baos.write( getDefinition().getName().getBytes() );
		baos.write( JsonConstants.D_QUOTATION_MARK );
		baos.write( JsonConstants.COLON );
		String type = definition.getType();
		if ( type == null ) {
			DataObject sdo = context.getIn();
			Object value = sdo.get( getDefinition().getBinding() );
			if ( value != null ) {
				type = value.getClass().getSimpleName();
			}
		}
		final JsonEncoder encoder = JsonEncoderSimpleFactory.getInstance().getEncoder( type );
		encoder.encode( context, definition );
	}

	@Override
	protected void decode( DataContext context ) throws Exception {
		DataObject sdo = context.getOut();
		Object value = null;
		if ( context.getIn() instanceof byte[] ) {
			byte[] bytes = context.getIn();
			value = new String( bytes );
			final Converter converter = TypeConverterSimpleFactory.getInstance().getConverter( getDefinition().getType() );
			value = converter.convert( value );
		} else {
			value = context.getIn();
		}
		sdo.set( getDefinition().getBinding(), value );
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "The element named [{}] has been decode, value is [{}]", getDefinition().getBinding(), value );
		}
		
	}

}
