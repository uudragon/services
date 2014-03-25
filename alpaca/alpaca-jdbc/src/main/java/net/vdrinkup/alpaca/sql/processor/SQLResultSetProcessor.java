/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataFactory;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.sql.definition.SQLCollectionDefinition;
import net.vdrinkup.alpaca.sql.definition.SQLElementDefinition;
import net.vdrinkup.alpaca.sql.definition.SQLResultSetDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 11, 2014
 */
public class SQLResultSetProcessor extends AbstractProcessor< SQLResultSetDefinition > {

	/**
	 * @param t
	 */
	public SQLResultSetProcessor( SQLResultSetDefinition t ) {
		super( t );
	}

	@Override
	protected void handle( DataContext context ) throws Exception {
		ResultSet rs = context.getIn();
		Map< Object, DataObject > resultMap = new LinkedHashMap< Object, DataObject >( 16 );
		DataObject out = context.getOut();
		DataObject item;
		Object key;
		int index = 0;
		while ( rs.next() ) {
			item = DataFactory.INSTANCE.create();
			context.setOut( item );
			if ( getDefinition().getId() != null ) {
				getDefinition().getId().createProcessor().process( context );
			} else {
				key = index;
			}
			for ( SQLElementDefinition element : getDefinition().getElements() ) {
				element.createProcessor().process( context );
			}
			context.setOut( resultMap );
			if ( getDefinition().getCollections().size() > 0 ) { 
				for ( SQLCollectionDefinition collection : getDefinition().getCollections() ) {
					key = item.get( collection.getName() );
					if ( resultMap.get( key ) == null ) {
						key = item.get( collection.getName() );
						resultMap.put( key, item );
					}
					collection.createProcessor().process( context );
				}
			} else {
				resultMap.put( index, item );
			}
			if ( getDefinition().isSingle() ) {
				break ;
			}
			index++;
		}
		if ( resultMap.size() > 0 ) {
			if ( getDefinition().isSingle() ) {
				out.set( getDefinition().getBinding(), resultMap.values().iterator().next() );
			} else {
				out.set( getDefinition().getBinding(), new ArrayList< DataObject >( resultMap.values() ) );
			}
		}
		context.setOut( out );
	}

}
