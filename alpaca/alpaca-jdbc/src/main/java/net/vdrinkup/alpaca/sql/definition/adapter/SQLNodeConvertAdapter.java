/*******************************************************************************
 * Copyright (c) 2014 Corporation and others.
 * 
 * Contributors:
 *     XXX Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.sql.definition.adapter;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

import net.vdrinkup.alpaca.configuration.model.ProcessorDefinition;
import net.vdrinkup.alpaca.configuration.model.adapter.NodeConvertAdapter;
import net.vdrinkup.alpaca.configuration.model.adapter.TypeNotSupportException;
import net.vdrinkup.alpaca.sql.definition.SQLStringDefinition;


/**
 *
 * <p></p>
 * @author pluto.bing.liu
 * Date 2014-1-27
 */
public class SQLNodeConvertAdapter extends NodeConvertAdapter {
	
	private static CollapsedStringAdapter adapter = new CollapsedStringAdapter();

	public SQLNodeConvertAdapter() {
	}

	public Object unmarshal( Object object ) throws Exception {
		ProcessorDefinition def = null;
		if ( object instanceof ProcessorDefinition ) {
			def = ( ProcessorDefinition ) object;
		} else if ( object instanceof String ) {
			String sql = adapter.unmarshal( ( String ) object );
			def = new SQLStringDefinition( sql );
		} else {
			throw new TypeNotSupportException( object.getClass().getName()
					+ " can not be supported." );
		}
		return def;
	}

	public Object marshal( Object object ) throws Exception {
		Object ret = null;
		if ( object instanceof SQLStringDefinition ) {
			SQLStringDefinition def = SQLStringDefinition.class.cast( object );
			ret = def.getSql();
		} else if ( object instanceof ProcessorDefinition ) {
			ret = object;
		} else {
			throw new TypeNotSupportException( object.getClass().getName()
					+ " can not be supported." );
		}
		return ret;
	}
	
}
