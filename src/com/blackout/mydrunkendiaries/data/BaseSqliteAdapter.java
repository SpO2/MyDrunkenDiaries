/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Class for SQL Adapters.                    *   
 *                                                      *   
 * Usage: Class that contains standard functions to use *   
 * in the sql adapter.                                  *   
 ********************************************************/
package com.blackout.mydrunkendiaries.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Contains tools for entities to work with the sqlite database
 * @author romain
 *
 */
public abstract class BaseSqliteAdapter 
{
	/**
	 * Helper to connect to the database.
	 */
	private OpenHelperSqlite helper;
	/**
	 * Sqlite database.
	 */
	private SQLiteDatabase db;
	
	/**
	 * Constructor.
	 * @param context
	 */
	public BaseSqliteAdapter(Context context)
	{
		this.helper = new OpenHelperSqlite(context);
	}
	
	/**
	 * Open the Database.
	 */
	public void open()
	{
		this.db = this.helper.getWritableDatabase();
	}
	
	/**
	 * Close the Database.
	 */
	public void close()
	{
		this.helper.close();
	}

	/**
	 * @return the helper
	 */
	public OpenHelperSqlite getHelper() 
	{
		return helper;
	}

	/**
	 * @param helper the helper to set
	 */
	public void setHelper(OpenHelperSqlite helper) 
	{
		this.helper = helper;
	}

	/**
	 * @return the db
	 */
	public SQLiteDatabase getDb() 
	{
		return db;
	}

	/**
	 * @param db the db to set
	 */
	public void setDb(SQLiteDatabase db) 
	{
		this.db = db;
	}

}
