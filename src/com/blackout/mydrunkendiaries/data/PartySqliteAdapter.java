/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Party Adapter.                             *   
 *                                                      *   
 * Usage: Class that controls the interactions with the *
 * table party from the Database.                       *   
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
import android.database.sqlite.SQLiteDatabase;

import com.blackout.mydrunkendiaries.entites.Party;

/**
 * @author spo2
 *
 */
public class PartySqliteAdapter 
{
	public final static String TABLE_PARTY = "Party";
	public final static String COLUMN_ID = "_id";
	public final static String COLUMN_NAME = "Name";
	public final static String COLUMN_CREATEDAT = "createdAt";
	public final static String COLUMN_ENDEDAT = "endedAt";
	
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
	
	private OpenHelperSqlite helper;
	private SQLiteDatabase db;
	
	/**
	 * Constructor
	 * @param context
	 */
	public PartySqliteAdapter(Context context)
	{
		this.helper = new OpenHelperSqlite(context);
	}
			
	/**
	 * Open the database.
	 */
	public void open()
	{
		this.db = this.helper.getWritableDatabase();
	}
	
	/**
	 * Close the database.
	 */
	public void close()
	{
		this.helper.close();
	}
	
	/**
	 * Return a Party from the database.
	 * @param id
	 * @return a Party.
	 */
	public Party getParty(long id)
	{
		String[] columns = {COLUMN_ID,
				 COLUMN_NAME,
				 COLUMN_CREATEDAT,
				 COLUMN_ENDEDAT};
		String[] selectionArgs = {String.valueOf(id)};
		
		String selection = COLUMN_ID + " = ?";
		
		Cursor cursor = this.db.query(TABLE_PARTY,
				columns,
				selection,
				selectionArgs,
				null,
				null,
				null);
		cursor.moveToFirst();
		
		return this.cursorToItem(cursor);
				
		
	}
	
	/**
	 * Take all the parties from the database.
	 * @return Array of party.
	 */
	public ArrayList<Party> getAllParty()
	{
		String[] columns = {COLUMN_ID,
				 COLUMN_NAME,
				 COLUMN_CREATEDAT,
				 COLUMN_ENDEDAT};
		
		Cursor cursor = this.db.query(TABLE_PARTY,
				columns,
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
	
	public long create(Party party)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, party.getName());
		values.put(COLUMN_CREATEDAT, party.getCreatedAt().toString());
		values.put(COLUMN_ENDEDAT, party.getCreatedAt().toString());
		
		return this.db.insert(TABLE_PARTY, null, values);
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
		
		return this.db.update(TABLE_PARTY, values, whereClause, whereArgs);
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
		
		return this.db.delete(TABLE_PARTY, whereClause, whereArgs);
	}
	
	/**
	 * Convert a record into a Party object.
	 * @param cursor
	 * @return a Party.
	 */
	private Party cursorToItem(Cursor cursor)
	{
		Party party = new Party();
		party.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
		party.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dt = formatter.parseDateTime(cursor.getString(cursor.getColumnIndex(COLUMN_CREATEDAT)));
		party.setCreatedAt(dt);
		dt = formatter.parseDateTime(cursor.getString(cursor.getColumnIndex(COLUMN_ENDEDAT)));
		party.setEndedAt(dt);
		
		return party;
	}
}