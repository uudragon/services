/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.processor;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.sql.definition.EffectCountDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 18, 2014
 */
public class EffectCountProcessor extends AbstractProcessor< EffectCountDefinition > {

	/**
	 * @param t
	 */
	public EffectCountProcessor( EffectCountDefinition t ) {
		super( t );
	}

	@Override
	protected void handle( DataContext context ) throws Exception {
		if ( ! ( context.getIn() instanceof Integer ) && ! ( context.getIn() instanceof int[] ) ) {
			throw new IllegalArgumentException( "The input of current context must be an instance of java.lang.Integer or int[]" );
		}
		String key = getDefinition().getBinding() == null ? "effect_count" : getDefinition().getBinding();
		DataObject sdo = context.getOut();
		sdo.set( key, context.getIn() );
	}

}
