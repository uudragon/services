package net.vdrinkup.alpaca.commons.filter;

public interface ResourceFilter {
	
	public < T, R > R doFilter( T t, ResourceFilterChain chain ) throws Exception;

}
