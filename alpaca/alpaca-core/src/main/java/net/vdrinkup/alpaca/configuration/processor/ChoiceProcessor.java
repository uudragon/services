/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.processor;

import java.util.List;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ChoiceDefinition;
import net.vdrinkup.alpaca.configuration.model.WhenDefinition;
import net.vdrinkup.alpaca.context.DataContext;


/**
 * <p></p>
 * @author bing.liu
 * Date 2012-2-10
 */
public class ChoiceProcessor extends AbstractProcessor< ChoiceDefinition > {

	/**
	 * @param definition
	 */
	public ChoiceProcessor( ChoiceDefinition definition ) {
		super( definition );
	}

	@Override
	public void handle( DataContext context ) throws Exception {
		List< WhenDefinition > whenClauses = getDefinition().getWhenClauses();
		boolean isMatch = false;
		for ( WhenDefinition whenClause : whenClauses ) {
			isMatch = whenClause.getExpression().matches( context );
			if ( isMatch ) {
				whenClause.createProcessor().process( context );
			}
		}
		if ( ! isMatch ) {
			getDefinition().getOtherwise().createProcessor().process( context );
		}
	}

}
