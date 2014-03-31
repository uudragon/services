/*******************************************************************************
 * Copyright (c) 2013 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration;

import java.util.concurrent.CountDownLatch;

import net.vdrinkup.alpaca.DoneCallback;
import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.context.DataContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 抽象处理器
 * <p></p>
 * @author liubing
 * Date 2013-11-13
 */
public abstract class AbstractProcessor< T extends ProcessorDefinition > implements Processor {
	
	protected static Logger LOG = LoggerFactory.getLogger( AbstractProcessor.class.getPackage().getName() );
	
	protected T definition;
	
	public AbstractProcessor( T t ) {
		this.definition = t;
	}

	public T getDefinition() {
		return definition;
	}

	@Override
	public void process( final DataContext context ) throws Exception {
		switch ( context.getStatus() ) {
		case EXCEPTION :
			return ;
		case TERMINATED :
			return ;
		default : 
			final CountDownLatch latch = new CountDownLatch(1);
	        boolean sync = process( context, new DoneCallback() {
	            public void done( boolean doneSync ) {
	                if (!doneSync) {
	                    latch.countDown();
	                }
	            }
	        });
	        if (!sync) {
	            latch.await();
	        }
	        break ;
		}
	}
	
	protected abstract boolean process( DataContext context, DoneCallback callback );
	
}
