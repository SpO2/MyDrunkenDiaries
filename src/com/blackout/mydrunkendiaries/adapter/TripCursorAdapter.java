/**
 * 
 */
package com.blackout.mydrunkendiaries.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.blackout.mydrunkendiaries.R;
import com.blackout.mydrunkendiaries.TripGalleryActivity;
import com.blackout.mydrunkendiaries.data.TripSqliteAdapter;
import com.blackout.mydrunkendiaries.entites.Trip;
import com.blackout.mydrunkendiaries.tools.DateTimeTools;

/**
 * Adapter for the trip list (party detail)
 * @author romain
 *
 */
public class TripCursorAdapter extends CursorAdapter {

	/**
	 * The count of selected items.
	 */
	private Integer selectedCount;
	/**
	 * Constructor of the trip adapter.
	 * @param context (activity)
	 * @param c the cursor 
	 */
	public TripCursorAdapter(Context context, Cursor c) 
	{
		super(context, c, 0);
		this.selectedCount = 0;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.widget.CursorAdapter#bindView(android.view.View, android.content.Context, android.database.Cursor)
	 */
	@Override
	public void bindView(final View v, Context context, Cursor cursor) {
		TextView name = (TextView) v.findViewById(R.id.title);
		TextView time = (TextView) v.findViewById(R.id.hours);
		RatingBar beerBar = (RatingBar) v.findViewById(R.id.beerbar);
		Trip trip = TripSqliteAdapter.cursorToItemWithPlace(cursor);
		CheckBox checkBox = (CheckBox) v.findViewById(R.id.check_multiple);
		
		ImageView btnGallery = (ImageView) v.findViewById(R.id.gallery);
		btnGallery.setTag(Long.valueOf(trip.getId()));
		btnGallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				long tripId = (Long)((ImageView) v).getTag();
				openGallery(v.getContext(),tripId);
			}
		});
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				v.setSelected(isChecked);
				if (isChecked){
					selectedCount++;
				}else{
					if (selectedCount >0){
						selectedCount--;
					}
				}
			}
		});
		if (trip.getDepravity() != null){
			beerBar.setRating(trip.getDepravity());
		}
		beerBar.setIsIndicator(true);
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
	/**
	 * @param long tripId Id of the selected trip
	 * Open new gallery by id
	 */
	private void openGallery(Context context,long tripId)
	{
		Intent intent = new Intent(context,TripGalleryActivity.class);
		intent.putExtra("tripId",tripId);
		context.startActivity(intent);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.widget.CursorAdapter#newView(android.content.Context, android.database.Cursor, android.view.ViewGroup)
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		
		return LayoutInflater.from(context).inflate(R.layout.places_list_row, parent, false);
	}

}
