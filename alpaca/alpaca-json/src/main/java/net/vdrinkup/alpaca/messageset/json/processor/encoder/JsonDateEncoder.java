/**
 * 
 */
package net.vdrinkup.alpaca.messageset.json.processor.encoder;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.messageset.MessageNode;
import net.vdrinkup.alpaca.messageset.json.JsonConstants;
import net.vdrinkup.alpaca.messageset.json.processor.JsonEncoder;

/**
 * @author pluto.bing.liu
 *
 */
public class JsonDateEncoder implements JsonEncoder {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat();
	
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd";

	@Override
	public void encode( DataContext context, MessageNode definition )
			throws Exception {
		final DataObject sdo = context.getIn();
		Date value = sdo.getDate( definition.getBinding() );
		String format = definition.getFormat();
		if ( format == null || "".equals( format ) ) {
			format = DEFAULT_PATTERN;
		}
		dateFormat.applyPattern( format );
		String dateStr = dateFormat.format( value );
 		final ByteArrayOutputStream baos = context.getOut();
 		baos.write( JsonConstants.D_QUOTATION_MARK );
 		baos.write( dateStr.getBytes() );
		baos.write( JsonConstants.D_QUOTATION_MARK );
	}

}
