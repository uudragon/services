package net.vdrinkup.alpaca.commons.token;

/**
 * 字符令牌处理器
 * <p>
 * 用于处理字符令牌。<br>
 * 这里的字符令牌指的是如下格式的字符串:起始符+令牌名称+结束符，例如${XXXX}。
 * “起始符”与“结束符”要求不能相同。
 * </p>
 * @author pluto.bing.liu
 * Date 2013-12-2
 */
public abstract interface TokenParser {
	
	public abstract String parse( String paramString, TokenHandler handler ) throws Exception;
	
}