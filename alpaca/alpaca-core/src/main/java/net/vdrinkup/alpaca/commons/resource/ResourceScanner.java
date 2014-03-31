package net.vdrinkup.alpaca.commons.resource;


/**
 * 资源搜索器接口
 * @author pluto.bing.liu
 *
 */
public interface ResourceScanner {
	/**
	 * 搜索方法
	 * @param resource
	 * @param filter
	 */
	public < R > void scan( R resource, ResourceFilter filter );

}
