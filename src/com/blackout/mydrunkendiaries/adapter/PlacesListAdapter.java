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

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blackout.mydrunkendiaries.R;
import com.blackout.mydrunkendiaries.entites.Trip;

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
			TextView hours = (TextView) v.findViewById(R.id.hours);
			name.setText(trip.getPlace().getName());
			/*SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
			
			Date dt;
			try 
			{
				dt = sdf.parse(trip.getCreatedAt());
				String createdAt = sdf.format(dt);
				dt = sdf.parse(trip.getEndedAt());
				String endedAt = sdf.format(dt);
				if ((createdAt != "") && (endedAt != ""))
				{
					hours.setText(createdAt + " - " + endedAt);
				}
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}*/
 		}
		return v;
	}

}
