/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset;

import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
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
	public void handle( DataContext context ) throws Exception {
		MessageDirection direction = context.getProperty( MessageSetConstants.MESSAGE_DIRECTION, MessageDirection.class );
		switch ( direction ) {
			case IN : decode( context ); break;
			case OUT : encode( context ); break;
			default : throw new IllegalArgumentException( 
					"MESSAGE_DIRECTION is error. expected [IN/OUT], actual " + direction );
		}
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
