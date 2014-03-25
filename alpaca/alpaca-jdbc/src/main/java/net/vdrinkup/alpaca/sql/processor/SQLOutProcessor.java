/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.sql.definition.SQLOutDefinition;


/**
 * SQL报文组装处理器
 * <p></p>
 * @author liubing
 * Date Jan 15, 2014
 */
public class SQLOutProcessor extends AbstractProcessor< SQLOutDefinition > {

	/**
	 * @param t
	 */
	public SQLOutProcessor( SQLOutDefinition t ) {
		super( t );
	}

	@Override
	public void handle( DataContext context ) throws Exception {
		if ( getDefinition().getSqlDef() != null ) {
			getDefinition().getSqlDef().createProcessor().process( context );
		}
	}

}
