/**
 * Copyright 2000-2012 DigitalChina. All Rights Reserved.
 */
package net.vdrinkup.alpaca.commons.token.impl;

import net.vdrinkup.alpaca.commons.token.TokenHandler;

/**
 * 单令牌处理器
 * <p>
 * 该令牌处理器，仅将解析出的令牌转换成固定值。
 * </p>
 * @author bing.liu
 */
public class SingleTokenHandler implements TokenHandler {

	private String content;
	
	public SingleTokenHandler( String content ) {
		this.content = content;
	}

	/* (non-Javadoc)
	 * @see com.dc.appengine.node.tools.TokenHandler#handleToken(java.lang.String)
	 */
	public String handleToken( String token ) throws Exception {
		return content;
	}

}
