/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.jdbc.definition;

import javax.xml.bind.annotation.XmlRootElement;

import net.vdrinkup.alpaca.dms.DataSourceDefinition;


/**
 *
 * <p></p>
 * @author liubing
 * Date Feb 12, 2014
 */
@XmlRootElement( name = "dataSource.jdbc" )
public class JdbcDataSourceDefinition extends DataSourceDefinition {
}
