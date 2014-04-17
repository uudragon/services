/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.flow.processor;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.SchemeConstants;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextConstants;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.FlowDefinition;

/**
 * 
 * <p>
 * </p>
 * 
 * @author liubing Date Nov 26, 2013
 */
public class FlowProcessor extends AbstractProcessor< FlowDefinition > {

	/**
	 * @param t
	 */
	public FlowProcessor( FlowDefinition t ) {
		super( t );
	}

	@Override
	public boolean process( DataContext context, DoneCallback callback ) {
		process: {
			// context.pushLocation();
			final StringBuffer location = new StringBuffer(
					SchemeConstants.Prefix.FLOW_PREFIX ).append( getDefinition().getId() );
			context.setProperty( ContextConstants.CURRENT_LOCATION, location.toString() );
			if ( getDefinition().getOutputs() == null
					|| getDefinition().getOutputs().size() == 0 ) {
				break process;
			}
 			for ( ProcessorDefinition definition : getDefinition().getOutputs() ) {
				try {
					definition.createProcessor().process( context );
				} catch ( Exception e ) {
					LOG.error( e.getMessage(), e );
					context.setException( e );
					context.setStatus( ContextStatus.EXCEPTION );
					break process;
				}
				// 恢复当前执行定位信息
				context.setProperty( ContextConstants.CURRENT_LOCATION,
						location.toString() );
			}
			// //本流程执行完成，恢复之前的执行定位信息
			// context.popLocation();
		}
		return true;

	}

}
