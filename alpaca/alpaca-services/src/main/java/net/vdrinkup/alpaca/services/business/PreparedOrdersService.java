package net.vdrinkup.alpaca.services.business;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.service.AbstractBusinessService;

/**
 * 订单预处理服务类
 * @author pluto.bing.liu
 *
 */
public class PreparedOrdersService extends AbstractBusinessService {
	
	private static Logger LOG = LoggerFactory.getLogger( PreparedOrdersService.class );

	@Override
	public <T, R> R invoke(T t) throws Exception {
		final DataObject sdo = (DataObject) t;
		final Date validDate = sdo.getDate( "validDate" );
		return null;
	}

}
