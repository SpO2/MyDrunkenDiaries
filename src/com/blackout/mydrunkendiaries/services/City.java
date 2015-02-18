package com.blackout.mydrunkendiaries.services;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class City 
{
	/**
	 * Count of items
	 */
	@SerializedName("count")
	private Integer count;
	/**
	 * List of items
	 */
	@SerializedName("list")
	private List<Weather> weathers;
		
	public Integer getCount()
	{
		return this.count;
	}
	public void setCount(Integer count)
	{
		this.count = count;
	}
	
	public List<Weather> getWeathers()
	{
		return this.weathers;
	}
	public void setWeathers(List<Weather> weathers)
	{
		this.weathers = weathers;
	}
}
