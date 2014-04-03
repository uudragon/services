/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.support.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.vdrinkup.alpaca.data.DataObject;

import org.apache.commons.jxpath.JXPathContext;


/**
 * 可映射的数据对象
 * <p>
 * 该类同时实现{@link net.vdrinkup.alpaca.data.DataObject}与{@link java.util.Map}接口。
 * </p>
 * @author pluto.bing.liu
 * Date 2014-2-23
 */
public class MappedDataObject implements DataObject, Map< String, Object >, Serializable {
	
	private Map< String, Object > embedded = new LinkedHashMap< String, Object >( 16 );
	
	private JXPathContext context = JXPathContext.newContext( embedded );
	
	private static final long serialVersionUID = 3555254404107020410L;

	@Override
	public Object get( String path ) {
		return context.getValue( path );
	}

	@Override
	public void set( String path, Object value ) {
		context.setValue( path, value );
	}

	@Override
	public byte[] getBytes( String path ) {
		Object value = get( path );
		if ( value != null && ! ( value instanceof byte[] ) ) {
			throw new ClassCastException( "The value of '" + path + "' can not cast byte[]" );
		}
		return castValue( value, byte[].class );
	}

	@Override
	public void setBytes( String path, byte[] value ) {
		embedded.put( path, value );
	}

	@Override
	public boolean getBoolean( String path ) {
		Object value = get( path );
		if ( value != null && ! ( value instanceof Boolean ) ) {
			throw new ClassCastException( "The value of '" + path + "' can not cast boolean" );
		}
		return value == null ? false : Boolean.class.cast( value );
	}

	@Override
	public void setBoolean( String path, boolean value ) {
		set( path, value );
	}

	@Override
	public int getInt( String path ) {
		Object value = get( path );
		if ( value != null && ! ( value instanceof Integer ) ) {
			throw new ClassCastException( "The value of '" + path + "' can not cast Integer" );
		}
		return value == null ? 0 : Integer.class.cast( value ).intValue();
	}

	@Override
	public void setInt( String path, int value ) {
		set( path, value );
	}

	@Override
	public long getLong( String path ) {
		Object value = get( path );
		if ( value != null && ! ( value instanceof Long ) ) {
			throw new ClassCastException( "The value of '" + path + "' can not cast Long" );
		}
		return value == null ? 0L : Long.class.cast( value ).longValue();
	}

	@Override
	public void setLong( String path, long value ) {
		set( path, value );
	}

	@Override
	public float getFloat( String path ) {
		Object value = get( path );
		if ( value != null && ! ( value instanceof Float ) ) {
			throw new ClassCastException( "The value of '" + path + "' can not cast Float" );
		}
		return value == null ? 0.0F : Float.class.cast( value ).floatValue();
	}

	@Override
	public void setFloat( String path, float value ) {
		set( path, value );
	}

	@Override
	public double getDouble( String path ) {
		Object value = get( path );
		if ( value != null && ! ( value instanceof Double ) ) {
			throw new ClassCastException( "The value of '" + path + "' can not cast Double" );
		}
		return value == null ? 0.00D : Double.class.cast( value ).doubleValue();
	}

	@Override
	public void setDouble( String path, double value ) {
		set( path, value );
	}

	@Override
	public Date getDate( String path ) {
		Object value = get( path );
		if ( value != null && ! ( value instanceof Date ) ) {
			throw new ClassCastException( "The value of '" + path + "' can not cast Date" );
		}
		return castValue( value, Date.class );
	}

	@Override
	public void setDate( String path, Date date ) {
		set( path, date );
	}

	@Override
	public BigDecimal getBigDecimal( String path ) {
		Object value = get( path );
		if ( value != null && ! ( value instanceof BigDecimal ) ) {
			throw new ClassCastException( "The value of '" + path + "' can not cast BigDecimal" );
		}
		return castValue( value, BigDecimal.class );
	}

	@Override
	public void setBigDecimal( String path, BigDecimal value ) {
		set( path, value );
	}

	@Override
	public short getShort( String path ) {
		Object value = get( path );
		if ( value != null && ! ( value instanceof Short ) ) {
			throw new ClassCastException( "The value of '" + path + "' can not cast Short" );
		}
		return value == null ? 0 : Short.class.cast( value ).shortValue();
	}

	@Override
	public void setShort( String path, short value ) {
		set( path, value );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public < T > List< T > getList( String path ) {
		Object value = get( path );
		if ( value != null && ! ( value instanceof List ) ) {
			throw new ClassCastException( "The value of '" + path + "' can not cast List" );
		}
		return castValue( value, List.class );
	}
	
	@Override
	public < T > void setList( String path, List< T > value ) {
		set( path, value );
	}

	@Override
	public DataObject getDataObject( String path ) {
		Object value = get( path );
		if ( value != null && ! ( value instanceof DataObject ) ) {
			throw new ClassCastException( "The value of '" + path + "' can not cast DataObject" );
		}
		return castValue( value, DataObject.class );
	}

	@Override
	public void setDataObject( String path, DataObject value ) {
		set( path, value );
	}
	
	public Map< String, Object > getMap() {
		return embedded;
	}

	@Override
	public Object clone() {
		MappedDataObject dataObject = new MappedDataObject();
		dataObject.getMap().putAll( this.embedded );
		return dataObject;
	}
	
	public String toString() {
		return this.embedded.toString();
	}

	@Override
	public String getString( String path ) {
		Object value = get( path );
		if ( value != null && ! ( value instanceof String ) ) {
			throw new ClassCastException( "The value of '" + path + "' can not cast String" );
		}
		return castValue( value, String.class );
	}

	@Override
	public void setString( String path, String value ) {
		set( path, value );
	}

	@Override
	public Number getNumber( String path ) {
		Object value = get( path );
		if ( value != null && ! ( value instanceof Number ) ) {
			throw new ClassCastException( "The value of '" + path + "' can not cast Number" );
		}
		return castValue( value, Number.class );
	}

	@Override
	public void setNumber( String path, Number number ) {
		set( path, number );
	}

	@Override
	public int size() {
		return embedded.size();
	}

	@Override
	public boolean isEmpty() {
		return embedded.isEmpty();
	}

	@Override
	public boolean containsKey( Object key ) {
		return embedded.containsKey( key );
	}

	@Override
	public boolean containsValue( Object value ) {
		return embedded.containsValue( value );
	}

	@Override
	public Object get( Object key ) {
		return embedded.get( key );
	}

	@Override
	public Object put( String key, Object value ) {
		return embedded.put( key, value );
	}

	@Override
	public Object remove( Object key ) {
		return embedded.remove( key );
	}

	@Override
	public void putAll( Map< ? extends String, ? extends Object > m ) {
		embedded.putAll( m );
	}

	@Override
	public void clear() {
		embedded.clear();
	}

	@Override
	public Set< String > keySet() {
		return embedded.keySet();
	}

	@Override
	public Collection< Object > values() {
		return embedded.values();
	}

	@Override
	public Set< java.util.Map.Entry< String, Object >> entrySet() {
		return embedded.entrySet();
	}
	
	private < T > T castValue( Object value,  Class< T > clazz ) {
		return value == null ? null : clazz.cast( value );
	}
	
}
