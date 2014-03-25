/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.xml.test;

import net.vdrinkup.alpaca.Env;
import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.context.DataContextFactory;
import net.vdrinkup.alpaca.messageset.MessageDirection;
import net.vdrinkup.alpaca.messageset.MessageSetConstants;
import net.vdrinkup.alpaca.messageset.definition.MessageDefinition;
import net.vdrinkup.alpaca.messageset.xml.XMLMsgConfigProcessor;

import org.junit.Before;
import org.junit.Test;


/**
 *
 * <p></p>
 * @author liubing
 * Date Jan 16, 2014
 */
public class XmlProcessTest {
	@Before
	public void setup() {
		System.setProperty( Env.Keys.INSTALL_PATH, this.getClass().getClassLoader().getResource( "" ).getPath() );
	}
	
	@Test
	public void test() throws Exception {
		XMLMsgConfigProcessor configProcessor = new XMLMsgConfigProcessor();
		MessageDefinition definition = configProcessor.read( this.getClass().getClassLoader().getResourceAsStream( "test.xml" ) );
		System.out.println( definition );
		String message = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><sow:recommandRequest xmlns:sow=\"http://sowing.wms.jd.com\">" +
				"<containerNo><![CDATA[aaaaaaa]]></containerNo><goodsNo>11111111</goodsNo><deskNo>111111111111</deskNo>" +
				"<oneByOne>true</oneByOne><details><shipmentDetail><id>aaaaaaaaaa</id><name>bbbbbbbbbb</name></shipmentDetail>" +
				"</details></sow:recommandRequest>";
		DataContext context = DataContextFactory.INSTANCE.create();
		context.setProperty( MessageSetConstants.MESSAGE_DIRECTION, MessageDirection.IN );
		context.setIn( message.getBytes( "UTF-8" ) );
		definition.getIn().createProcessor().process( context );
		System.out.println( context.getIn().toString() );
		context.setProperty( MessageSetConstants.MESSAGE_DIRECTION, MessageDirection.OUT );
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
		definition.getOut().createProcessor().process( context );
		System.out.println( new String( ( byte[] ) context.getOut(), "UTF-8" ) );
	}

}
