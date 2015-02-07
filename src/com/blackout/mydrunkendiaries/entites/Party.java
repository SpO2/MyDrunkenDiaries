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
	private String createdAt;
	private String endedAt;
	
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

	/**
	 * @return the createdAt
	 */
	public String getCreatedAt() 
	{
		return createdAt;
	}
	
	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(String createdAt) 
	{
		this.createdAt = createdAt;
	}

	/**
	 * @return the endedAt
	 */
	public String getEndedAt() 
	{
		return endedAt;
	}

	/**
	 * @param endedAt the endedAt to set
	 */
	public void setEndedAt(String endedAt) 
	{
		this.endedAt = endedAt;
	}
}
