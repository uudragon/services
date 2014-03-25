/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataFactory;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.sql.definition.SQLCollectionDefinition;
import net.vdrinkup.alpaca.sql.definition.SQLElementDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 19, 2014
 */
public class SQLCollectioniProcessor extends AbstractProcessor< SQLCollectionDefinition > {

	public SQLCollectioniProcessor( SQLCollectionDefinition t ) {
		super( t );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	protected void handle( DataContext context ) throws Exception {
		Map< String, DataObject > sdo = context.getOut();
		ResultSet rs = context.getIn();
		DataObject item;
		Object associationName;
		DataObject associationValue;
		List< DataObject > resultList;
		do {
			item = DataFactory.INSTANCE.create();
			context.setOut( item );
			if ( getDefinition().getId() != null ) {
				getDefinition().getId().createProcessor().process( context );
			}
			for ( SQLElementDefinition element : getDefinition().getElements() ) {
				element.createProcessor().process( context );
			}
			associationName = item.get( getDefinition().getName() );
			associationValue = sdo.get( associationName );
			if ( associationValue == null ) {
				rs.previous();
				break ;
			} else {
				resultList = ( List< DataObject > ) associationValue.get( getDefinition().getBinding() );
				if ( resultList == null ) {
					resultList = new LinkedList< DataObject >();
					associationValue.setList( getDefinition().getBinding(), resultList );
				}
				resultList.add( item );
			}
		} while ( rs.next() );
		context.setOut( sdo );
	}

}
