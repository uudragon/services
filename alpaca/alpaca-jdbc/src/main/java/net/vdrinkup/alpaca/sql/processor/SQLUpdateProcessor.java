/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import java.util.Iterator;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.jdbc.session.SQLUpdateSessionImpl;
import net.vdrinkup.alpaca.sql.definition.AbstractSQLUpdateDefinition;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-17
 */
public class SQLUpdateProcessor extends AbstractProcessor< AbstractSQLUpdateDefinition > {

	/**
	 * @param t
	 */
	public SQLUpdateProcessor( AbstractSQLUpdateDefinition t ) {
		super( t );
	}

	@Override
	protected void handle( DataContext context ) throws Exception {
		SQLUpdateSessionImpl impl = new SQLUpdateSessionImpl();
		context.setOut( impl );
		Iterator< ProcessorDefinition > iter = getDefinition().getElements().iterator();
		while ( iter.hasNext() ) {
			if ( ! ContextStatus.VALID.equals( context.getStatus() ) ) {
				break ;
			}
			iter.next().createProcessor().process( context );
		}
	}

}