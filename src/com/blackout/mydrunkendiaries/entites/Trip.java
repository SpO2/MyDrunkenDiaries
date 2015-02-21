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


/**
 * Class of the Trip entity.
 * @author romain
 *
 */
public class Trip 
{
	/**
	 * Id of trip.
	 */
	private long id;
	/**
	 * Mapped place of the trip.
	 */
	private Place place;
	/**
	 * Mapped party of the place.
	 */
	private Party party;
	/**
	 * Date of creation of the trip.
	 */
	private String createdAt;
	/**
	 * End date of the trip.
	 */
	private String endedAt;
	/**
	 * Score for the place.
	 */
	private Integer placeScore;
	/**
	 * Depravity of the trip.
	 */
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
	 * @return the party
	 */
	public Party getParty() 
	{
		return party;
	}

	/**
	 * @param party the party to set
	 */
	public void setParty(Party party) 
	{
		this.party = party;
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
