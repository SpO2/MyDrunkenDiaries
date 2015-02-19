/**
 * 
 */
package com.blackout.mydrunkendiaries.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackout.mydrunkendiaries.R;
import com.blackout.mydrunkendiaries.data.PartySqliteAdapter;

/**
 * @author spo2
 *
 */
public class PartyCursorAdapter extends CursorAdapter 
{

	public PartyCursorAdapter(Context context, Cursor c) 
	{
		super(context, c, 0);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View v, Context context, Cursor cursor) 
	{
		TextView name = (TextView) v.findViewById(R.id.name);
		TextView createdAt = (TextView) v.findViewById(R.id.createdAt);
		TextView endedAt = (TextView) v.findViewById(R.id.endedAt);	

		name.setText(cursor.getString(cursor.getColumnIndex(PartySqliteAdapter.COLUMN_NAME)));
		v.setTag(cursor.getLong(cursor.getColumnIndex(PartySqliteAdapter.COLUMN_ID)));
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
		try 
		{
			Date dt = sdf.parse(cursor.getString(cursor
					.getColumnIndex(PartySqliteAdapter.COLUMN_CREATEDAT)));
			createdAt.setText(sdf.format(dt));
			dt = sdf.parse(cursor.getString(cursor
					.getColumnIndex(PartySqliteAdapter.COLUMN_ENDEDAT)));
			endedAt.setText(sdf.format(dt));
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}	
	}

	/* (non-Javadoc)
	 * @see android.support.v4.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context context, Cursor arg1, ViewGroup parent) {
		return LayoutInflater.from(context).inflate(R.layout.party_list_row, parent, false);
	}

}
