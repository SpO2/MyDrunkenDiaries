/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Party Adapter.                             *   
 *                                                      *   
 * Usage: Class that controls the interactions with the *
 * table party of the Database.                         *   
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

/**
 * @author spo2
 *
 */
public class PartySqliteAdapter extends BaseSqliteAdapter implements DatabaseAdpater<Party> 
{

	public final static String TABLE_PARTY = "Party";
	public final static String COLUMN_ID = "_id";
	public final static String COLUMN_NAME = "Name";
	public final static String COLUMN_CREATEDAT = "createdAt";
	public final static String COLUMN_ENDEDAT = "endedAt";
	public final static String[] COLUMN_LIST = {COLUMN_ID,
								 COLUMN_NAME,
								 COLUMN_CREATEDAT,
								 COLUMN_ENDEDAT};
	
	public final static String SCHEMA = "CREATE TABLE " + 
										PartySqliteAdapter.TABLE_PARTY +
										" ( " + PartySqliteAdapter.COLUMN_ID +
										" integer primary key autoincrement, " +
										PartySqliteAdapter.COLUMN_NAME + 
										" text not null, " + 
										PartySqliteAdapter.COLUMN_CREATEDAT + 
										"text, " +
										PartySqliteAdapter.COLUMN_ENDEDAT + 
										"text)";
			
	/**
	 * Constructor
	 * @param context
	 */
	public PartySqliteAdapter(Context context) 
	{
		super(context);
	}
	
	/**
	 * Add a new party in database.
	 * @param party
	 */
	public long create(Party party)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, party.getName());
		values.put(COLUMN_CREATEDAT, party.getCreatedAt().toString());
		values.put(COLUMN_ENDEDAT, party.getCreatedAt().toString());
		
		return this.getDb().insert(TABLE_PARTY, null, values);
	}
	
	/**
	 * Update a party in the database.
	 * @param party
	 * @return 
	 */
	public int update(Party party)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, party.getName());
		values.put(COLUMN_ENDEDAT, party.getEndedAt().toString());
		
		String whereClause = COLUMN_ID + " = ?";
		String whereArgs[] = {String.valueOf(party.getId())};
		
		return this.getDb().update(TABLE_PARTY, values, whereClause, whereArgs);
	}
	
	/**
	 * Delete a party in the database.
	 * @param party
	 * @return 
	 */
	public int delete(Party party)
	{
		String whereClause = COLUMN_ID + " = ?";
		String whereArgs[] = {String.valueOf(party.getId())};
		
		return this.getDb().delete(TABLE_PARTY, whereClause, whereArgs);
	}
	
	/**
	 * Find a Party from the database.
	 * @param id
	 * @return a Party.
	 */
	@Override
	public Party get(long id)
	{
		String[] selectionArgs = {String.valueOf(id)};
		
		String selection = COLUMN_ID + " = ?";
		
		Cursor cursor = this.getDb().query(TABLE_PARTY,
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
	 * Fetch all the parties from the database.
	 * @return Array of party.
	 */
	@Override
	public ArrayList<Party> getAll()
	{		
		Cursor cursor = this.getDb().query(TABLE_PARTY,
				COLUMN_LIST,
				null,
				null,
				null,
				null,
				null);
	    ArrayList<Party> parties = new ArrayList<Party>();
	    if (cursor.moveToFirst())
	    {
	    	while (!cursor.isAfterLast())
	    	{
	    		parties.add(this.cursorToItem(cursor));
	    	}
	    }
	    return parties;
	}
	
	/**
	 * Convert a record into a Party object.
	 * @param cursor
	 * @return a Party.
	 */
    @Override
	public Party cursorToItem(Cursor cursor)
	{
		Party party = new Party();
		party.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
		party.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dt = formatter.parseDateTime(cursor.getString(cursor
				 .getColumnIndex(COLUMN_CREATEDAT)));
		party.setCreatedAt(dt);
		dt = formatter.parseDateTime(cursor.getString(cursor
		.getColumnIndex(COLUMN_ENDEDAT)));
		party.setEndedAt(dt);
		
		return party;
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