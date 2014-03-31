package net.vdrinkup.alpaca.commons.resource;


public interface ResourceFilter {
	/**
	 * 过滤方法
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public < T, R > R doFilter( T t ) throws Exception;

}
