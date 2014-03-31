package net.vdrinkup.alpaca.services;

import java.util.Date;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.context.DataContextFactory;
import net.vdrinkup.alpaca.data.DataFactory;
import net.vdrinkup.alpaca.data.DataObject;
import net.vdrinkup.alpaca.service.InvokeException;
import net.vdrinkup.alpaca.services.business.PreparedOrdersService;

import org.junit.Before;
import org.junit.Test;

public class PreparedOrderServiceTest {
	@Before
	public void setup() {
		System.setProperty( Env.Keys.INSTALL_PATH, this.getClass().getClassLoader().getResource( "" ).getPath() );
	}
	
	@Test
	public void testPreparedOrder() throws InvokeException {
		DataContext context = DataContextFactory.INSTANCE.create();
		DataObject sdo = DataFactory.INSTANCE.create();
		sdo.set( "productCode", "123455");
		sdo.set( "effective", new Date() );
		sdo.set( "validity", 3 );
		context.setIn( sdo );
		PreparedOrdersService service = new PreparedOrdersService();
		service.invoke( context );
		System.out.println( context.getIn() );
	}

}
