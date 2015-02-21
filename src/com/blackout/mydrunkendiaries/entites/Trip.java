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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class of the Trip entity.
 * @author romain
 *
 */
public class Trip implements Parcelable {
	public static final int PARCELABLE_CODE = 2;
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
	 * @return the place
	 */
	public Place getPlace() {
		return place;
	}

	/**
	 * @param place
	 *            the place to set
	 */
	public void setPlace(Place place) {
		this.place = place;
	}

	/**
	 * @return the party
	 */
	public Party getParty() {
		return party;
	}

	/**
	 * @param party
	 *            the party to set
	 */
	public void setParty(Party party) {
		this.party = party;
	}

	/**
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the endedAt
	 */
	public String getEndedAt() {
		return endedAt;
	}

	/**
	 * @param endedAt
	 *            the endedAt to set
	 */
	public void setEndedAt(String endedAt) {
		this.endedAt = endedAt;
	}

	/**
	 * @return the placeScore
	 */
	public Integer getPlaceScore() {
		return placeScore;
	}

	/**
	 * @param placeScore
	 *            the placeScore to set
	 */
	public void setPlaceScore(Integer placeScore) {
		this.placeScore = placeScore;
	}

	/**
	 * @return the depravity
	 */
	public Integer getDepravity() {
		return depravity;
	}

	/**
	 * @param depravity
	 *            the depravity to set
	 */
	public void setDepravity(Integer depravity) {
		this.depravity = depravity;
	}

	/*
	 * Trip Consturctor
	 */
	public Trip() {
	}

	/*
	 * Trip parcel Constructor
	 */
	public Trip(Parcel in) {
		this.id = in.readLong();
		this.party = in.readParcelable(this.party.getClass().getClassLoader());
		this.place = in.readParcelable(this.place.getClass().getClassLoader());
		this.createdAt = in.readString();
		this.endedAt = in.readString();
		this.placeScore = in.readInt();
		this.depravity = in.readInt();

	}

	@Override
	public int describeContents() {
		return PARCELABLE_CODE;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeValue(this.party);
		dest.writeValue(this.place);
		dest.writeString(this.createdAt);
		dest.writeString(this.endedAt);
		dest.writeInt(this.placeScore);
		dest.writeInt(this.depravity);

	}

	public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
		@Override
		public Trip createFromParcel(Parcel source) {
			return new Trip(source);
		}

		@Override
		public Trip[] newArray(int size) {
			return new Trip[size];
		}
	};

}
