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
 * @author spo2
 *
 */
public abstract class BaseSqliteAdapter 
{
	private OpenHelperSqlite helper;
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
