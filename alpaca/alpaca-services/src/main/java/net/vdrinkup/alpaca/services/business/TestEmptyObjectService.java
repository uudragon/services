/**
 * 
 */
package net.vdrinkup.alpaca.services.business;

import net.vdrinkup.alpaca.data.DataFactory;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.service.AbstractBusinessService;

/**
 * @author pluto.bing.liu
 *
 */
public class TestEmptyObjectService extends AbstractBusinessService {

	@SuppressWarnings( "unchecked" )
	@Override
	public < T, R > R invoke( T t ) throws Exception {
		final DataObject sdo = DataFactory.INSTANCE.create();
		return ( R ) sdo;
	}

}
