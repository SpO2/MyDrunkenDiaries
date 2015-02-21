/**
 * 
 */
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
import com.blackout.mydrunkendiaries.services.Weather;

/**
 * Adapter for the city list
 * @author romain
 *
 */
@SuppressLint("InflateParams")
public class CityListAdapter extends BaseAdapter 
{
	/**
	 * Current activity context.
	 */
	private Activity activity;
	/**
	 * Inflater for the view.
	 */
	private LayoutInflater inflater;
	/**
	 * List of cities returned by the webservice.
	 */
	private List<Weather> weathers;
	
	/**
	 * Constructor.
	 * @param activity Context.
	 * @param weathers List of the cities returned by the web service.
	 */
	public CityListAdapter(Activity activity, List<Weather> weathers)
	{
		this.activity = activity;
		this.weathers = weathers;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() 
	{
		return weathers.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int location) 
	{
		return weathers.get(location);
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
			v = inflater.inflate(R.layout.row_city, null);
		}
		
		Weather weather = weathers.get(position);
		if(weather != null)
		{
			TextView cityResult = (TextView) v.findViewById(R.id.city_result);
			TextView countryResult = (TextView) v.findViewById(R.id.country_result);
			
			cityResult.setText(weather.getName());
			countryResult.setText(weather.getSys().getCountry());
		}
		
		return v;
	}

}
