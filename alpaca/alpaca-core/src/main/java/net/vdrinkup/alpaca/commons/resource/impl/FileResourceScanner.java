package net.vdrinkup.alpaca.commons.resource.impl;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import net.vdrinkup.alpaca.commons.resource.ResourceFilter;
import net.vdrinkup.alpaca.commons.resource.ResourceScanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileResourceScanner implements ResourceScanner {

	private static Logger LOG = LoggerFactory.getLogger( FileResourceScanner.class );

	public < R > void scan( R resource, ResourceFilter filter ) {
		if ( ! ( resource instanceof File ) ) {
			throw new IllegalArgumentException( "The resource must be an instance of java.io.File." );
		}
		final File root = ( File ) resource;
		try {
			frt( root, filter );
		} catch ( Exception e ) {
			LOG.error( e.getMessage(), e );
		}
	}
	
	/**
	 * 文件先根遍历算法
	 * @param root
	 * @param filter
	 * @throws Exception
	 */
	private void frt( File root, ResourceFilter filter ) throws Exception {
		List< StackItem > stack = new LinkedList< StackItem >();
		if ( root.isFile() ) {
			filter.doFilter( root );
			return ;
		} else {
			stack.add( 0, new StackItem( root.listFiles(), -1 ) );
			StackItem item = null;
			int i = 0;
			File[] files = null;
			while ( ! stack.isEmpty() ) {
				item = stack.remove( 0 );
				files = item.getFiles();
				i = item.getIndex();
				i++;
				
				for ( ; files != null && i < files.length; i++ ) {
					if ( files[ i ].isDirectory() ) {
						stack.add( 0, new StackItem( files, i ) );
						files = files[ i ].listFiles();
						i = -1;
						continue ;
					}
					filter.doFilter( files[ i ] );
				}
			}
		}
	}
	
	class StackItem {
		private File[] files;
		
		private int index;
		
		StackItem( File[] files, int index ) {
			this.files = files;
			this.index = index;
		}

		public File[] getFiles() {
			return files;
		}

		public int getIndex() {
			return index;
		}
		
	}

}
