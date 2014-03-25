/**
 * 
 */
package net.vdrinkup.alpaca.commons.typeconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bing
 */
public abstract class AbstractConverter implements Converter {
	
	private static Logger LOG = LoggerFactory.getLogger( AbstractConverter.class );

	public Object convert( Object object ) {
		Object result = null;
		try {
			result = convertToType( object );
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
		}
		return result;
	}

	protected abstract Object convertToType( Object value ) throws Exception;

}
