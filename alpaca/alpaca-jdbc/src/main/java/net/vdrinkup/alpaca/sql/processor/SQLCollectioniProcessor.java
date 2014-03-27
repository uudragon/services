/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.DataContext;
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

	@Override
	protected void handle( DataContext context ) throws Exception {
		for ( SQLElementDefinition element : getDefinition().getElements() ) {
			element.createProcessor().process( context );
		}
	}

}
