/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import java.util.Iterator;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.jdbc.session.SQLQuerySessionImpl;
import net.vdrinkup.alpaca.sql.definition.SQLSelectDefinition;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-17
 */
public class SQLSelectProcessor extends AbstractProcessor< SQLSelectDefinition > {

	/**
	 * @param t
	 */
	public SQLSelectProcessor( SQLSelectDefinition t ) {
		super( t );
	}

	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		SQLQuerySessionImpl impl = new SQLQuerySessionImpl();
		context.setOut( impl );
		Iterator< ProcessorDefinition > iter = getDefinition().getElements().iterator();
		loop : while ( iter.hasNext() ) {
			try {
				iter.next().createProcessor().process( context );
			} catch ( Exception e ) {
				context.setException( e );
				context.setStatus( ContextStatus.EXCEPTION );
				callback.done( true );
				break loop;
			}
		}
		return true;
	}

}
