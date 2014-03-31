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
import net.vdrinkup.alpaca.sql.definition.AbstractSQLDefinition;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-1-27
 */
public class SQLProcessor extends AbstractProcessor< AbstractSQLDefinition > {
	/**
	 * @param t
	 */
	public SQLProcessor( AbstractSQLDefinition t ) {
		super( t );
	}

	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		Iterator< ProcessorDefinition > iter = getDefinition().getElements().iterator();
		while ( iter.hasNext() ) {
			try {
				iter.next().createProcessor().process( context );
			} catch ( Exception e ) {
				context.setException( e );
				context.setStatus( ContextStatus.EXCEPTION );
				callback.done( true );
			}
		}
		return true;
	}

}
