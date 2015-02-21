/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Entites - Party.                           *   
 *                                                      *   
 * Usage:                                               *   
 *                                                      *   
 ********************************************************/

package com.blackout.mydrunkendiaries.entites;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class for the party entity.
 * @author romain
 *
 */
public class Party implements Parcelable {
	public static final int PARCELABLE_CODE = 0;
	/**
	 * Id of the party.
	 */
	private long id;
	/**
	 * Name of the party.
	 */
	private String name;
	/**
	 * Creation date of the party.
	 */
	private String createdAt;
	/**
	 * Ended date of the party.
	 */
	private String endedAt;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            : the id to set
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
	 *            : the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	/*
	 * Party constructor
	 */
	public Party() {
	}

	/*
	 * Party parcel Constructor
	 */
	public Party(Parcel in) {
		this.id = in.readLong();
		this.name = in.readString();
		this.createdAt = in.readString();
		this.endedAt = in.readString();

	}

	@Override
	public int describeContents() {
		return PARCELABLE_CODE;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.name);
		dest.writeString(this.createdAt);
		dest.writeString(this.endedAt);
	}

	public static final Parcelable.Creator<Party> CREATOR = new Parcelable.Creator<Party>() {
		@Override
		public Party createFromParcel(Parcel source) {
			return new Party(source);
		}

		@Override
		public Party[] newArray(int size) {
			return new Party[size];
		}
	};

}
