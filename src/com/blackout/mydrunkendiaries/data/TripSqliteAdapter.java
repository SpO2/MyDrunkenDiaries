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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;

import com.blackout.mydrunkendiaries.entites.Party;
import com.blackout.mydrunkendiaries.entites.Place;
import com.blackout.mydrunkendiaries.entites.Trip;
import com.blackout.mydrunkendiaries.tools.DateTimeTools;

/**
 * Sqlite adapter for Trip entity.
 * @author romain
 *
 */
public class TripSqliteAdapter extends BaseSqliteAdapter 
							   implements DatabaseAdpater<Trip>
{
	/**
	 * Name of the table.
	 */
	public final static String TABLE_TRIP = "Trip";
	/**
	 * Name of the id column.
	 */
	public final static String COLUMN_ID = "_id";
	/**
	 * Name of the place column.
	 */
	public final static String COLUMN_PLACE = "place";
	/**
	 * Name of the party column.
	 */
	public final static String COLUMN_PARTY = "party";
	/**
	 * Name of the placeScore column.
	 */
	public final static String COLUMN_PLACESCORE = "placescore";
	/**
	 * Name of the depravity column.
	 */
	public final static String COLUMN_DEPRAVITY = "depravity";
	/**
	 * Name of the begin date column.
	 */
	public final static String COLUMN_CREATEDAT = "createdat";
	/**
	 * Name of the ended date column.
	 */
	public final static String COLUMN_ENDEDAT = "endedat";
	/**
	 * Name of the place id column.
	 */
	public final static String COLUMN_PLACE_ID = "pid";
	/**
	 * List of the column.
	 */
	public final static String[] COLUM_LIST = {COLUMN_ID,
								 COLUMN_PLACE,
								 COLUMN_PARTY,
								 COLUMN_PLACESCORE,
								 COLUMN_DEPRAVITY,
								 COLUMN_CREATEDAT,
								 COLUMN_ENDEDAT};
	
	/**
	 * Schema of the table.
	 */
	public final static String SCHEMA = "CREATE TABLE " +
								TripSqliteAdapter.TABLE_TRIP + " ( " +
								TripSqliteAdapter.COLUMN_ID + 
								" integer primary key autoincrement, " +
								TripSqliteAdapter.COLUMN_PLACE + " integer, " +
								TripSqliteAdapter.COLUMN_PARTY + " integer, " +
								TripSqliteAdapter.COLUMN_PLACESCORE + " integer, " +
								TripSqliteAdapter.COLUMN_DEPRAVITY + " integer, " +
								TripSqliteAdapter.COLUMN_CREATEDAT + " NUMERIC not null, " +
								TripSqliteAdapter.COLUMN_ENDEDAT + " NUMERIC, " +
								"FOREIGN KEY(" + TripSqliteAdapter.COLUMN_PARTY + 
								") REFERENCES " + PartySqliteAdapter.TABLE_PARTY + 
								"(" + PartySqliteAdapter.COLUMN_ID +"), " + 
								"FOREIGN KEY(" + TripSqliteAdapter.COLUMN_PLACE +
								") REFERENCES " + PlaceSqliteAdapter.TABLE_PLACE +
								"(" + PlaceSqliteAdapter.COLUMN_ID + "));";
	
	/**
	 * Query to get trip with place.
	 */
	private final static String QUERYWITHPLACE = "SELECT t._id, t.placescore, t.place " +
						  "t.depravity, t.createdat, t.endedat, p._id pid, " + 
						  "p.name, p.longitude, p.latitude " +
						  "FROM Trip t " +
						  "LEFT JOIN Place p ON p._id = t.place " +
						  "WHERE t._id = ?";
	
	/**
	 * Query to get trip with the place by party.
	 */
	public final static String QUERYWITHPLACEBYPARTY = "SELECT t._id, t.placescore, " +
	                      "t.depravity, t.createdat, t.endedat, p._id pid, " +
			              "p.name, p.longitude, p.latitude " + 
	                      "FROM TRIP t " +
			              "LEFT JOIN Place p ON p._id = t.place " + 
	                      "WHERE t.party = ?";
	/**
	 * Query to get trip, which ended date is not null, with the place by party.
	 */
	public final static String QUERYWITHPLACEBYPARTYENDEDDATENOTNULL = "SELECT t._id, t.placescore, " +
            			  "t.depravity, t.createdat, t.endedat, p._id pid, " +
            			  "p.name, p.longitude, p.latitude " + 
            			  "FROM TRIP t " +
            			  "LEFT JOIN Place p ON p._id = t.place " + 
            			  "WHERE t.party = ? AND t.endedat IS NOT NULL " + 
            			  "ORDER BY t._id DESC";
	/**
	 * Query to get trip, which ended date is null, with the place by party.
	 */
	public final static String QUERYWITHPLACEBYPARTYENDEDDATENULL = "SELECT t._id, t.placescore, " +
			  			  "t.depravity, t.createdat, t.endedat, p._id pid, " +
			  			  "p.name, p.longitude, p.latitude " + 
			  			  "FROM TRIP t " +
			  			  "LEFT JOIN Place p ON p._id = t.place " + 
			  			  "WHERE t.party = ? AND t.endedat IS NULL";
	
	/**
	 * Constructor
	 * @param context
	 */
	public TripSqliteAdapter(Context context) 
	{
		super(context);
		//tripFixtures();
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
		values.put(COLUMN_CREATEDAT, trip.getCreatedAt());
		values.put(COLUMN_ENDEDAT, trip.getEndedAt());
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
		values.put(COLUMN_ENDEDAT, trip.getEndedAt());
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
		
		if(cursor.moveToFirst())
		{
			return cursorToItem(cursor);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Get a cursor to the trip.
	 * @param id
	 * @return Cursor
	 */
	public Cursor getCursor(long id) 
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
		return cursor;
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
		if(cursor.moveToFirst())
		{
			return cursorToItemWithPlace(cursor);
		}		
		else
		{
			return null;
		}
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
			while (!cursor.isAfterLast())
			{
				trips.add(cursorToItem(cursor));
				cursor.moveToNext();
			}
		}
		
		return trips;
	}
	
	/**
	 * Get a cursor to the trips.
	 * @param party
	 * @return Cursor
	 */
	public Cursor getByPartyCursor(Party party)
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
		cursor.moveToFirst();
		
		return cursor;
	}
	
	/**
	 * 
	 * @param party
	 * @return the list of the trips that belong to a party
	 */
	public ArrayList<Trip> getByPartyWithPlace(long party)
	{

		String selectionArgs[] = {String.valueOf(party)};
		Cursor cursor = this.getDb().rawQuery(QUERYWITHPLACEBYPARTY, selectionArgs);
		
		ArrayList<Trip> trips = new ArrayList<Trip>();
		if (cursor.moveToFirst())
		{
			while(!cursor.isAfterLast())
			{
				trips.add(cursorToItemWithPlace(cursor));
				cursor.moveToNext();
			}
		}
		
		return trips;
	}
	
	
	/**
	 * 
	 * @param party
	 * @return the list of the trips that belong to a party where the endedat
	 * field is not null
	 */
	public ArrayList<Trip> getByPartyWithPlaceEndedDataNotNull(long party)
	{

		String selectionArgs[] = {String.valueOf(party)};
		Cursor cursor = this.getDb().rawQuery(QUERYWITHPLACEBYPARTYENDEDDATENOTNULL,
				selectionArgs);
		
		ArrayList<Trip> trips = new ArrayList<Trip>();
		if (cursor.moveToFirst())
		{
			while(!cursor.isAfterLast())
			{
				trips.add(cursorToItemWithPlace(cursor));
				cursor.moveToNext();
			}
		}	
		
		return trips;
	}
	
	/**
	 * Get a cursor to the trips which ended date is null.
	 * @param party
	 * @return cursor
	 */
	public Cursor getByPartyEndedDataNotNullCursor(long party)
	{

		String selectionArgs[] = {String.valueOf(party)};
		Cursor cursor = this.getDb().rawQuery(QUERYWITHPLACEBYPARTYENDEDDATENOTNULL,
				selectionArgs);
		
		return cursor;
	}
	
	/**
	 * 
	 * @param party
	 * @return the trip that belong to a party where the endedat field is null
	 */
	public Trip getByPartyWithPlaceEndedDateNull(long party)
	{

		String selectionArgs[] = {String.valueOf(party)};
		Cursor cursor = this.getDb().rawQuery(QUERYWITHPLACEBYPARTYENDEDDATENULL,
				selectionArgs);
		if (cursor.moveToFirst())
		{
			return cursorToItemWithPlace(cursor);
		}
		else
		{
			return null;
		}
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
				trips.add(cursorToItem(cursor));
				cursor.moveToNext();
			}
		}
		
		return trips;
	}
	
	/**
	 * Get cursor for the trips.
	 * @return cursor
	 */
	public Cursor getAllCursor() 
	{
		Cursor cursor = this.getDb().query(TABLE_TRIP,
				COLUM_LIST,
				null,
				null,
				null,
				null,
				null);
		
		return cursor;
	}

	/**
	 * Convert a record into a trip object.
	 * @param cursor
	 * @return a Trip
	 */
	public static Trip cursorToItem(Cursor cursor) 
	{
		Trip trip = new Trip();
		
			trip.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
			trip.setPlaceScore(cursor.getInt(cursor.getColumnIndex(COLUMN_PLACESCORE)));
			trip.setDepravity(cursor.getInt(cursor.getColumnIndex(COLUMN_DEPRAVITY)));
			trip.setCreatedAt(cursor.getString(cursor
					.getColumnIndex(COLUMN_CREATEDAT)));
			trip.setEndedAt(cursor.getString(cursor
					.getColumnIndex(COLUMN_ENDEDAT)));
		return trip;
	}
	
	/**
	 * Convert a record to a Trip with its Place.
	 * @param cursor
	 * @return a Trip with its Place.
	 */
	public static Trip cursorToItemWithPlace(Cursor cursor)
	{
		Trip trip = cursorToItem(cursor);
		
		if (trip != null)
		{
			Place place = new Place();
			place.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_PLACE_ID)));
			place.setName(cursor.getString(cursor.getColumnIndex(
					PlaceSqliteAdapter.COLUMN_NAME)));
			place.setLongitude(cursor.getDouble(
					cursor.getColumnIndex(PlaceSqliteAdapter.COLUMN_LONGITUDE)));
			place.setLatitude(cursor.getDouble(
					cursor.getColumnIndex(PlaceSqliteAdapter.COLUMN_LATITUDE)));
			
			trip.setPlace(place);
		}
		return trip;
	}

	/**
	 * Fixtures - for test only.
	 */
	public void tripFixtures()
	{
		Integer k = 0;
		Trip trip = new Trip();
		Place place = new Place();
		Party party = new Party();
	    open();
		for	(int i=0; i<5; i++)
		{
			trip.setId(i+1);
			place.setId(i+1);
			party.setId(i+1);
			k = i;
			trip.setDepravity(3);
			if (party.getId() != 1)
			{
				trip.setEndedAt(DateTimeTools.getDateTime());
			}
			trip.setPlaceScore(k);
			trip.setPlace(place);
			trip.setParty(party);;
			trip.setCreatedAt(DateTimeTools.getDateTime());
			this.create(trip);
		}
	}
}
