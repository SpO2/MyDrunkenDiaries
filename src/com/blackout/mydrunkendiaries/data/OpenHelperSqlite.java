/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Helper SQlite.                             *   
 *                                                      *   
 * Usage: Class that controls the interactions with the *
 * Database.                                            *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author spo2
 *
 */
public class OpenHelperSqlite extends SQLiteOpenHelper 
{
	public final static String DBNAME = "Database.sqlite";
	public final static int VERSION = 1;
	
	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public OpenHelperSqlite(Context context, String name,
			CursorFactory factory, int version) 
	{
		super(context, DBNAME, null, version);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 * @param errorHandler
	 */
	public OpenHelperSqlite(Context context, String name,
			CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) 
	{
		super(context, DBNAME, null, version, errorHandler);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param context
	 */
	public OpenHelperSqlite(Context context)
	{
		super(context, DBNAME, null, VERSION);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(PartySqliteAdapter.SCHEMA);
		db.execSQL(TripMediaSqliteAdapter.SCHEMA);
		db.execSQL(PlaceSqliteAdapter.SCHEMA);
		db.execSQL(TripSqliteAdapter.SCHEMA);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) 
	{
		// TODO Auto-generated method stub

	}

}
