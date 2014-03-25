package net.vdrinkup.alpaca.commons.token.impl;

import java.util.Map;
import java.util.NoSuchElementException;

import net.vdrinkup.alpaca.commons.token.TokenHandler;


/**
 * 可映射的令牌处理器
 * <p>
 * 该处理器通过定义一组令牌与对应值的映射关系，将解析出的令牌进行转换
 * </p>
 * @author pluto.bing.liu
 * Date 2013-12-2
 */
public class MappedTokenHandler implements TokenHandler {
	
	private Map< String, ? > contents;
	
	public MappedTokenHandler( Map< String, ? > contents ) {
		this.contents = contents;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dc.appengine.node.tools.TokenHandler#handleToken(java.lang.String)
	 */
	public String handleToken( String token ) throws Exception {
		String content = null;
		if ( contents != null && contents.containsKey( token ) ) {
			content = contents.get( token ).toString();
		} else  {
			throw new NoSuchElementException( "No such element named [" + token + "]" );
		}
		return content;
	}
	
}