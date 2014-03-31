package net.vdrinkup.alpaca.services.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vdrinkup.alpaca.data.DataFactory;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.service.AbstractBusinessService;

/**
 * 订单预处理服务类
 * @author pluto.bing.liu
 *
 */
public class PreparedOrdersService extends AbstractBusinessService {
	
	private static Logger LOG = LoggerFactory.getLogger( PreparedOrdersService.class );

	@SuppressWarnings("unchecked")
	@Override
	public <T, R> R invoke(T t) throws Exception {
		final DataObject sdo = (DataObject) t;
		final Date effective = sdo.getDate( "effective" );
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Prepared Order --> effective [{}]", effective );
		}
		final int validity = sdo.getInt( "validity" );
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( effective );
		final List< DataObject > details = new ArrayList< DataObject >( validity );
		DataObject detail;
		for ( int i = 0; i < validity; i++ ) {
			detail = DataFactory.INSTANCE.create();
			detail.setString( "productCode", sdo.getString( "productCode" ) );
			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "Prepared Order details --> productCode [{}]", detail.getString( "productCode" ) );
			}
			calendar.add( Calendar.MONTH, 1 );
			if ( LOG.isDebugEnabled() ) {
				LOG.debug( "Prepared Order details --> effective [{}]", calendar.toString() );
			}
			detail.setDate( "effective", calendar.getTime() );
			detail.setInt( "qty", 1 );
			details.add( detail );
		}
		sdo.setList( "details", details );
		final Date deadline = calendar.getTime();
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Prepared Order --> deadline [{}]", deadline );
		}
		sdo.setDate( "deadline", deadline );
		return (R) sdo;
	}
}
