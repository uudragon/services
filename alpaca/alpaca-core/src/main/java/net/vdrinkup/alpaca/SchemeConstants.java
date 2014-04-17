/*******************************************************************************
 * Copyright (c) 2013 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca;

/**
 * Scheme常量类
 * <p></p>
 * @author liubing
 * Date Nov 28, 2013
 */
public class SchemeConstants {
	
	private SchemeConstants() {
	}
	
	public class Prefix {
		
		public static final String PROTOCOL_PREFIX = PROTOCOL_SCHEME + SCHEME_SEPARATOR;
		
		public static final String FLOW_PREFIX = FLOW_SCHEME + SCHEME_SEPARATOR;
		
		public static final String DMS_PREFIX = DMS_SCHEME + SCHEME_SEPARATOR;
		
		public static final String SERVICE_PREFIX = SERVICE_SCHEME + SCHEME_SEPARATOR;
	 
		private Prefix(){};
		
	}
	
	public static final String PROTOCOL_SCHEME = "protocol";
	
	public static final String FLOW_SCHEME = "flow";
	
	public static final String DMS_SCHEME = "dms";
	
	public static final String SERVICE_SCHEME = "service";
	
	public static final String LOCATION_SEPARATOR = "/";

	public static final String SCHEME_SEPARATOR = ":";
	
}
