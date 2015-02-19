/**
 * 
 */
package com.blackout.mydrunkendiaries.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackout.mydrunkendiaries.R;
import com.blackout.mydrunkendiaries.data.TripSqliteAdapter;
import com.blackout.mydrunkendiaries.entites.Trip;
import com.blackout.mydrunkendiaries.tools.DateTimeTools;

/**
 * @author spo2
 *
 */
public class TripCursorAdapter extends CursorAdapter {

	public TripCursorAdapter(Context context, Cursor c) 
	{
		super(context, c, 0);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(View v, Context context, Cursor cursor) {
		TextView name = (TextView) v.findViewById(R.id.title);
		TextView time = (TextView) v.findViewById(R.id.hours);
		Trip trip = TripSqliteAdapter.cursorToItemWithPlace(cursor);
		name.setText(trip.getPlace().getName());
		String createdAt = DateTimeTools.getTimeFromString(trip
				.getCreatedAt());
		v.setTag(trip.getId());
		if (trip.getEndedAt() != "")
		{
			String endedAt = DateTimeTools.getTimeFromString(
					trip.getEndedAt());
			time.setText(createdAt + " - " + endedAt);
		}
		else
		{
			time.setText(createdAt);
		}
	}

	/* (non-Javadoc)
	 * @see android.support.v4.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		
		return LayoutInflater.from(context).inflate(R.layout.places_list_row, parent, false);
	}

}
