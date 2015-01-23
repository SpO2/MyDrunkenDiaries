/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  TripMedia Adapter.                         *   
 *                                                      *   
 * Usage: Class that controls the interactions with the *
 * table tripmedia of the Database.                     *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;

import com.blackout.mydrunkendiaries.entites.Trip;
import com.blackout.mydrunkendiaries.entites.TripMedia;

/**
 * @author spo2
 *
 */
public class TripMediaSqliteAdapter extends BaseSqliteAdapter 
implements DatabaseAdpater<TripMedia>
{

	public final static String TABLE_TRIPMEDIA = "tripmedia";
	public final static String COLUMN_ID = "_id";
	public final static String COLUMN_PATH = "path";
	public final static String COLUMN_TRIP = "trip";
	
	public final static String[] COLUMN_LIST = {COLUMN_ID,
								 COLUMN_PATH,
								 COLUMN_TRIP};
	
	public final static String SCHEMA = "CREATE TABLE " +
	                             TripMediaSqliteAdapter.TABLE_TRIPMEDIA + 
	                             " ( " +
								 TripMediaSqliteAdapter.COLUMN_ID +
								 " integer primary key autoincrement, " +
								 TripMediaSqliteAdapter.COLUMN_PATH + 
								 " text not null, " +
								 TripMediaSqliteAdapter.COLUMN_TRIP + 
								 " integer not null, " +
								 " FOREIGN KEY(" + 
								 TripMediaSqliteAdapter.COLUMN_TRIP +
								 ") REFERENCES " + TripSqliteAdapter.TABLE_TRIP +
								 "(" + TripSqliteAdapter.COLUMN_ID + "));";
	
	/**
	 * Constructor
	 * @param context
	 */
	public TripMediaSqliteAdapter(Context context) 
	{
		super(context);
	}

	@Override
	public int describeContents() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) 
	{
		// TODO Auto-generated method stub	
	}

	/**
	 * Add a TripMedia in the database.
	 * @param tripMedia
	 * @return the id of the new tripMedia.
	 */
	@Override
	public long create(TripMedia tripMedia) 
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_PATH, tripMedia.getPath());
		values.put(COLUMN_TRIP, tripMedia.getTrip().getId());
		
		return this.getDb().insert(TABLE_TRIPMEDIA, null, values);
	}

	/**
	 * Update a TripMedia in the database.
	 * @param tripMedia
	 * @return
	 */
	@Override
	public int update(TripMedia tripMedia) 
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_PATH, tripMedia.getPath());
		values.put(COLUMN_TRIP, tripMedia.getTrip().getId());
		
		String whereClause = COLUMN_ID + " = ?";
		
		String[] whereArgs = {String.valueOf(tripMedia.getId())};
		
		return this.getDb().update(TABLE_TRIPMEDIA, values, whereClause, whereArgs);
	}

	/**
	 * Delete a TripMedia from the database.
	 * @param tripMedia
	 * @return
	 */
	@Override
	public int delete(TripMedia tripMedia) 
	{
		String whereClause = COLUMN_ID + " = ?";
		String whereArgs[] = {String.valueOf(tripMedia.getId())};
		
		return this.getDb().delete(TABLE_TRIPMEDIA, whereClause, whereArgs);
	}

	/**
	 * Find a TripMedia in the database.
	 * @param id
	 * @return a TripMedia
	 */
	@Override
	public TripMedia get(long id) 
	{
		String[] selectionArgs = {String.valueOf(id)};
		
		String selection = COLUMN_ID + " = ?";
		
		Cursor cursor = this.getDb().query(TABLE_TRIPMEDIA,
				COLUMN_LIST,
				selection,
				selectionArgs,
				null,
				null,
				null);
		cursor.moveToFirst();
		
		return this.cursorToItem(cursor);
	}

	/**
	 * Fetch all the TripMedia link to a Trip.
	 * @param trip
	 * @return Array of TripMedia
	 */
	public ArrayList<TripMedia> getByTrip(Trip trip)
	{
		String selection = COLUMN_TRIP + " = ?";
		String[] selectionArgs = {String.valueOf(trip.getId())};
		
		Cursor cursor = this.getDb().query(TABLE_TRIPMEDIA,
				COLUMN_LIST,
				selection,
				selectionArgs,
				null,
				null,
				null);
		
		ArrayList<TripMedia> tripMedias = new ArrayList<TripMedia>();
		if (cursor.moveToFirst())
		{
			while (!cursor.moveToFirst())
			{
				tripMedias.add(this.cursorToItem(cursor));
				cursor.moveToNext();
			}
		}
		
		return tripMedias;
	}
	
	/**
	 * Fetch all the tripmedias from the database.
	 * @return Array of TripMedia.
	 */
	@Override
	public ArrayList<TripMedia> getAll() 
	{
		Cursor cursor = this.getDb().query(TABLE_TRIPMEDIA,
				COLUMN_LIST,
				null,
				null,
				null,
				null,
				null);
		
		ArrayList<TripMedia> tripMedias = new ArrayList<TripMedia>();
		if (cursor.moveToFirst())
		{
			while (!cursor.isAfterLast())
			{
				tripMedias.add(this.cursorToItem(cursor));
			}
		}
		
		return tripMedias;
	}

	/**
	 * Convert a record into a TripMedia object.
	 * @param cursor
	 * @return TripMedia
	 */
	@Override
	public TripMedia cursorToItem(Cursor cursor) 
	{
		TripMedia tripMedia = new TripMedia();
		tripMedia.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
		tripMedia.setPath(cursor.getString(cursor.getColumnIndex(COLUMN_PATH)));
		
		return tripMedia;
	}
	
}
