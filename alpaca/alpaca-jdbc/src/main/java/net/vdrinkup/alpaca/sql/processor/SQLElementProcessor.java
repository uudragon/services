/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import java.sql.ResultSet;

import net.vdrinkup.alpaca.commons.typeconverter.Converter;
import net.vdrinkup.alpaca.commons.typeconverter.TypeConverterSimpleFactory;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.jdbc.session.AbstractSQLSession;
import net.vdrinkup.alpaca.messageset.AbstractCodec;
import net.vdrinkup.alpaca.sql.SQLConstants;
import net.vdrinkup.alpaca.sql.definition.AbstractSQLElementDefinition;


/**
 * 
 * <p>
 * </p>
 * 
 * @author liubing Date Jan 15, 2014
 */
public class SQLElementProcessor extends AbstractCodec< AbstractSQLElementDefinition > {

	public SQLElementProcessor( AbstractSQLElementDefinition t ) {
		super( t );
	}

	@Override
	protected void encode( DataContext context ) throws Exception {
		if ( context.getOut() == null ) {
			return;
		}
		if ( !( context.getOut() instanceof AbstractSQLSession ) ) {
			throw new IllegalArgumentException(
					"The out must be an instance of "
							+ AbstractSQLSession.class.getName() );
		}
		AbstractSQLSession session = context.getOut();
		if ( session.getScript().size() != 0 ) {
			session.getScript().write( SQLConstants.WHITESPACE );
		}
		if ( session.isWriteScript() ) {
			if ( getDefinition().getName() != null && !"".equals( getDefinition().getName() ) ) {
				if ( LOG.isDebugEnabled() ) {
					LOG.debug( "Current parameter' name is [{}].", getDefinition().getName() );
				}
				session.getScript().write( getDefinition().getName() );
				session.getScript().write( getDefinition().getSymbol() );
			}
			session.getScript().write( SQLConstants.PLACEHOLDER );
		}
		final DataObject sdo = context.getIn();
		if ( sdo == null ) {
			return ;
		}
		Object value = sdo.get( getDefinition().getBinding() );
		if ( value == null ) {
			value = getDefinition().getDefaultValue();
		}
		Converter converter = TypeConverterSimpleFactory.getInstance()
				.getConverter( getDefinition().getType() );
		session.getParameters().add( converter.convert( value ) );
	}

	@Override
	protected void decode( DataContext context ) throws Exception {
		if ( context.getIn() == null ) {
			return;
		}
		if ( !( context.getIn() instanceof ResultSet ) ) {
			throw new IllegalArgumentException(
					"The out must be an instance of "
							+ ResultSet.class.getName() );
		}
		ResultSet rs = context.getIn();
		Object columnValue = rs.getObject( getDefinition().getName() );
		DataObject out = context.getOut();
		if ( columnValue == null ) {
			columnValue = getDefinition().getDefaultValue();
			Converter converter = TypeConverterSimpleFactory.getInstance()
					.getConverter( getDefinition().getType() );
			columnValue = converter.convert( columnValue );
		}
		out.set( getDefinition().getBinding(), columnValue );
	}

//	protected void nested( DataContext context, MessageDirection direction ) {
//		if ( ! getDefinition().isLeaf() ) {
//			return ;
//		}
//		final StringBuilder keyBuff = new StringBuilder( context.getProperty( ContextConstants.FROM_NAME, String.class ) );
//		keyBuff.append( GlobalConstants.UNDERLINE );
//		keyBuff.append( context.getProperty( ContextConstants.TO_NAME, String.class ) );
//		keyBuff.append( getDefinition().getNestedType() );
//		keyBuff.append( GlobalConstants.UNDERLINE );
//		keyBuff.append( getDefinition().getBinding() );
//		final MessageDefinition nestedDefinition = MessageSetConfigManager.getInstance().lookup( keyBuff.toString() );
//		Object in = context.getIn();
//	}

}
