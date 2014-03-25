/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.processor;

import java.util.Map;
import java.util.UUID;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.definition.UUIDDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 21, 2014
 */
public class UUIDProcessor extends AbstractProcessor< UUIDDefinition > {

	/**
	 * @param t
	 */
	public UUIDProcessor( UUIDDefinition t ) {
		super( t );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	protected void handle( DataContext context ) throws Exception {
		String uuidString = UUID.randomUUID().toString();
		( ( Map< String, Object > ) context.getIn() ).put( getDefinition().getBinding(), uuidString.replaceAll( "-", "" ) );
	}

}
