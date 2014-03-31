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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataFactory;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.sql.CacheKey;
import net.vdrinkup.alpaca.sql.definition.SQLCollectionDefinition;
import net.vdrinkup.alpaca.sql.definition.SQLElementDefinition;
import net.vdrinkup.alpaca.sql.definition.SQLResultSetDefinition;

/**
 * 
 * <p>
 * </p>
 * 
 * @author liubing Date Feb 11, 2014
 */
public class SQLResultSetProcessor extends
		AbstractProcessor< SQLResultSetDefinition > {

	/**
	 * @param t
	 */
	public SQLResultSetProcessor( SQLResultSetDefinition t ) {
		super( t );
	}

	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		ResultSet rs = context.getIn();
		Map< Object, DataObject > cacheResultMap = new LinkedHashMap< Object, DataObject >(
				16 );
		DataObject out = context.getOut();
		DataObject item;
		int index = 0;
		CacheKey cacheKey = null;
		try {
			while ( rs.next() ) {
				cacheKey = createCacheKey( rs, getDefinition().getElements() );
				item = cacheResultMap.get( cacheKey );
				if ( item == null ) {
					item = DataFactory.INSTANCE.create();
					cacheResultMap.put( cacheKey, item );
					context.setOut( item );
					for ( SQLElementDefinition element : getDefinition()
							.getElements() ) {
						element.createProcessor().process( context );
					}
				}
				for ( SQLCollectionDefinition collection : getDefinition()
						.getCollections() ) {
					DataObject collect = DataFactory.INSTANCE.create();
					context.setOut( collect );
					collection.createProcessor().process( context );
					List< DataObject > list = item.getList( collection
							.getBinding() );
					if ( list == null ) {
						list = new LinkedList< DataObject >();
						list.add( collect );
						item.setList( collection.getBinding(), list );
					} else {
						list.add( collect );
					}
				}

				if ( getDefinition().getFetchSize() != 0
						&& getDefinition().getFetchSize() <= index ) {
					break;
				}
				index++;
			}
			if ( getDefinition().getFetchSize() == 1 ) {
				out.set( getDefinition().getBinding(), cacheResultMap.values()
						.iterator().next() );
			} else {
				out.set( getDefinition().getBinding(), new ArrayList< DataObject >(
						cacheResultMap.values() ) );
			}
			cacheResultMap.clear();
			cacheResultMap = null;
			context.setOut( out );
		} catch ( Exception e ) {
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
			callback.done( true );
		}
		return true;
	}

	private CacheKey createCacheKey( ResultSet rs,
			List< SQLElementDefinition > elements ) throws Exception {
		CacheKey cacheKey = new CacheKey();
		for ( SQLElementDefinition e : elements ) {
			cacheKey.update( e.getName() );
			String value = rs.getString( e.getName() );
			if ( value != null ) {
				cacheKey.update( value );
			}
		}
		return cacheKey;
	}

}
