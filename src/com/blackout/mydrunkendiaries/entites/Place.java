/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Entites - Place.                           *   
 *                                                      *   
 * Usage:                                               *   
 *                                                      *   
 ********************************************************/

package com.blackout.mydrunkendiaries.entites;

/**
 * Class of the place entity.
 * @author romain
 *
 */
public class Place 
{
	/**
	 * Id of the place.
	 */
	private long id;
	/**
	 * Name of the place.
	 */
	private String name;
	/**
	 * Longitude of the place.
	 */
	private double longitude;
	/**
	 * Latitude of the place.
	 */
	private double latitude;
	
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
	 * @return the name
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() 
	{
		return longitude;
	}
	
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) 
	{
		this.longitude = longitude;
	}
	
	/**
	 * @return the latitude
	 */
	public double getLatitude() 
	{
		return latitude;
	}
	
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) 
	{
		this.latitude = latitude;
	}
	
}
