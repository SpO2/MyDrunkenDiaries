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
 * @author spo2
 *
 */
public class Trip 
{
	private long id;
	private Place place;
	private Party party;
	private String createdAt;
	private String endedAt;
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
