/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.processor;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.dms.DMSProviderManager;
import net.vdrinkup.alpaca.dms.Provider;
import net.vdrinkup.alpaca.flow.definition.TransactedDefinition;
import net.vdrinkup.alpaca.quality.transaction.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * <p>
 * </p>
 * 
 * @author liubing Date Nov 28, 2013
 */
public class TransactedProcessor extends
		AbstractProcessor< TransactedDefinition > {

	private static Logger LOG = LoggerFactory
			.getLogger( TransactedProcessor.class );

	public TransactedProcessor( TransactedDefinition t ) {
		super( t );
	}

	@Override
	public boolean process( DataContext context, DoneCallback callback ) {
		String dms = getDefinition().getDms();
		Provider provider = DMSProviderManager.getInstance().lookup( dms );
		Transaction transaction = null;
		try {
			transaction = provider.createTransaction( false );
			transaction.setIsoLation( getDefinition().getIsolation().getValue() );
			context.setTransaction( transaction );
			context.setTransacted( true );
			for ( ProcessorDefinition processor : getDefinition().getOutputs() ) {
				processor.createProcessor().process( context );
			}
			if ( ContextStatus.EXCEPTION.equals( context.getStatus() ) ) {
				LOG.error(
						"The status of context [{}] is exception. The transaction will be rollback.",
						context.getId() );
				transaction.rollback();
			} else {
				transaction.commit();
				LOG.info( "The transaction of context [{}] is committed.",
						context.getId() );
			}
		} catch ( Exception e ) {
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
		} finally {
			if ( transaction != null ) {
				try {
					transaction.close();
				} catch ( Exception e ) {
					LOG.error( e.getMessage(), e );
				}
			}
			context.setTransacted( false );
			context.setTransaction( null );
		}
		return true;
	}

}
