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
 * Class for the TripMedia entity.
 * @author romain
 *
 */
public class TripMedia 
{
	/**
	 * Id of the tripmedia.
	 */
	private long id;
	/**
	 * Mapped trip of the tripMedia.
	 */
	private Trip trip;
	/**
	 * Name of the TripMedia.
	 */
	private String name;
	/**
	 * Path for the TripMedia album.
	 */
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
	 * 
	 * @return the name of the media
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) 
	{
		this.name = name;
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
