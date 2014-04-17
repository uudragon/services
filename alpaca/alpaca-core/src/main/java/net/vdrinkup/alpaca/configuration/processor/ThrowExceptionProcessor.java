/**
 * 
 */
package net.vdrinkup.alpaca.configuration.processor;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.AbstractProcessor;
import net.vdrinkup.alpaca.configuration.model.ThrowExceptionDefinition;
import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常抛出配置处理器
 * @author pluto.bing.liu
 */
public class ThrowExceptionProcessor extends AbstractProcessor< ThrowExceptionDefinition > {

	private static Logger LOG = LoggerFactory.getLogger( ThrowExceptionProcessor.class );
	
	public ThrowExceptionProcessor( ThrowExceptionDefinition t ) {
		super( t );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	protected boolean process( DataContext context, DoneCallback callback ) {
		Exception exception = null;
		try {
			final Class< ? extends Exception > clazz = ( Class< ? extends Exception > ) Class.forName( getDefinition().getClazz(), false, context.getClass().getClassLoader() );
			exception = clazz.newInstance();
		} catch ( InstantiationException e ) {
			exception = e;
		} catch ( IllegalAccessException e ) {
			exception = e;
		} catch ( ClassNotFoundException e ) {
			exception = e;
		}
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( exception.getMessage(), exception );
		}
		context.setException( exception );
		context.setStatus( ContextStatus.EXCEPTION );
		callback.done( true );
		return true;
	}

}
