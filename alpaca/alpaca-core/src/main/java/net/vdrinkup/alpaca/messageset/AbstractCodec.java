/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;

/**
 * 抽象编解码器
 * <p></p>
 * @author liubing
 * Date Jan 13, 2014
 */
public abstract class AbstractCodec< T extends ProcessorDefinition > extends AbstractProcessor< T > {

	/**
	 * @param t
	 */
	public AbstractCodec( T t ) {
		super( t );
	}

	@Override
	public boolean process( DataContext context, DoneCallback callback ) {
		MessageDirection direction = context.getProperty( MessageSetConstants.MESSAGE_DIRECTION, MessageDirection.class );
		try {
			switch ( direction ) {
				case IN : decode( context ); break;
				case OUT : encode( context ); break;
				default : 
					throw new IllegalArgumentException( 
							"MESSAGE_DIRECTION is error. expected [IN/OUT], actual " + direction );
			}
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
			context.setException( e );
			context.setStatus( ContextStatus.EXCEPTION );
		}
		return true;
	}
	
	/**
	 * 编码方法
	 * @param context
	 * @throws Exception
	 */
	protected abstract void encode( DataContext context ) throws Exception;
	/**
	 * 解码方法
	 * @param context
	 * @throws Exception
	 */
	protected abstract void decode( DataContext context ) throws Exception;
	
}
