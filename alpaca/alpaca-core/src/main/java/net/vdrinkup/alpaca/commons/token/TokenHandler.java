package net.vdrinkup.alpaca.commons.token;

/**
 * 字符令牌处理
 * <p>
 * 用于字符令牌解析后的处理。
 * 通过调用{@link TokenParser}解析出令牌后，如果需要将令牌做后续处理，则实现该接口。
 * </p>
 * @author pluto.bing.liu
 * Date 2013-12-2
 */
public abstract interface TokenHandler {
	
	public abstract String handleToken( String paramString ) throws Exception;
	
}