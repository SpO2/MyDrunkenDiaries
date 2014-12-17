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

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blackout.mydrunkendiaries.entites.Place;

/**
 * @author spo2
 *
 */
public class PlaceSqliteAdapter 
{
	public final static String TABLE_PLACE = "Place";
	public final static String COLUMN_ID = "_id";
	public final static String COLUMN_NAME = "Name";
	public final static String COLUMN_LONGITUDE = "longitude";
	public final static String COLUMN_LATITUDE = "latitude";
	public final static String COLUMN_PARTY = "Party";
	
	public final static String SCHEMA = "CREATE TABLE " + 
								PlaceSqliteAdapter.TABLE_PLACE + " ( " +
								PlaceSqliteAdapter.COLUMN_ID +
								" integer primary key autoincrement, " +
								PlaceSqliteAdapter.COLUMN_NAME + "text not null, " +
								PlaceSqliteAdapter.COLUMN_LONGITUDE + " REAL , " +
								PlaceSqliteAdapter.COLUMN_LATITUDE + " REAL, " +
								PlaceSqliteAdapter.COLUMN_PARTY + 
								" integer not null " + 
								"FOREIGN KEY(" + PlaceSqliteAdapter.COLUMN_PARTY +
								") REFERENCES " + PartySqliteAdapter.TABLE_PARTY +
								"(" + PartySqliteAdapter.COLUMN_ID + ")";
	
	private OpenHelperSqlite helper;
	private SQLiteDatabase db;
	
	
	/**
	 * Constructor
	 * @param context
	 */
	public PlaceSqliteAdapter(Context context)
	{
		this.helper = new OpenHelperSqlite(context);
	}
	
	public void open()
	{
		this.db = this.helper.getWritableDatabase();
	}
	
	public void close()
	{
		this.helper.close();
	}
	
	public Place getPlace(long id)
	{
		String[] columns = {COLUMN_ID,
				 COLUMN_NAME,
				 COLUMN_LONGITUDE,
				 COLUMN_LATITUDE,
				 COLUMN_PARTY};
		
		String[] selectionArgs = {String.valueOf(id)};
		
		String selection = COLUMN_ID + " = ?";
		
		Cursor cursor = this.db.query(TABLE_PLACE,
				columns,
				selection,
				selectionArgs,
				null,
				null,
				null);
		cursor.moveToFirst();
		
		return null;
	}
	
}
