/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.jdbc.session.AbstractSQLSession;
import net.vdrinkup.alpaca.jdbc.session.SQLBatchUpdateSessionImpl;
import net.vdrinkup.alpaca.sql.definition.SQLBatchUpdateDefinition;


/**
 * SQL批量更新接口
 * <p></p>
 * @author liubing
 * Date Feb 19, 2014
 */
public class SQLBatchUpdateProcessor extends AbstractProcessor< SQLBatchUpdateDefinition > {

	public SQLBatchUpdateProcessor( SQLBatchUpdateDefinition t ) {
		super( t );
	}

	@Override
	protected void handle( DataContext context ) throws Exception {
		final SQLBatchUpdateSessionImpl session = new SQLBatchUpdateSessionImpl();
		for ( ProcessorDefinition processor : getDefinition().getElements() ) {
			processor.createProcessor().process( context );
			session.addSession( ( AbstractSQLSession ) context.getOut() );
		}
		context.setOut( session );
	}

}
