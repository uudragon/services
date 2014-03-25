/**
 * 
 */
package net.vdrinkup.alpaca.commons.typeconverter.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;

import net.vdrinkup.alpaca.commons.typeconverter.AbstractConverter;


/**
 * 数字类型转换器
 * @author bing
 */
public abstract class NumeralConverter extends AbstractConverter {

	protected Object convertToType( Object value ) throws Exception {
		Object result = null;
		convert : if ( value != null ) {
			if ( defaultType().equals( BigDecimal.class ) ) {
				result = toBigDecimal( value );
			} else {
				Number valueNumber = null;
				if ( value instanceof Boolean ) {
					valueNumber = ( ( Boolean ) value ) == true ? 1 : 0;
				} else {
					final String valueStr = value.toString();
					if ( "".equals( valueStr ) ) {
						result = null;
						break convert;
					}
					valueNumber = NumberFormat.getInstance().parse( valueStr );
				}
				result = toNumber( defaultType(), valueNumber );
			}
		}
		return result;
	}

	private Object toNumber( final Class< ? > clazz, 
			final Number number ) throws Exception {
		Object obj = null;
		if ( clazz.equals( Integer.class ) ) {
			obj = toInt( number );
		}
		if ( clazz.equals( Long.class ) ) {
			obj = toLong( number );
		}
		if ( clazz.equals( Float.class ) ) {
			obj = toFloat( number );
		}
		if ( clazz.equals( Double.class ) ) {
			obj = toDouble( number );
		}
		return obj;
	}

	private BigDecimal toBigDecimal( final Object value ) {
		return new BigDecimal( value.toString() );
	}

	private Float toFloat( final Number number ) {
		if ( Float.MIN_VALUE > number.doubleValue() ) {
			throw new RuntimeException( "The value is too small Float." );
		}
		if ( Float.MAX_VALUE < number.doubleValue() ) {
			throw new RuntimeException( "The value is too large Float." );
		}
		return Float.valueOf( number.floatValue() );
	}

	private Double toDouble( final Number number ) {
		return Double.valueOf( number.doubleValue() );
	}

	private Long toLong( final Number number ) {
		return Long.valueOf( number.longValue() );
	}

	private Integer toInt( final Number number ) {
		if ( Integer.MIN_VALUE > number.longValue() ) {
			throw new RuntimeException( "The value is too small Integer." );
		}
		if ( Integer.MAX_VALUE < number.longValue() ) {
			throw new RuntimeException( "The value is too large Integer." );
		}
		return Integer.valueOf( number.intValue() );
	}
}
