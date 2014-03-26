/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import java.util.Map;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.dms.Session;
import net.vdrinkup.alpaca.messageset.MessageEncodingException;
import net.vdrinkup.alpaca.sql.definition.SQLForeachDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 20, 2014
 */
public class SQLForeachProcessor extends AbstractProcessor< SQLForeachDefinition > {

	public SQLForeachProcessor( SQLForeachDefinition t ) {
		super( t );
	}

	@Override
	protected void handle( DataContext context ) throws Exception {
		final DataObject sdo = context.getIn();
		final Object value = sdo.get( getDefinition().getBinding() );
		Iterable< ? > iterable = preparedValue( value );
		final Session session = context.getOut();
		boolean first = true;
		assembleOpen( session );
		final Object bindingValue = sdo.get( getDefinition().getName() );
		for ( Object o : iterable ) {
			if ( first ) {
				session.getScript().append( "" );
			} else {
				if ( getDefinition().getSeparator() != null ) {
					session.getScript().append( getDefinition().getSeparator() );
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
		// List< Object > list = session.getParameters();
		// DataObject sdo = context.getIn();
		// Object bindingValue = sdo.get( getDefinition().getBinding() );
		// if ( bindingValue == null
		// || ! ( bindingValue instanceof Collection< ? > ) ) {
		// throw new IllegalArgumentException(
		// "The value of binding element can not be null or must be an instanceof Collection."
		// );
		// }
		// Collection< Object > collection = ( Collection< Object > )
		// bindingValue;
		// Iterator< Object > iter = collection.iterator();
		// session.setParameters( new LinkedList< Object >() );
		// Object item = null;
		// if ( ! iter.hasNext() ) {
		// LOG.warn(
		// "Collections has no more elements, <foreach> can not be process." );
		// context.setStatus( ContextStatus.TERMINATED );
		// return ;
		// }
		// item = iter.next();
		// if ( ! ( item instanceof DataObject ) ) {
		// LOG.warn( "The item of collections is not an instance of DataObject."
		// );
		// context.setStatus( ContextStatus.TERMINATED );
		// return ;
		// }
		// if ( getDefinition().getName() != null && !"".equals(
		// getDefinition().getName() ) ) {
		// ( ( DataObject ) item ).set( getDefinition().getName(), sdo.get(
		// getDefinition().getName() ) );
		// }
		// context.setIn( item );
		// for ( ProcessorDefinition processor : getDefinition().getElements() )
		// {
		// processor.createProcessor().process( context );
		// }
		// list.add( session.getParameters() );
		// session.setWriteScript( false );
		// while ( iter.hasNext() ) {
		// session.setParameters( new LinkedList< Object >() );
		// item = iter.next();
		// if ( ! ( item instanceof DataObject ) ) {
		// LOG.warn( "The item of collections is not an instance of DataObject."
		// );
		// context.setStatus( ContextStatus.TERMINATED );
		// return ;
		// }
		// if ( getDefinition().getName() != null && !"".equals(
		// getDefinition().getName() ) ) {
		// ( ( DataObject ) item ).set( getDefinition().getName(), sdo.get(
		// getDefinition().getName() ) );
		// }
		// context.setIn( item );
		// for ( ProcessorDefinition processor : getDefinition().getElements() )
		// {
		// processor.createProcessor().process( context );
		// }
		// list.add( session.getParameters() );
		// }
		// session.setWriteScript( true );
		// session.setParameters( list );
		// context.setIn( sdo );
		assembleClose( session );
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
	private void assembleDataObject( DataContext context, DataObject item ) throws Exception {
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
