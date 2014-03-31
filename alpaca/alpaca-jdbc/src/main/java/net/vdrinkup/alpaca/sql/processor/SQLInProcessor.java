/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataFactory;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.sql.definition.SQLInDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Jan 15, 2014
 */
public class SQLInProcessor extends AbstractProcessor< SQLInDefinition > {

	public SQLInProcessor( SQLInDefinition t ) {
		super( t );
	}

	@Override
	public boolean process( DataContext context, DoneCallback callback ) {
		process : if ( getDefinition().isLeaf() ) {
			DataObject sdo = context.getOut();
			if ( sdo == null ) {
				sdo = DataFactory.INSTANCE.create();
			}
			sdo.set( getDefinition().getBinding(), context.getIn() );
		} else {
			for ( ProcessorDefinition definition : getDefinition().getElements() ) {
				try {
					definition.createProcessor().process( context );
				} catch ( Exception e ) {
					LOG.error( e.getMessage(), e );
					context.setException( e );
					context.setStatus( ContextStatus.EXCEPTION );
					break process;
				}
			}
		}
		return true;
	}

}
