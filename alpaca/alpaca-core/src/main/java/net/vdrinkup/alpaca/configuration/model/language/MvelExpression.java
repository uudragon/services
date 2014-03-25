/*******************************************************************************
 * Copyright (c) 2012 Corporation and others.
 * 
 * Contributors:
 *     Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.configuration.model.language;

import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.context.DataContext;
import net.vdrinkup.alpaca.data.DataObject;

import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p></p>
 * @author bing.liu
 * Date 2012-2-16
 */
@XmlRootElement( name = "mvel" )
public class MvelExpression extends ExpressionDefinition {
	
	private static Logger LOG = LoggerFactory.getLogger( MvelExpression.class );

	public < T > T evaluate( DataObject dataObject, DataContext context, Class< T > type ) {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.neptune.mp.Predicate#matches(java.lang.Object)
	 */
	public boolean matches( Object ctx ) {
		LOG.debug("getExpression() = {}", getExpression());
		LOG.debug("eval2 = {}", MVEL.eval( getExpression(), ctx ));
		return ( Boolean ) MVEL.eval( getExpression(), ctx );
	}

}
