/*******************************************************************************
 * Copyright (c) 2014 JD Corporation and others.
 * 
 * Contributors:
 *     JD Corporation 
 *******************************************************************************/
package net.vdrinkup.alpaca.messageset;

import java.util.List;

/**
 *
 * <p></p>
 * @author liubing
 * Date Mar 10, 2014
 */
public interface MessageNode {
	
	public String getName();
	
	public String getBinding();
	
	public String getType();
	
	public int getLength();
	
	public String getDefaultValue();
	
	public boolean isRequired();
	
	public List< MessageNode > getElements();
	
	public String getPath();
	
	public MessageNode findSub( String key );
	
	public boolean isLeaf();

}
