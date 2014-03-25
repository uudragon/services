package net.vdrinkup.alpaca.service;

import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 抽象业务模板类
 * <p>
 * 继承{@link BusinessService}接口
 * </p>
 * @author liubing
 * Date Oct 24, 2013
 */
public abstract class AbstractBusinessService implements Service {
	
	protected static Logger LOG = LoggerFactory.getLogger( "com.jd.wms.services" );
	
	protected boolean running;
			
	protected AbstractBusinessService() {
		super();
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.vdrinkup.alpaca.service.Service#invoke(net.vdrinkup.alpaca.context.DataContext)
	 */
	public void invoke( DataContext context ) throws InvokeException {
		if ( ContextStatus.INVALID.equals( context.getStatus() ) ) {
			getLogger().warn( "****************Current context is invalid.******************" );
			return ;
		}
		try {
			Object ret = invoke( context.getIn() );
			context.setIn( ret );
		} catch ( Exception e ) {
			LOG.debug( e.getMessage(), e );
			context.setStatus( ContextStatus.EXCEPTION );
			context.setException( e );
		}
	}
	
	@Override
	public synchronized void start() throws ServiceException {
		if ( isStartup() ) {
			return ;
		}
		running = true;
	}

	@Override
	public synchronized void stop() throws ServiceException {
		if ( isShutdown() ) {
			return ;
		}
		running = false;
	}

	@Override
	public boolean isStartup() {
		return running;
	}

	@Override
	public boolean isShutdown() {
		return ! running;
	}
	
	public abstract < T, R > R invoke( T t ) throws Exception;

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return LOG;
	}
	
}
