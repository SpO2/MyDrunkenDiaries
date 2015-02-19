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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;

import com.blackout.mydrunkendiaries.entites.Party;
import com.blackout.mydrunkendiaries.tools.DateTimeTools;

/**
 * @author spo2
 *
 */
public class PartySqliteAdapter extends BaseSqliteAdapter implements DatabaseAdpater<Party> 
{

	public final static String TABLE_PARTY = "Party";
	public final static String COLUMN_ID = "_id";
	public final static String COLUMN_NAME = "name";
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
										" NUMERIC, " +
										PartySqliteAdapter.COLUMN_ENDEDAT + 
										" NUMERIC);";
			
	/**
	 * Constructor
	 * @param context
	 */
	public PartySqliteAdapter(Context context) 
	{
		super(context);
		//partyFixtures();
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
	    		cursor.moveToNext();
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
		party.setCreatedAt(cursor.getString(cursor
				.getColumnIndex(COLUMN_CREATEDAT)));
		party.setEndedAt(cursor.getString(cursor
				.getColumnIndex(COLUMN_ENDEDAT)));
		return party;
	}

	public void partyFixtures()
	{
		Party party = new Party();
		open();;
		for	(int i=0; i<10; i++)
		{
			party.setId(i+1);
			party.setName("party" + String.valueOf(i));	
			party.setCreatedAt(DateTimeTools.getDateTime());
			party.setEndedAt(DateTimeTools.getDateTime());
			this.create(party);
		}
		close();
	}
}