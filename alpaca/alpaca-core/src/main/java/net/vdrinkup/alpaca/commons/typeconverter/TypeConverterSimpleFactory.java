/**
 * 
 */
package net.vdrinkup.alpaca.commons.typeconverter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.vdrinkup.alpaca.commons.typeconverter.impl.BigDecimalConverter;
import net.vdrinkup.alpaca.commons.typeconverter.impl.BooleanConverter;
import net.vdrinkup.alpaca.commons.typeconverter.impl.DateTimeConverter;
import net.vdrinkup.alpaca.commons.typeconverter.impl.DoubleConverter;
import net.vdrinkup.alpaca.commons.typeconverter.impl.FloatConverter;
import net.vdrinkup.alpaca.commons.typeconverter.impl.IntegerConverter;
import net.vdrinkup.alpaca.commons.typeconverter.impl.LongConverter;
import net.vdrinkup.alpaca.commons.typeconverter.impl.ShortConverter;
import net.vdrinkup.alpaca.commons.typeconverter.impl.StringConverter;


/**
 * 类型转换简单工厂类
 * @author bing
 */
public class TypeConverterSimpleFactory {
	
	public static final TypeConverterSimpleFactory INSTANCE = new TypeConverterSimpleFactory();
	
	static final Map< String, Converter > TYPE_REGISTER;
	
	static {
		TYPE_REGISTER = new HashMap< String, Converter >();
		TYPE_REGISTER.put( short.class.getSimpleName().toLowerCase(), new ShortConverter() );
		TYPE_REGISTER.put( Short.class.getSimpleName().toLowerCase(), new ShortConverter() );
		TYPE_REGISTER.put( boolean.class.getSimpleName().toLowerCase(), new BooleanConverter() );
		TYPE_REGISTER.put( Boolean.class.getSimpleName().toLowerCase(), new BooleanConverter() );
		TYPE_REGISTER.put( int.class.getSimpleName().toLowerCase(), new IntegerConverter() );
		TYPE_REGISTER.put( Integer.class.getSimpleName().toLowerCase(), new IntegerConverter() );
		TYPE_REGISTER.put( long.class.getSimpleName().toLowerCase(), new LongConverter() );
		TYPE_REGISTER.put( Long.class.getSimpleName().toLowerCase(), new LongConverter() );
		TYPE_REGISTER.put( float.class.getSimpleName().toLowerCase(), new FloatConverter() );
		TYPE_REGISTER.put( Float.class.getSimpleName().toLowerCase(), new FloatConverter() );
		TYPE_REGISTER.put( double.class.getSimpleName().toLowerCase(), new DoubleConverter() );
		TYPE_REGISTER.put( Double.class.getSimpleName().toLowerCase(), new DoubleConverter() );
		TYPE_REGISTER.put( String.class.getSimpleName().toLowerCase(), new StringConverter() );
		TYPE_REGISTER.put( BigDecimal.class.getSimpleName().toLowerCase(), new BigDecimalConverter() );
		TYPE_REGISTER.put( BigDecimalConverter.DECIMAL_SIMPLE_NAME, new BigDecimalConverter() );
		TYPE_REGISTER.put( Date.class.getSimpleName().toLowerCase(), new DateTimeConverter() );	
	}
	
	public static final TypeConverterSimpleFactory getInstance() {
		return INSTANCE;
	}
	
	public Converter getConverter( String key ) {
		if ( key == null || "".equals( key ) ) {
			key = String.class.getSimpleName();
		}
		return ( Converter ) TYPE_REGISTER.get( key.toLowerCase() );
	}

}
