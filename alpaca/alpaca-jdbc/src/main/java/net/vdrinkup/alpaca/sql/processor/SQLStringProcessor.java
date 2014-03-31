/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import java.io.IOException;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.jdbc.session.AbstractSQLSession;
import net.vdrinkup.alpaca.sql.SQLConstants;
import net.vdrinkup.alpaca.sql.definition.SQLStringDefinition;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-1-27
 */
public class SQLStringProcessor extends AbstractProcessor< SQLStringDefinition > {

	public SQLStringProcessor( SQLStringDefinition t ) {
		super( t );
	}

	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		if ( context.getOut() == null ) {
			callback.done( true );
			return true;
		}
		if ( ! ( context.getOut() instanceof AbstractSQLSession ) ) {
			throw new IllegalArgumentException( "The out must be an instance of " + AbstractSQLSession.class.getName() );
		}
		final AbstractSQLSession session = context.getOut();
		if ( ! session.isWriteScript() ) {
			callback.done( true );
			return true;
		}
		if ( getDefinition().getSql() == null || "".equals( getDefinition().getSql() ) ) {
			callback.done( true );
			return true;
		}
		if ( session.getScript().size() != 0 ) {
			session.getScript().write( SQLConstants.WHITESPACE );
		}
		try {
			session.getScript().write( getDefinition().getSql() );
		} catch ( IOException e ) {
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
		}
		return true;
	}

}
