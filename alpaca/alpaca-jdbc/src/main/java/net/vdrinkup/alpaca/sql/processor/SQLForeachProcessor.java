/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.jdbc.session.AbstractSQLSession;
import net.vdrinkup.alpaca.jdbc.session.SQLUpdateSessionImpl;
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

	@SuppressWarnings( "unchecked" )
	@Override
	protected void handle( DataContext context ) throws Exception {
		AbstractSQLSession session = context.getOut();
		if ( session instanceof SQLUpdateSessionImpl ) {
			( ( SQLUpdateSessionImpl ) session ).setBatch( true );
		}
		List< Object > list = session.getParameters();
		DataObject sdo = context.getIn();
		Object bindingValue = sdo.get( getDefinition().getBinding() );
		if ( bindingValue == null 
				|| ! ( bindingValue instanceof Collection< ? > ) ) {
			throw new IllegalArgumentException( "The value of binding element can not be null or must be an instanceof Collection." );
		}
		Collection< Object > collection = ( Collection< Object > ) bindingValue;
		Iterator< Object > iter = collection.iterator();
		session.setParameters( new LinkedList< Object >() );
		Object item = null;
		if ( ! iter.hasNext() ) {
			LOG.warn( "Collections has no more elements, <foreach> can not be process." );
			context.setStatus( ContextStatus.TERMINATED );
			return ;
		}
		item = iter.next();
		if ( ! ( item instanceof DataObject ) ) {
			LOG.warn( "The item of collections is not an instance of DataObject." );
			context.setStatus( ContextStatus.TERMINATED );
			return ;
		}
		if ( getDefinition().getName() != null && !"".equals( getDefinition().getName() ) ) { 
			( ( DataObject ) item ).set( getDefinition().getName(), sdo.get( getDefinition().getName() ) );
		}
		context.setIn( item );
		for ( ProcessorDefinition processor : getDefinition().getElements() ) {
			processor.createProcessor().process( context );
		}
		list.add( session.getParameters() );
		session.setWriteScript( false );
		while ( iter.hasNext() ) {
			session.setParameters( new LinkedList< Object >() );
			item = iter.next();
			if ( ! ( item instanceof DataObject ) ) {
				LOG.warn( "The item of collections is not an instance of DataObject." );
				context.setStatus( ContextStatus.TERMINATED );
				return ;
			}
			if ( getDefinition().getName() != null && !"".equals( getDefinition().getName() ) ) { 
				( ( DataObject ) item ).set( getDefinition().getName(), sdo.get( getDefinition().getName() ) );
			}
			context.setIn( item );
			for ( ProcessorDefinition processor : getDefinition().getElements() ) {
				processor.createProcessor().process( context );
			}
			list.add( session.getParameters() );
		}
		session.setWriteScript( true );
		session.setParameters( list );
		context.setIn( sdo );
	}

}
