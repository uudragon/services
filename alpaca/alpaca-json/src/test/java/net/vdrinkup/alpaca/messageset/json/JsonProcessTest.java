/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset.json;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.context.DataContextFactory;
import net.vdrinkup.alpaca.messageset.MessageDirection;
import net.vdrinkup.alpaca.messageset.MessageSetConstants;
import net.vdrinkup.alpaca.messageset.definition.MessageDefinition;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * <p></p>
 * @author liubing
 * Date Jan 16, 2014
 */
public class JsonProcessTest {
	@Before
	public void setup() {
		System.setProperty( Env.Keys.INSTALL_PATH, this.getClass().getClassLoader().getResource( "" ).getPath() );
	}
	
	@Test
	public void test() throws Exception {
		JsonConfigProcessor configProcessor = new JsonConfigProcessor();
		MessageDefinition definition = configProcessor.read( this.getClass().getClassLoader().getResourceAsStream( "test.xml" ) );
		System.out.println( definition );
//		String message = "[[{\"containerNo\":\"CT-001\",\"bizNode\":\"1\",\"containerMark\":2,\"details\": [{\"containerNo\":\"CT-001\",\"destZone\":\"DZ- 001\",\"lotNo\":\"1\",\"bizType\":1,\"bizCode\":\"1\",\"qty\":100.00,\"taskNo\":\"20140226001\",\"goodsNo\":\"1001001\",\"stockLevel\":\"1\",\"packingUnit\":null,\"goodsName\":\" 马应龙痔疮 膏\"}],\"distributeNo\":\"10\",\"orgNo\":\"10\",\"taskType\":\"1\",\"taskNo\":\"20140226001\",\"warehouseNo\":null}]]";
//		String message = "{\"orgNo\":\"6\",\"distributeNo\":\"6\",\"warehouseNo\":\"98\",\"mqDestination\":\"WMS3_6_98_ob_outcheck\"," +
//				"\"msgId\":null,\"processType\":\"out\",\"fromMsgServiceNo\":null,\"fromNodeNo\":\"picking\"," +
//				"\"toMsgServiceNo\":null,\"toNodeNo\":\"outcheck\",\"businessNo\":\"OBTA669814022500000002-2\"," +
//				"\"dataType\":\"13\",\"businessData\":{\"id\":77,\"batchNo\":\"OBTA669814022500000002\",\"batchType\":108," +
//				"\"batchLevel\":0,\"firstFetchTime\":\"2014-02-25 16:50:27\",\"outboundQty\":1,\"orgNo\":\"6\",\"distributeNo\":\"6\"," +
//				"\"warehouseNo\":\"98\",\"mergeFlag\":0,\"containerNo\":\"12A13848571874PT\",\"checkFlag\":50702005,\"reckeckTableNo\":\"FHT004\"," +
//				"\"platformNo\":null,\"taskPageQty\":1,\"waveNo\":null,\"assignCarNo\":null,\"sendcarTime\":null,\"carrierNo\":\"SJI00000092\"," +
//				"\"carrierName\":null,\"driver\":null,\"carNo\":null,\"carLicence\":null,\"optStatus\":2,\"createTime\":\"2014-02-25 16:45:28\"," +
//				"\"updateTime\":\"2014-02-25 17:10:41\",\"createUser\":\"picking\",\"updateUser\":\"songxiaodi\"," +
//				"\"yn\":0,\"ts\":\"2014-02-25 17:10:41\",\"detailList\":[],\"pickingTaskMainList\":null," +
//				"\"pickingTaskDetailList\":null,\"rebinwallList\":null},\"processResult\":true}"; 
		String message = "{\"orgNo\":\"6\",\"distributeNo\":\"6\",\"warehouseNo\":\"98\",\"mqDestination\":\"WMS3_6_98_ob_outcheck\"," +
		"\"msgId\":null,\"processType\":\"out\",\"fromMsgServiceNo\":null,\"fromNodeNo\":\"picking\"," +
		"\"toMsgServiceNo\":null,\"toNodeNo\":\"outcheck\",\"businessNo\":\"OBTA669814022500000002-2\"," +
		"\"dataType\":\"13\",\"businessData\":\"{\"containerNo\":\"CT-001\",\"containerMark\":2,\"taskNo\":\"1001010101\",\"taskType\":1,\"bizNode\":1,\"details\":[{\"goodsNo\":\"100001\",\"goodsName\":\"马应龙痔疮膏\",\"lotNo\":\"1\",\"packingUnit\":\"件\",\"stockLevel\":1,\"containerNo\":\"CT-001\",\"taskNo\":\"10101010\",\"bizCode\":\"11111\",\"bizType\":1,\"qty\":10.00,\"destZone\":\"D-001\"}]}\",\"processResult\":true}"; 
		DataContext context = DataContextFactory.INSTANCE.create();
		context.setProperty( MessageSetConstants.MESSAGE_DIRECTION, MessageDirection.IN );
		context.setIn( message.getBytes( "UTF-8" ) );
		definition.getIn().createProcessor().process( context );
		System.out.println( context.getOut().toString() );
		context.setProperty( MessageSetConstants.MESSAGE_DIRECTION, MessageDirection.OUT );
		context.setIn( context.getOut() );
		definition.getOut().createProcessor().process( context );
		System.out.println( new String( ( byte[] ) context.getOut() ) );
//		Map< String, Object > sIn = context.getIn();
//		Object details = sIn.get( "details" );
//		context.setProperty( MessageSetConstants.MESSAGE_DIRECTION, MessageDirection.OUT );
//		Map< String, Object > in = new HashMap< String, Object >();
//		in.put( "cellAddr", "aaaaaaaaaaaa" );
//		in.put( "qty", 1 );
//		in.put( "result", 1 );
//		in.put( "message", "fuck" );
//		in.put( "details", details );
//		context.setIn( in );
//		definition.getOut().createProcessor().process( context );
//		System.out.println( new String( ( byte[] ) context.getOut(), "UTF-8" ) );
	}

}
