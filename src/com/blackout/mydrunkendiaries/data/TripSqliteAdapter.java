/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Trip Adapter.                              *   
 *                                                      *   
 * Usage: Class that controls the interactions with the *
 * table trip of the Database.                          *   
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
import com.blackout.mydrunkendiaries.entites.Trip;

/**
 * @author spo2
 *
 */
public class TripSqliteAdapter extends BaseSqliteAdapter 
implements DatabaseAdpater<Trip>
{

	public final static String TABLE_TRIP = "Trip";
	public final static String COLUMN_ID = "_id";
	public final static String COLUMN_PLACE = "place";
	public final static String COLUMN_PARTY = "party";
	public final static String COLUMN_PLACESCORE = "placescore";
	public final static String COLUMN_DEPRAVITY = "depravity";
	public final static String COLUMN_CREATEDAT = "createdat";
	public final static String COLUMN_ENDEDAT = "endedat";
	public final static String COLUMN_PLACE_ID = "pid";
	public final static String[] COLUM_LIST = {COLUMN_ID,
								 COLUMN_PLACE,
								 COLUMN_PARTY,
								 COLUMN_PLACESCORE,
								 COLUMN_DEPRAVITY,
								 COLUMN_CREATEDAT,
								 COLUMN_ENDEDAT};
	
	public final static String SCHEMA = "CREATE TABLE " +
								TripSqliteAdapter.TABLE_TRIP + " ( " +
								TripSqliteAdapter.COLUMN_ID + 
								" integer primary key autoincrement, " +
								TripSqliteAdapter.COLUMN_PLACE + " integer, " +
								TripSqliteAdapter.COLUMN_PARTY + " integer, " +
								TripSqliteAdapter.COLUMN_PLACESCORE + " integer, " +
								TripSqliteAdapter.COLUMN_DEPRAVITY + " integer, " +
								TripSqliteAdapter.COLUMN_CREATEDAT + " text not null, " +
								TripSqliteAdapter.COLUMN_ENDEDAT + " text not null, " +
								"FOREIGN KEY(" + TripSqliteAdapter.COLUMN_PARTY + 
								") REFERENCES " + PartySqliteAdapter.TABLE_PARTY + 
								"(" + PartySqliteAdapter.COLUMN_ID +"), " + 
								"FOREIGN KEY(" + TripSqliteAdapter.COLUMN_PLACE +
								") REFERENCES " + PlaceSqliteAdapter.TABLE_PLACE +
								"(" + PlaceSqliteAdapter.COLUMN_ID + "));";
	
	private final String QUERYWITHPLACE = "SELECT t._id, t.placescore, t.place " +
						  "t.depravity, t.createdat, t.endedat, p._id pid, " + 
						  "p.name, p.longitude, p.latitude, p.party " +
						  "FROM Trip t " +
						  "LEFT JOIN Place p ON p._id = t.place " +
						  "WHERE t._id = ?";
	
	public TripSqliteAdapter(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
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
	 * Add a new trip in the database.
	 * @param trip
	 * @return the id of the new trip.
	 */
	@Override
	public long create(Trip trip) 
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_PLACESCORE, trip.getPlaceScore());
		values.put(COLUMN_DEPRAVITY, trip.getDepravity());
		values.put(COLUMN_CREATEDAT, trip.getCreatedAt().toString());
		values.put(COLUMN_ENDEDAT, trip.getEndedAt().toString());
		values.put(COLUMN_PLACE, trip.getPlace().getId());
		values.put(COLUMN_PARTY, trip.getParty().getId());
		
		return this.getDb().insert(TABLE_TRIP, null, values);
	}

	/**
	 * Update a place in the database.
	 * @param trip
	 * @return
	 */
	@Override
	public int update(Trip trip) 
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_PLACESCORE, trip.getPlaceScore());
		values.put(COLUMN_DEPRAVITY, trip.getDepravity());
		values.put(COLUMN_ENDEDAT, trip.getEndedAt().toString());
		values.put(COLUMN_PLACE, trip.getPlace().getId());
		values.put(COLUMN_PARTY, trip.getParty().getId());
		
		String whereClause = COLUMN_ID + " = ?";
		
		String[] whereArgs = {String.valueOf(trip.getId())};
		
		return this.getDb().update(TABLE_TRIP, values, whereClause, whereArgs);
	}

	/**
	 * Delete a trip in the database.
	 * @param trip
	 * @return
	 */
	@Override
	public int delete(Trip trip) 
	{
		String whereClause = COLUMN_ID + " = ?";
		String whereArgs[] = {String.valueOf(trip.getId())};
		
		return this.getDb().delete(TABLE_TRIP, whereClause, whereArgs);
	}

	/**
	 * Find a trip in the database.
	 * @param id
	 * @return a trip.
	 */
	@Override
	public Trip get(long id) 
	{
		String[] selectionArgs = {String.valueOf(id)};
		
		String selection = COLUMN_ID + " = ?";
		
		Cursor cursor = this.getDb().query(TABLE_TRIP,
				COLUM_LIST,
				selection,
				selectionArgs,
				null,
				null,
				null);
		cursor.moveToFirst();
		
		return this.cursorToItem(cursor);
	}
	
	/**
	 * Find a Trip with its place in the database.
	 * @param id
	 * @return a Trip with Place.
	 */
	public Trip getWithPlace(long id)
	{
		String[] selectionArgs = {String.valueOf(id)};
		
		Cursor cursor =  this.getDb().rawQuery(QUERYWITHPLACE, selectionArgs);
		cursor.moveToFirst();
		
		return this.cursorToItemWithPlace(cursor);
	}
	
	/**
	 * Fetch all the Trip link to a Party
	 * @param party
	 * @return Array of Trip.
	 */
	public ArrayList<Trip> getByParty(Party party)
	{
		String selection = COLUMN_PARTY + " = ?";
		String selectionArgs[] = {String.valueOf(party.getId())};
		
		Cursor cursor = this.getDb().query(TABLE_TRIP,
				COLUM_LIST,
				selection,
				selectionArgs,
				null,
				null,
				null);
		
		ArrayList<Trip> trips = new ArrayList<Trip>();
		if (cursor.moveToFirst())
		{
			while (!cursor.moveToFirst())
			{
				trips.add(this.cursorToItem(cursor));
				cursor.moveToNext();
			}
		}
		
		return trips;
	}

	/**
	 * Fetch all the trip from the database.
	 * @return Array of Trip
	 */
	@Override
	public ArrayList<Trip> getAll() 
	{
		Cursor cursor = this.getDb().query(TABLE_TRIP,
				COLUM_LIST,
				null,
				null,
				null,
				null,
				null);
		
		ArrayList<Trip> trips = new ArrayList<Trip>();
		
		if (cursor.moveToFirst())
		{
			while (!cursor.isAfterLast())
			{
				trips.add(this.cursorToItem(cursor));
				cursor.moveToNext();
			}
		}
		
		return trips;
	}

	/**
	 * Convert a record into a trip object.
	 * @param cursor
	 * @return a Trip
	 */
	@Override
	public Trip cursorToItem(Cursor cursor) 
	{
		Trip trip = new Trip();
		trip.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
		trip.setPlaceScore(cursor.getInt(cursor.getColumnIndex(COLUMN_PLACESCORE)));
		trip.setDepravity(cursor.getInt(cursor.getColumnIndex(COLUMN_DEPRAVITY)));
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dt = formatter.parseDateTime(cursor.getString(cursor
				.getColumnIndex(COLUMN_CREATEDAT)));
		trip.setCreatedAt(dt);
		dt = formatter.parseDateTime(cursor.getString(cursor
				.getColumnIndex(COLUMN_ENDEDAT)));
		trip.setEndedAt(dt);
		return trip;
	}
	
	/**
	 * Convert a record to a Trip with its Place.
	 * @param cursor
	 * @return a Trip with its Place.
	 */
	public Trip cursorToItemWithPlace(Cursor cursor)
	{
		Trip trip = this.cursorToItem(cursor);
		
		Place place = new Place();
		place.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_PLACE_ID)));
		place.setName(cursor.getString(cursor.getColumnIndex(
				PlaceSqliteAdapter.COLUMN_NAME)));
		place.setLongitude(cursor.getDouble(
				cursor.getColumnIndex(PlaceSqliteAdapter.COLUMN_LONGITUDE)));
		place.setLatitude(cursor.getDouble(
				cursor.getColumnIndex(PlaceSqliteAdapter.COLUMN_LATITUDE)));
		
		trip.setPlace(place);
		return trip;
	}
	

}
