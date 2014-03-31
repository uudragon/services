/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import java.util.Map;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.dms.Session;
import net.vdrinkup.alpaca.messageset.MessageEncodingException;
import net.vdrinkup.alpaca.sql.definition.SQLForeachDefinition;

/**
 * 
 * <p>
 * </p>
 * 
 * @author liubing Date Feb 20, 2014
 */
public class SQLForeachProcessor extends
		AbstractProcessor< SQLForeachDefinition > {

	public SQLForeachProcessor( SQLForeachDefinition t ) {
		super( t );
	}

	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		final DataObject sdo = context.getIn();
		final Object value = sdo.get( getDefinition().getBinding() );
		Iterable< ? > iterable;
		try {
			iterable = preparedValue( value );
			final Session session = context.getOut();
			boolean first = true;
			assembleOpen( session );
			final Object bindingValue = sdo.get( getDefinition().getName() );
			for ( Object o : iterable ) {
				if ( first ) {
					session.getScript().append( "" );
				} else {
					if ( getDefinition().getSeparator() != null ) {
						session.getScript().append(
								getDefinition().getSeparator() );
					} else {
						session.getScript().append( "" );
					}
				}
				if ( o instanceof DataObject ) {
					DataObject item = ( DataObject ) o;
					item.set( getDefinition().getName(), bindingValue );
					assembleDataObject( context, item );
				} else {
					assembleObject( session, o );
				}
				if ( first ) {
					first = false;
				}
			}
			assembleClose( session );
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
		}
		return true;
	}

	/**
	 * @param session
	 * @param o
	 */
	private void assembleObject( Session session, Object o ) {

	}

	/**
	 * @param context
	 */
	private void assembleDataObject( DataContext context, DataObject item )
			throws Exception {
		DataObject in = context.getIn();
		context.setIn( ( DataObject ) item );
		for ( ProcessorDefinition processor : getDefinition().getElements() ) {
			processor.createProcessor().process( context );
		}
		context.setIn( in );
	}

	/**
	 * @param session
	 */
	private void assembleClose( Session session ) {
		final String close = getDefinition().getClose();
		if ( close != null ) {
			session.getScript().append( close );
		}
	}

	/**
	 * @param session
	 */
	private void assembleOpen( Session session ) {
		final String open = getDefinition().getOpen();
		if ( open != null ) {
			session.getScript().append( open );
		}
	}

	/**
	 * @param value
	 * @return
	 */
	private Iterable< ? > preparedValue( Object value )
			throws MessageEncodingException {
		if ( value == null ) {
			throw new MessageEncodingException( "Con not encode a null value." );
		}
		if ( value instanceof Iterable< ? > ) {
			return ( Iterable< ? > ) value;
		}
		if ( value instanceof Map ) {
			return ( ( Map< ?, ? > ) value ).entrySet();
		}
		throw new MessageEncodingException(
				"Can not encode a value is not an instance of Iterable." );
	}

}
