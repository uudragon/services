/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.processor;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.commons.token.TokenParser;
import net.vdrinkup.alpaca.commons.token.impl.DataObjectTokenHandler;
import net.vdrinkup.alpaca.commons.token.impl.DefaultTokenParser;
import net.vdrinkup.alpaca.commons.typeconverter.Converter;
import net.vdrinkup.alpaca.commons.typeconverter.TypeConverterSimpleFactory;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.SetInBodyDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;

/**
 *
 * <p></p>
 * @author liubing
 * Date Jan 15, 2014
 */
public class SetInBodyProcessor extends AbstractProcessor< SetInBodyDefinition > {

	private TokenParser parser = new DefaultTokenParser( "${", "}" );
	
	/**
	 * @param t
	 */
	public SetInBodyProcessor( SetInBodyDefinition t ) {
		super( t );
	}

	@Override
	public boolean process( DataContext context, DoneCallback callback ) {
		DataObject sdo = context.getIn();
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Set key->value [{}->{}] into the context", getDefinition().getKey(), 
					getDefinition().getValue() );
		}
		String value;
		try {
			value = parser.parse( getDefinition().getValue(), new DataObjectTokenHandler( sdo ) );
			final Converter converter = TypeConverterSimpleFactory.getInstance().getConverter( getDefinition().getType() );
			final Object realValue = converter.convert( value );
			sdo.set( getDefinition().getKey(), realValue );
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
			return true;
		}
		return true;
	}

}
