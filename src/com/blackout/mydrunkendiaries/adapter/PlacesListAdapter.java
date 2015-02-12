/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Adapter for the Places list.               *   
 *                                                      *   
 * Usage:                                               *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blackout.mydrunkendiaries.R;
import com.blackout.mydrunkendiaries.entites.Trip;
import com.blackout.mydrunkendiaries.tools.DateTimeTools;

/**
 * @author spo2
 *
 */
public class PlacesListAdapter extends BaseAdapter 
{

	private Activity activity;
    private LayoutInflater inflater;
    private List<Trip> tripItems;
	
    public PlacesListAdapter(Activity activity, List<Trip> tripItems) 
    {
    	this.activity = activity;
    	this.tripItems = tripItems;
	}
    
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() 
	{
		return this.tripItems.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) 
	{
		return this.tripItems.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = convertView;
		if (v == null)
		{
			v = inflater.inflate(R.layout.places_list_row,null);	
		}
		Trip trip = tripItems.get(position);
		if (trip != null)
		{
			TextView name = (TextView) v.findViewById(R.id.title);
			TextView time = (TextView) v.findViewById(R.id.hours);
			name.setText(trip.getPlace().getName());
			String createdAt = DateTimeTools.getTimeFromString(trip
					.getCreatedAt());
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
		return v;
	}

}
