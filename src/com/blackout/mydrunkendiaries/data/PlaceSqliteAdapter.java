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

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;

import com.blackout.mydrunkendiaries.entites.Party;
import com.blackout.mydrunkendiaries.entites.Place;

/**
 * @author spo2
 *
 */
public class PlaceSqliteAdapter extends BaseSqliteAdapter implements DatabaseAdpater<Place>
{

	public final static String TABLE_PLACE = "Place";
	public final static String COLUMN_ID = "_id";
	public final static String COLUMN_NAME = "name";
	public final static String COLUMN_LONGITUDE = "longitude";
	public final static String COLUMN_LATITUDE = "latitude";
	public final static String COLUMN_PARTY = "party";
	public final static String COLUMN_PARTY_ID = "paid";
	public final static String COLUMN_PARTY_NAME = "paname";
	public final static String[] COLUMN_LIST={COLUMN_ID,
								 COLUMN_NAME,
								 COLUMN_LONGITUDE,
								 COLUMN_LATITUDE,
								 COLUMN_PARTY};
	
	public final static String SCHEMA = "CREATE TABLE " + 
								PlaceSqliteAdapter.TABLE_PLACE + " ( " +
								PlaceSqliteAdapter.COLUMN_ID +
								" integer primary key autoincrement, " +
								PlaceSqliteAdapter.COLUMN_NAME + " text not null, " +
								PlaceSqliteAdapter.COLUMN_LONGITUDE + " REAL , " +
								PlaceSqliteAdapter.COLUMN_LATITUDE + " REAL, " +
								PlaceSqliteAdapter.COLUMN_PARTY + 
								" integer not null, " + 
								"FOREIGN KEY(" + PlaceSqliteAdapter.COLUMN_PARTY +
								") REFERENCES " + PartySqliteAdapter.TABLE_PARTY +
								"(" + PartySqliteAdapter.COLUMN_ID + "));";
	
	private final String QUERYWITHPARTY = "SELECT p._id, p.Name, p.longitude, p.latitude, " +
			   "p.party, pa._id paid, pa.name paname, pa.createAt, " +
			   "pa.endedAt " +
			   "FROM Place p " +
			   "LEFT JOIN Party pa ON pa._id = p.party" +
			   "WHERE p._id = ?";
	
	/**
	 * Constructor
	 * @param context
	 */
	public PlaceSqliteAdapter(Context context) 
	{
		super(context);
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
		values.put(COLUMN_PARTY, place.getParty().getId());
		
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
		values.put(COLUMN_PARTY, place.getParty().getId());
		
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
		
		return this.cursorToItem(cursor);
	}
	
	/**
	 * Find a Place with its Party.
	 * @param id
	 * @return a Place with 
	 */
	public Place getWithParty(long id)
	{
		String[] selectionArgs = {String.valueOf(id)};
		
		Cursor cursor = this.getDb().rawQuery(QUERYWITHPARTY, selectionArgs);
		
		return this.cursorToItemWithParty(cursor);
	}
	
	/**
	 * Fetch all the Place link to a Party
	 * @param party
	 * @return Array of Place
	 */
	public ArrayList<Place> getByParty(Party party)
	{
		String selection = COLUMN_PARTY + " = ?";
		String selectionArgs[] = {String.valueOf(party.getId())};
		
		Cursor cursor = this.getDb().query(TABLE_PLACE,
				COLUMN_LIST,
				selection,
				selectionArgs,
				null,
				null,
				null);
		
		ArrayList<Place> places = new ArrayList<Place>();
		
		if (cursor.moveToFirst())
		{
			while (!cursor.isAfterLast())
			{
				places.add(this.cursorToItem(cursor));
				cursor.moveToNext();
			}
		}
		
		return places;
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
				places.add(this.cursorToItem(cursor));
			}
		}
		
		return places;
	}

	/**
	 * Convert a record into a Place object.
	 * @param cursor
	 * @return a Place.
	 */
	@Override
	public Place cursorToItem(Cursor cursor) 
	{
		Place place = new Place();
		place.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
		place.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
		place.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
		place.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)));
		
		return place;
	}
	
	/**
	 * Convert a record into a Place object with a Party.
	 * @param cursor
	 * @return a Place with the Party.
	 */
	public Place cursorToItemWithParty(Cursor cursor)
	{
		Place place = this.cursorToItem(cursor);
		
		Party party = new Party();
		party.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_PARTY_ID)));
		party.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PARTY_NAME)));
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dt = formatter.parseDateTime(cursor.getString(cursor
				 .getColumnIndex(PartySqliteAdapter.COLUMN_CREATEDAT)));
		party.setCreatedAt(dt);
		dt = formatter.parseDateTime(cursor.getString(cursor
				.getColumnIndex(PartySqliteAdapter.COLUMN_ENDEDAT)));
				party.setEndedAt(dt);
		
		place.setParty(party);
		
		return place;
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
}
