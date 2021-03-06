/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Place Adapter.                             *   
 *                                                      *   
 * Usage: Class that controls the interactions with the *
 * table place of the Database.                         *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.data;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.blackout.mydrunkendiaries.entites.Place;

/**
 * Sqlite adapter for places entity.
 * @author romain
 *
 */
public class PlaceSqliteAdapter extends BaseSqliteAdapter implements DatabaseAdpater<Place>
{

	/**
	 * Name of the table.
	 */
	public final static String TABLE_PLACE = "Place";
	/**
	 * Name of the id column.
	 */
	public final static String COLUMN_ID = "_id";
	/**
	 * Name of the name column.
	 */
	public final static String COLUMN_NAME = "name";
	/**
	 * Name of the longitude column.
	 */
	public final static String COLUMN_LONGITUDE = "longitude";
	/**
	 * Name of the latitude column.
	 */
	public final static String COLUMN_LATITUDE = "latitude";
	/**
	 * List of the column.
	 */
	public final static String[] COLUMN_LIST={COLUMN_ID,
								 COLUMN_NAME,
								 COLUMN_LONGITUDE,
								 COLUMN_LATITUDE};
	/**
	 * Schema of the table.
	 */
	public final static String SCHEMA = "CREATE TABLE " + 
								PlaceSqliteAdapter.TABLE_PLACE + " ( " +
								PlaceSqliteAdapter.COLUMN_ID +
								" integer primary key autoincrement, " +
								PlaceSqliteAdapter.COLUMN_NAME + " text not null, " +
								PlaceSqliteAdapter.COLUMN_LONGITUDE + " REAL, " +
								PlaceSqliteAdapter.COLUMN_LATITUDE + " REAL);";
	
	/**
	 * Constructor
	 * @param context
	 */
	public PlaceSqliteAdapter(Context context) 
	{
		super(context);
		//placeFixtures();
	}

	/**
	 * Add a new Place in the database.
	 * @param place
	 * @return the id of the new place.
	 */
	@Override
	public long create(Place place) 
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, place.getName());
		values.put(COLUMN_LONGITUDE, place.getLongitude());
		values.put(COLUMN_LATITUDE, place.getLatitude());
		
		return this.getDb().insert(TABLE_PLACE, null, values);
	}

	/**
	 * Update a Place in the database.
	 * @param place
	 * @return
	 */
	@Override
	public int update(Place place) 
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, place.getName());
		values.put(COLUMN_LONGITUDE, place.getLongitude());
		values.put(COLUMN_LATITUDE, place.getLatitude());
		
		String whereClause = COLUMN_ID + " = ?";
		
		String[] whereArgs = {String.valueOf(place.getId())};
		
		return this.getDb().update(TABLE_PLACE, values, whereClause, whereArgs);
	}

	/**
	 * Delete a Place in the database.
	 * @param place
	 * @return
	 */
	@Override
	public int delete(Place place) 
	{
		String whereClause = COLUMN_ID + " = ?";
		String whereArgs[] = {String.valueOf(place.getId())};
		
		return this.getDb().delete(TABLE_PLACE, whereClause, whereArgs);
	}

	/**
	 * Find a Place in the database.
	 * @param id
	 * @return a Place
	 */
	@Override
	public Place get(long id)
	{
		
		String[] selectionArgs = {String.valueOf(id)};
		
		String selection = COLUMN_ID + " = ?";
		
		Cursor cursor = this.getDb().query(TABLE_PLACE,
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
	 * Get a cursor for a place.
	 * @param id
	 * @return cursor of the place.
	 */
	public Cursor getCursor(long id)
	{
		
		String[] selectionArgs = {String.valueOf(id)};
		
		String selection = COLUMN_ID + " = ?";
		
		Cursor cursor = this.getDb().query(TABLE_PLACE,
				COLUMN_LIST,
				selection,
				selectionArgs,
				null,
				null,
				null);
		cursor.moveToFirst();
		
		return cursor;
	}

	/**
	 * Fetch all the places from the database.
	 * @return Array of Place.
	 */
	@Override
	public ArrayList<Place> getAll() 
	{
		Cursor cursor = this.getDb().query(TABLE_PLACE,
				COLUMN_LIST,
				null,
				null,
				null,
				null,
				null);
		
		ArrayList<Place> places = new ArrayList<Place>();
		
		if (cursor.moveToFirst())
		{
			while (!cursor.isAfterLast())
			{
				places.add(cursorToItem(cursor));
				cursor.moveToNext();
			}
		}
		
		return places;
	}
	
	/**
	 * Get a cursor for places.
	 * @return cursor for the places.
	 */
	public Cursor getAllCursor() 
	{
		Cursor cursor = this.getDb().query(TABLE_PLACE,
				COLUMN_LIST,
				null,
				null,
				null,
				null,
				null);
		cursor.moveToFirst();
		
		return cursor;
	}

	/**
	 * Convert a record into a Place object.
	 * @param cursor
	 * @return a Place.
	 */
	public static Place cursorToItem(Cursor cursor) 
	{
		Place place = new Place();
		place.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
		place.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
		place.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
		place.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)));
		
		return place;
	}
	

	
	/**
	 * Fixtures - use for test only.
	 */
	public void placeFixtures()
	{
		Place place = new Place();
		open();
		for (int i=0; i<5; i++)
		{
			place.setId(i+1);
			place.setLatitude(12.2);
			place.setLongitude(14.4);
			place.setName("Place " + String.valueOf(i));
			this.create(place);
		}
	}
}
