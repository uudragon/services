/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
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
	protected void handle( DataContext context ) throws Exception {
		if ( context.getOut() == null ) {
			return ;
		}
		if ( ! ( context.getOut() instanceof AbstractSQLSession ) ) {
			throw new IllegalArgumentException( "The out must be an instance of " + AbstractSQLSession.class.getName() );
		}
		final AbstractSQLSession session = context.getOut();
		if ( ! session.isWriteScript() ) {
			return ;
		}
		if ( getDefinition().getSql() == null || "".equals( getDefinition().getSql() ) ) {
			return ;
		}
		if ( session.getScript().size() != 0 ) {
			session.getScript().write( SQLConstants.WHITESPACE );
		}
		session.getScript().write( getDefinition().getSql() );
	}

}
