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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class of the place entity.
 * @author romain
 *
 */
public class Place implements Parcelable {
	public static final int PARCELABLE_CODE = 1;
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
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Place() {
	}

	/*
	 * Place parcel Constructor
	 */
	public Place(Parcel in) {
		this.id = in.readLong();
		this.name = in.readString();
		this.longitude = in.readDouble();
		this.latitude = in.readDouble();

	}

	@Override
	public int describeContents() {
		return PARCELABLE_CODE;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.name);
		dest.writeDouble(this.longitude);
		dest.writeDouble(this.latitude);
	}

	public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
		@Override
		public Place createFromParcel(Parcel source) {
			return new Place(source);
		}

		@Override
		public Place[] newArray(int size) {
			return new Place[size];
		}
	};

}
