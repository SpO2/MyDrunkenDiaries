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
	private long place;
	private DateTime startDate;
	private DateTime endDate;
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
	public long getPlace() 
	{
		return place;
	}
	
	/**
	 * @param place the place to set
	 */
	public void setPlace(long place) 
	{
		this.place = place;
	}
	
	/**
	 * @return the startDate
	 */
	public DateTime getStartDate() 
	{
		return startDate;
	}
	
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(DateTime startDate) 
	{
		this.startDate = startDate;
	}
	
	/**
	 * @return the endDate
	 */
	public DateTime getEndDate() 
	{
		return endDate;
	}
	
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(DateTime endDate) 
	{
		this.endDate = endDate;
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
