/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Entites - Media.                           *   
 *                                                      *   
 * Usage:                                               *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.entites;

/**
 * @author spo2
 *
 */
public class TripMedia 
{
	private long id;
	private Trip trip;
	private String path;
	
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
	 * @return the trip
	 */
	public Trip getTrip() 
	{
		return trip;
	}
	
	/**
	 * @param trip the trip to set
	 */
	public void setTrip(Trip trip) 
	{
		this.trip = trip;
	}
	
	/**
	 * @return the path
	 */
	public String getPath() 
	{
		return path;
	}
	
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) 
	{
		this.path = path;
	}
	
	
}
