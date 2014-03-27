/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 缓存Key,由于处理一对多映射时，生成唯一标识
 * <p>
 * 该类借鉴自MyBatis，或者说抄袭后做了点修改更诚实些。后续会进行改进
 * </p>
 * @author pluto.bing.liu Date 2014-3-27
 */
public class CacheKey implements Cloneable, Serializable {

	private static final long serialVersionUID = 486031990211774761L;
	
	public static final CacheKey NULL_CACHE_KEY = new NullCacheKey();

	private static final int DEFAULT_MULTIPLYER = 37;
	
	private static final int DEFAULT_HASHCODE = 17;

	private int multiplier;
	private int hashcode;
	private long checksum;
	private int count;
	private List< Object > updateList;

	public CacheKey() {
		this.hashcode = DEFAULT_HASHCODE;
		this.multiplier = DEFAULT_MULTIPLYER;
		this.count = 0;
		this.updateList = new LinkedList< Object >();
	}

	public CacheKey( Object[] objects ) {
		this();
		updateAll( objects );
	}

	public int getUpdateCount() {
		return updateList.size();
	}

	public void update( Object object ) {
		int baseHashCode = object == null ? 1 : object.hashCode();

		count++;
		checksum += baseHashCode;
		baseHashCode *= count;

		hashcode = multiplier * hashcode + baseHashCode;

		updateList.add( object );
	}

	public void updateAll( Object[] objects ) {
		for ( Object o : objects ) {
			update( o );
		}
	}

	public boolean equals( Object object ) {
		if ( this == object )
			return true;
		if ( !( object instanceof CacheKey ) )
			return false;

		final CacheKey cacheKey = ( CacheKey ) object;

		if ( hashcode != cacheKey.hashcode )
			return false;
		if ( checksum != cacheKey.checksum )
			return false;
		if ( count != cacheKey.count )
			return false;

		for ( int i = 0; i < updateList.size(); i++ ) {
			Object thisObject = updateList.get( i );
			Object thatObject = cacheKey.updateList.get( i );
			if ( thisObject == null ) {
				if ( thatObject != null )
					return false;
			} else {
				if ( !thisObject.equals( thatObject ) )
					return false;
			}
		}
		return true;
	}

	public int hashCode() {
		return hashcode;
	}

	public String toString() {
		StringBuilder returnValue = new StringBuilder().append( hashcode )
				.append( ':' ).append( checksum );
		for ( int i = 0; i < updateList.size(); i++ ) {
			returnValue.append( ':' ).append( updateList.get( i ) );
		}

		return returnValue.toString();
	}

	@Override
	public CacheKey clone() throws CloneNotSupportedException {
		CacheKey clonedCacheKey = ( CacheKey ) super.clone();
		clonedCacheKey.updateList = new ArrayList< Object >( updateList );
		return clonedCacheKey;
	}

}

class NullCacheKey extends CacheKey {

	private static final long serialVersionUID = 9080303622193081597L;

	public NullCacheKey() {
		super();
	}

	@Override
	public void update( Object object ) {
		throw new CacheException(
				"Not allowed to update a NullCacheKey instance." );
	}

	@Override
	public void updateAll( Object[] objects ) {
		throw new CacheException(
				"Not allowed to update a NullCacheKey instance." );
	}
}