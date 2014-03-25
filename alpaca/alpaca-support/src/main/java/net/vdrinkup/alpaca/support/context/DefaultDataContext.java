/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.support.context;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.vdrinkup.alpaca.context.ContextStatus;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.flow.FlowDefinition;
import net.vdrinkup.alpaca.quality.transaction.Transaction;


/**
 * 默认的交换上下文对象
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-2-24
 */
public class DefaultDataContext implements DataContext {

	private static final long serialVersionUID = 5907953635258703435L;
	
	private Map< String, Object > properties = new HashMap< String, Object >( 16 );
	
	private String id;
	
	private Throwable t;
	
	private ContextStatus status = ContextStatus.VALID;
	
	private boolean transacted;
	
	private Transaction transaction;
	
	private Object attach;

	private FlowDefinition flowDefinition;
	
	private Object in;
	
	private Object out;
	
	public DefaultDataContext() {
		id = KeyGen.generate();
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T > T getAttach() {
		return ( T ) attach;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public ContextStatus getStatus() {
		return this.status;
	}

	@Override
	public void setStatus( ContextStatus status ) {
		this.status = status;
	}

	@Override
	public Map< String, Object > getProperties() {
		return properties;
	}

	@Override
	public void setProperty( String key, Object value ) {
		properties.put( key, value );
	}

	@Override
	public Object getProperty( String key ) {
		return properties.get( key );
	}

	@Override
	public < T > T getProperty( String key, Class< T > clazz ) {
		return getProperty( key, clazz, null );
	}

	@Override
	public < T > T getProperty( String key, Class< T > clazz, T defaultValue ) {
		Object value = properties.get( key );
		if ( value == null ) {
			return defaultValue;
		}
		return clazz.cast( value );
	}

	@Override
	public Object removeProperty( String key ) {
		return properties.remove( key );
	}

	@Override
	public void setException( Throwable t ) {
		this.t = t;
	}

	@Override
	public Throwable getException() {
		return t;
	}

	@Override
	public boolean isTransacted() {
		return transacted;
	}
	
	public void setTransacted( boolean transacted ) {
		this.transacted = transacted;
	}

	@Override
	public Transaction getTransaction() {
		return transaction;
	}

	@Override
	public void setTransaction( Transaction transaction ) {
		this.transaction = transaction;
	}

	@Override
	public void setFlowDefinition( FlowDefinition definition ) {
		this.flowDefinition = definition;
	}

	@Override
	public FlowDefinition getFlowDefinition() {
		return flowDefinition;
	}

	@Override
	public DataContext copy() {
		DataContext context = new DefaultDataContext();
		context.getProperties().putAll( this.properties );
		context.setException( this.t );
		context.setFlowDefinition( this.flowDefinition );
		context.setStatus( this.status );
		return context;
	}

	protected static class KeyGen {
		public static synchronized String generate() {
			return UUID.randomUUID().toString();
		}
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T > T getIn() {
		return ( T ) this.in;
	}

	@Override
	public < T > void setIn( T in ) {
		this.in = in;
	}

	@Override
	public boolean hasOut() {
		return this.out == null ? false : true;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T > T getOut() {
		return ( T ) this.out;
	}

	@Override
	public < T > void setOut( T out ) {
		this.out = out;
	}
	
}
