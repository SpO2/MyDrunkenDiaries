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

/**
 * @author spo2
 *
 */
public class PartySqliteAdapter 
{
	public final static String TABLE_PARTY = "Party";
	public final static String COLUMN_ID = "_id";
	public final static String COLUMN_NAME = "Name";
	
	public final static String SCHEMA = "CREATE TABLE " + 
										PartySqliteAdapter.TABLE_PARTY +
										" ( " + PartySqliteAdapter.COLUMN_ID +
										" integer primary key autoincrement, " +
										PartySqliteAdapter.COLUMN_NAME + 
										" text not null)";
	
	private OpenHelperSqlite helper;
										
}