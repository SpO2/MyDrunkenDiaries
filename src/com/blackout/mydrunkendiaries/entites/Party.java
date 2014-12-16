/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Entites - Party.                           *   
 *                                                      *   
 * Usage:                                               *   
 *                                                      *   
 ********************************************************/

package com.blackout.mydrunkendiaries.entites;

/**
 * @author spo2
 *
 */
public class Party 
{
	private long id;
	private String name;
	
	/**
	 * @return the id
	 */
	public long getId() 
	{
		return id;
	}
	
	/**
	 * @param id : the id to set
	 */
	public void setId(long id) 
	{
		this.id = id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * @param name : the name to set
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
}
