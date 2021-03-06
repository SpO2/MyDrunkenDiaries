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

import com.blackout.mydrunkendiaries.entites.Trip;
import com.blackout.mydrunkendiaries.entites.TripMedia;

/**
 * Sqlite adapter for TripMedia entity.
 * @author romain
 *
 */
public class TripMediaSqliteAdapter extends BaseSqliteAdapter 
implements DatabaseAdpater<TripMedia>
{
	/**
	 * Name of the table.
	 */
	public final static String TABLE_TRIPMEDIA = "tripmedia";
	/**
	 * Name of the id column.
	 */
	public final static String COLUMN_ID = "_id";
	/**
	 * Name of the path column.
	 */
	public final static String COLUMN_PATH = "path";
	/***
	 * Name of the name column.
	 */
	public final static String COLUMN_NAME = "name";
	/**
	 * Name of the trip column.
	 */
	public final static String COLUMN_TRIP = "trip";
	/**
	 * List of the column.
	 */
	public final static String[] COLUMN_LIST = {COLUMN_ID,
								 COLUMN_PATH,
								 COLUMN_NAME,
								 COLUMN_TRIP};
	/**
	 * Schema of the table.
	 */
	public final static String SCHEMA = "CREATE TABLE " +
	                             TripMediaSqliteAdapter.TABLE_TRIPMEDIA + 
	                             " ( " +
								 TripMediaSqliteAdapter.COLUMN_ID +
								 " integer primary key autoincrement, " +
								 TripMediaSqliteAdapter.COLUMN_PATH + 
								 " text not null, " +
								 TripMediaSqliteAdapter.COLUMN_NAME +
								 " text, " +
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
		values.put(COLUMN_NAME, tripMedia.getName());
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
		values.put(COLUMN_NAME, tripMedia.getName());
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
		
		return cursorToItem(cursor);
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
			while (!cursor.isAfterLast())
			{
				tripMedias.add(cursorToItem(cursor));
				cursor.moveToNext();
			}
		}
		
		return tripMedias;
	}
	/**
	 * Get cursor for all TripMedia by trip .
	 * @param tripId id of the trip
	 * @return Cursor of Trip media
	 */
	public Cursor getCursorByTrip(long tripId)
	{
		String selection = COLUMN_TRIP + " = ?";
		String[] selectionArgs = {String.valueOf(tripId)};
		
		Cursor cursor = this.getDb().query(TABLE_TRIPMEDIA,
				COLUMN_LIST,
				selection,
				selectionArgs,
				null,
				null,
				null);
		return cursor;
	}
	/**
	 * Fetch all the tripmedias by trip.
	 * @return Array of TripMedia.
	 */
	public ArrayList<TripMedia> getByTrip(long tripId)
	{
		String selection = COLUMN_TRIP + " = ?";
		String[] selectionArgs = {String.valueOf(tripId)};
		
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
			while (!cursor.isAfterLast())
			{
				tripMedias.add(cursorToItem(cursor));
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
				tripMedias.add(cursorToItem(cursor));
				cursor.moveToNext();
			}
		}
		
		return tripMedias;
	}

	/**
	 * Convert a record into a TripMedia object.
	 * @param cursor
	 * @return TripMedia
	 */
	public static TripMedia cursorToItem(Cursor cursor) 
	{
		TripMedia tripMedia = new TripMedia();
		tripMedia.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
		tripMedia.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
		tripMedia.setPath(cursor.getString(cursor.getColumnIndex(COLUMN_PATH)));
		
		return tripMedia;
	}
	
}
