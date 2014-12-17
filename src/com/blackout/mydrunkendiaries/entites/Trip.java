/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Entites - Trip.                            *   
 *                                                      *   
 * Usage:                                               *   
 *                                                      *   
 ********************************************************/

package com.blackout.mydrunkendiaries.entites;

import org.joda.time.DateTime;

/**
 * @author spo2
 *
 */
public class Trip 
{
	private long id;
	private Place place;
	private DateTime createdAt;
	private DateTime endedAt;
	private Integer placeScore;
	private Integer depravity;
	
	/**
	 * @return the id
	 */
	public long getId() 
	{
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(long id) 
	{
		this.id = id;
	}
	
	/**
	 * @return the place
	 */
	public Place getPlace() 
	{
		return place;
	}
	
	/**
	 * @param place the place to set
	 */
	public void setPlace(Place place) 
	{
		this.place = place;
	}
	
	/**
	 * @return the createdAt
	 */
	public DateTime getCreatedAt() 
	{
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(DateTime createdAt) 
	{
		this.createdAt = createdAt;
	}

	/**
	 * @return the endedAt
	 */
	public DateTime getEndedAt() 
	{
		return endedAt;
	}

	/**
	 * @param endedAt the endedAt to set
	 */
	public void setEndedAt(DateTime endedAt) 
	{
		this.endedAt = endedAt;
	}

	/**
	 * @return the placeScore
	 */
	public Integer getPlaceScore() 
	{
		return placeScore;
	}
	
	/**
	 * @param placeScore the placeScore to set
	 */
	public void setPlaceScore(Integer placeScore) 
	{
		this.placeScore = placeScore;
	}
	
	/**
	 * @return the depravity
	 */
	public Integer getDepravity() 
	{
		return depravity;
	}
	
	/**
	 * @param depravity the depravity to set
	 */
	public void setDepravity(Integer depravity) 
	{
		this.depravity = depravity;
	}
	
	
}
