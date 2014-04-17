/**
 * 
 */
package net.vdrinkup.alpaca.commons.typeconverter.impl;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import net.vdrinkup.alpaca.commons.typeconverter.AbstractConverter;
import net.vdrinkup.alpaca.commons.typeconverter.ConversionException;

/**
 * @author pluto.bing.liu
 * 
 */
public class DateTimeConverter extends AbstractConverter {

	private String[] patterns;
	private Locale locale;
	private TimeZone timeZone;
	private boolean useLocaleFormat;

	public DateTimeConverter() {
		super();
	}

	public void setUseLocaleFormat( boolean useLocaleFormat ) {
		this.useLocaleFormat = useLocaleFormat;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone( TimeZone timeZone ) {
		this.timeZone = timeZone;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale( Locale locale ) {
		this.locale = locale;
		setUseLocaleFormat( true );
	}

	public void setPattern( String pattern ) {
		setPatterns( new String[] { pattern } );
	}

	public String[] getPatterns() {
		return patterns;
	}

	public void setPatterns( String[] patterns ) {
		this.patterns = patterns;
		if ( patterns != null && patterns.length > 1 ) {
			StringBuilder buffer = new StringBuilder();
			for ( int i = 0; i < patterns.length; i++ ) {
				if ( i > 0 ) {
					buffer.append( ", " );
				}
				buffer.append( patterns[ i ] );
			}
		}
		setUseLocaleFormat( true );
	}

	private < T > T toDate( Class< T > type, long value ) {

		// java.util.Date
		if ( type.equals( Date.class ) ) {
			return type.cast( new Date( value ) );
		}

		// java.sql.Date
		if ( type.equals( java.sql.Date.class ) ) {
			return type.cast( new java.sql.Date( value ) );
		}

		// java.sql.Time
		if ( type.equals( java.sql.Time.class ) ) {
			return type.cast( new java.sql.Time( value ) );
		}

		// java.sql.Timestamp
		if ( type.equals( java.sql.Timestamp.class ) ) {
			return type.cast( new java.sql.Timestamp( value ) );
		}

		// java.util.Calendar
		if ( type.equals( Calendar.class ) ) {
			Calendar calendar = null;
			if ( locale == null && timeZone == null ) {
				calendar = Calendar.getInstance();
			} else if ( locale == null ) {
				calendar = Calendar.getInstance( timeZone );
			} else if ( timeZone == null ) {
				calendar = Calendar.getInstance( locale );
			} else {
				calendar = Calendar.getInstance( timeZone, locale );
			}
			calendar.setTime( new Date( value ) );
			calendar.setLenient( false );
			return type.cast( calendar );
		}

		throw new ConversionException();
	}

	private < T > T toDate( Class< T > type, String value ) {
		// java.sql.Date
		if ( type.equals( java.sql.Date.class ) ) {
			try {
				return type.cast( java.sql.Date.valueOf( value ) );
			} catch ( IllegalArgumentException e ) {
				throw new ConversionException(
						"String must be in JDBC format [yyyy-MM-dd] to create a java.sql.Date" );
			}
		}

		// java.sql.Time
		if ( type.equals( java.sql.Time.class ) ) {
			try {
				return type.cast( java.sql.Time.valueOf( value ) );
			} catch ( IllegalArgumentException e ) {
				throw new ConversionException(
						"String must be in JDBC format [HH:mm:ss] to create a java.sql.Time" );
			}
		}

		// java.sql.Timestamp
		if ( type.equals( java.sql.Timestamp.class ) ) {
			try {
				return type.cast( java.sql.Timestamp.valueOf( value ) );
			} catch ( IllegalArgumentException e ) {
				throw new ConversionException(
						"String must be in JDBC format [yyyy-MM-dd HH:mm:ss.fffffffff] "
								+ "to create a java.sql.Timestamp" );
			}
		}

		throw new ConversionException();
	}

	protected DateFormat getFormat( Locale locale, TimeZone timeZone ) {
		DateFormat format = null;
		if ( locale == null ) {
			format = DateFormat.getDateInstance( DateFormat.SHORT );
		} else {
			format = DateFormat.getDateInstance( DateFormat.SHORT, locale );
		}
		if ( timeZone != null ) {
			format.setTimeZone( timeZone );
		}
		return format;
	}

	private DateFormat getFormat( String pattern ) {
		DateFormat format = new SimpleDateFormat( pattern );
		if ( timeZone != null ) {
			format.setTimeZone( timeZone );
		}
		return format;
	}

	private Calendar parse( Class< ? > sourceType, Class< ? > targetType,
			String value ) throws Exception {
		Exception firstEx = null;
		for ( int i = 0; i < patterns.length; i++ ) {
			try {
				DateFormat format = getFormat( patterns[ i ] );
				Calendar calendar = parse( sourceType, targetType, value,
						format );
				return calendar;
			} catch ( Exception ex ) {
				if ( firstEx == null ) {
					firstEx = ex;
				}
			}
		}
		if ( patterns.length > 1 ) {
			throw new ConversionException();
		} else {
			throw firstEx;
		}
	}

	/**
	 * Parse a String into a <code>Calendar</code> object using the specified
	 * <code>DateFormat</code>.
	 * 
	 * @param sourceType
	 *            The type of the value being converted
	 * @param targetType
	 *            The type to convert the value to
	 * @param value
	 *            The String date value.
	 * @param format
	 *            The DateFormat to parse the String value.
	 * 
	 * @return The converted Calendar object.
	 * @throws ConversionException
	 *             if the String cannot be converted.
	 */
	private Calendar parse( Class< ? > sourceType, Class< ? > targetType,
			String value, DateFormat format ) {
		format.setLenient( false );
		ParsePosition pos = new ParsePosition( 0 );
		Date parsedDate = format.parse( value, pos ); // ignore the result (use
														// the Calendar)
		if ( pos.getErrorIndex() >= 0 || pos.getIndex() != value.length()
				|| parsedDate == null ) {
			throw new ConversionException();
		}
		Calendar calendar = format.getCalendar();
		return calendar;
	}

	@Override
	public Class< ? > defaultType() {
		return Date.class;
	}

	@Override
	protected Object convertToType( Object value ) throws Exception {
		Class< ? > sourceType = value.getClass();
		if ( value instanceof java.sql.Timestamp ) {
			java.sql.Timestamp timestamp = ( java.sql.Timestamp ) value;
			long timeInMillis = ( ( timestamp.getTime() / 1000 ) * 1000 );
			timeInMillis += timestamp.getNanos() / 1000000;
			return toDate( defaultType(), timeInMillis );
		}

		if ( value instanceof Date ) {
			Date date = ( Date ) value;
			return toDate( defaultType(), date.getTime() );
		}

		// Handle Calendar
		if ( value instanceof Calendar ) {
			Calendar calendar = ( Calendar ) value;
			return toDate( defaultType(), calendar.getTime().getTime() );
		}

		// Handle Long
		if ( value instanceof Long ) {
			Long longObj = ( Long ) value;
			return toDate( defaultType(), longObj.longValue() );
		}

		String stringValue = value.toString().trim();
		if ( stringValue.length() == 0 ) {
			return new Date();
		}

		if ( useLocaleFormat ) {
			Calendar calendar = null;
			if ( patterns != null && patterns.length > 0 ) {
				calendar = parse( sourceType, defaultType(), stringValue );
			} else {
				DateFormat format = getFormat( locale, timeZone );
				calendar = parse( sourceType, defaultType(), stringValue,
						format );
			}
			if ( Calendar.class.isAssignableFrom( defaultType() ) ) {
				return defaultType().cast( calendar );
			} else {
				return toDate( defaultType(), calendar.getTime().getTime() );
			}
		}
		return toDate( defaultType(), stringValue );
	}
}
