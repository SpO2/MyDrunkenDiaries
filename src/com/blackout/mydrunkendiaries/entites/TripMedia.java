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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class for the TripMedia entity.
 * @author romain
 *
 */
public class TripMedia implements Parcelable {
	public static final int PARCELABLE_CODE = 3;
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
	 * @return the trip
	 */
	public Trip getTrip() {
		return trip;
	}

	/**
	 * @param trip
	 *            the trip to set
	 */
	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	/**
	 * 
	 * @return the name of the media
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/*
	 * TripMedia Constructor
	 */
	public TripMedia() {
	}

	/*
	 * TripMedia parcel Constructor
	 */
	public TripMedia(Parcel in) {
		this.id = in.readLong();
		this.trip = in.readParcelable(this.trip.getClass().getClassLoader());
		this.name = in.readString();
		this.path = in.readString();

	}

	@Override
	public int describeContents() {
		return PARCELABLE_CODE;
	}

	@Override
	public void writeToParcel(Parcel dest, int flag) {
		dest.writeLong(this.id);
		dest.writeValue(this.trip);
		dest.writeString(this.name);
		dest.writeString(this.path);

	}

	public static final Parcelable.Creator<TripMedia> CREATOR = new Parcelable.Creator<TripMedia>() {
		@Override
		public TripMedia createFromParcel(Parcel source) {
			return new TripMedia(source);
		}

		@Override
		public TripMedia[] newArray(int size) {
			return new TripMedia[size];
		}
	};

}
