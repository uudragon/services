package net.vdrinkup.alpaca.commons.token.impl;

import net.vdrinkup.alpaca.commons.token.TokenHandler;
import net.vdrinkup.alpaca.commons.token.TokenParser;

/**
 * 默认的令牌处解析器实现
 * <p></p>
 * @author pluto.bing.liu
 * Date 2013-12-2
 */
public class DefaultTokenParser implements TokenParser {
	private String startChar;
	private String endChar;

	public DefaultTokenParser( String startChar, String endChar ) {
		this.startChar = startChar;
		this.endChar = endChar;
	}

	public String parse( String text, TokenHandler handler ) throws Exception {
		StringBuffer buffer = new StringBuffer();
		if ( text != null ) {
			int start;
			String result = text;
			do {
				start = result.indexOf( this.startChar );
				int end = result.indexOf( this.endChar );
				if ( ( end > start ) && ( start != -1 ) ) {
					buffer.append( result.substring( 0, start ) );
					String content = result.substring(
							start + this.startChar.length(), end );
					buffer.append( handler.handleToken( content ) );
					result = result.substring( end + this.endChar.length() );
				} else {
					if ( end <= -1 )
						break;
					buffer.append( result.substring( 0, end ) ).append(
							this.endChar );
					result = result.substring( end + this.endChar.length() );
				}
			} while ( start > -1 );
			buffer.append( result );
		}
		return buffer.toString();
	}
	
}