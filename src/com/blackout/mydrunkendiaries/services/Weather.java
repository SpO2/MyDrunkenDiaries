/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Classes for the weather webservice.        *   
 *                                                      *   
 * Usage: Contains data for the weather.         	    *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.services;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * Contains the results of the weather webservice.
 * @author spo2
 *
 */
public class Weather 
{
	/**
	 * Coordinate object
	 */
	@SerializedName("coord")
	private Coord coord;
	/**
	 * Sys object
	 */
	@SerializedName("sys")
	private Sys sys;
	/**
	 * MainData object
	 */
	@SerializedName("main")
	private MainData mainData;
	/**
	 * Wind object
	 */
	@SerializedName("wind")
	private Wind wind;
	/**
	 * List of weather conditions
	 */
	@SerializedName("weather")
	private ArrayList<WeatherData> weatherDatas;
	/**
	 * Name of the city
	 */
	@SerializedName("name")
	private String name;
	/**
	 * Image of the current weather
	 */
	private byte[] iconData;
	
	public Coord getCoord()
	{
		return this.coord;
	}
	public void setCoord(Coord coord)
	{
		this.coord = coord;
	}
	
	public Sys getSys()
	{
		return this.sys;
	}
	public void setSys(Sys sys)
	{
		this.sys = sys;
	}
	
	public MainData getMainData()
	{
		return this.mainData;
	}
	public void setMaindata(MainData mainData)
	{
		this.mainData = mainData;
	}
	
	public Wind getWind()
	{
		return this.wind;
	}
	public void setWind(Wind wind)
	{
		this.wind = wind;
	}
	
	public ArrayList<WeatherData> getWeatherDatas()
	{
		return this.weatherDatas;
	}
	public void setWeatherDatas(ArrayList<WeatherData> weatherDatas)
	{
		this.weatherDatas = weatherDatas;
	}
	
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public byte[] getIconData()
	{
		return this.iconData;
	}
	public void setIconData(byte[] iconData)
	{
		this.iconData = iconData;
	}
	
	/**
	 * Class for the coordinates (latitude and longitude)
	 * @author spo2
	 *
	 */
	public class Coord
	{
		/**
		 * Longitude
		 */
		@SerializedName("lon")
		private Float lon;
		/**
		 * Latitude
		 */
		@SerializedName("lat")
		private Float lat;
		
		public Float getLon()
		{
			return this.lon;
		}
		
		public void setLon(Float lon)
		{
			this.lon = lon;
		}
		
		public Float getLat()
		{
			return this.lat;
		}
		
		public void setLat(Float lat)
		{
			this.lat = lat;
		}
	}

	/**
	 * Contains country name, sunrise and sunset of current day
	 * @author spo2
	 *
	 */
	public class Sys
	{
		/**
		 * Name of the country
		 */
		@SerializedName("country")
		private String country;
		/**
		 * Hour of the sunrise
		 */
		@SerializedName("sunrise")
		private Long sunrise;
		/**
		 * Hour of the sunset
		 */
		@SerializedName("sunset")
		private Long sunset;
		
		public String getCountry()
		{
			return this.country;
		}
		public void setCountry(String country)
		{
			this.country = country;
		}
		
		public Long getSunrise()
		{
			return this.sunrise;
		}
		public void setSunrise(Long sunrise)
		{
			this.sunrise = sunrise;
		}
		
		public Long getSunset()
		{
			return this.sunset;
		}
		public void setSunset(Long sunset)
		{
			this.sunset = sunset;
		}
		
	}

	/**
	 * Contains global data for the current weather
	 * @author spo2
	 *
	 */
	public class WeatherData
	{
		/**
		 * Id of the current weather returned
		 */
		@SerializedName("id")
		private Long id;
		/**
		 * Current condition (cloudy, rainy...)
		 */
		@SerializedName("main")
		private String main;
		/**
		 * Description of the current condition
		 */
		@SerializedName("description")
		private String description;
		/**
		 * Name of the thumb associated with the current condition
		 */
		@SerializedName("icon")
		private String icon;
		
		public Long getId()
		{
			return this.id;
		}
		public void setId(Long id)
		{
			this.id = id;
		}
		
		public String getMain()
		{
			return this.main;
		}
		public void setMain(String main)
		{
			this.main = main;
		}
		
		public String getDescription()
		{
			return this.description;
		}
		public void setDescription(String description)
		{
			this.description = description;
		}
		
		public String getIcon()
		{
			return this.icon;
		}
		public void setIcon(String icon)
		{
			this.icon = icon;
		}
		
	}

	/**
	 * Contains specific informations for the current weather
	 * @author spo2
	 *
	 */
	public class MainData
	{
		/**
		 * Current temperature
		 */
		@SerializedName("temp")
		private Float temp;
		/**
		 * Current humidity
		 */
		@SerializedName("humidity")
		private Float humidity;
		/**
		 * Current pressure
		 */
		@SerializedName("pressure")
		private Float pressure;
		/**
		 * Minimum temperature
		 */
		@SerializedName("temp_min")
		private Float tempMin;
		/**
		 * Maximum temperature
		 */
		@SerializedName("temp_max")
		private Float tempMax;
		
		public Float getTemp()
		{
			return this.temp;
		}
		public void setTemp(Float temp)
		{
			this.temp = temp;
		}
		
		public Float getHumidity()
		{
			return this.humidity;
		}
		public void setHumidity(Float humidity)
		{
			this.humidity = humidity;
		}
		
		public Float getPressure()
		{
			return this.pressure;
		}
		public void setPressure(Float pressure)
		{
			this.pressure = pressure;
		}
		
		public Float getTempMin()
		{
			return this.tempMin;
		}
		public void setTempMin(Float tempMin)
		{
			this.tempMin = tempMin;
		}
		
		public Float getTempMax()
		{
			return this.tempMax;
		}
		public void setTempMax(Float tempMax)
		{
			this.tempMax = tempMax;
		}
	}

	/**
	 * Contains detail on wind conditions
	 * @author spo2
	 *
	 */
	public class Wind
	{
		/**
		 * Wind speed
		 */
		@SerializedName("speed")
		private Float speed;
		/**
		 * Direction (angle)
		 */
		@SerializedName("deg")
		private Float deg;
		
		public Float getSpeed()
		{
			return this.speed;
		}
		public void setSpeed(Float speed)
		{
			this.speed = speed;
		}
		
		public Float getDeg()
		{
			return this.deg;
		}
		public void setDeg(Float deg)
		{
			this.deg = deg;
		}
	}
}
