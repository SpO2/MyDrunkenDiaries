/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Adapter for the Parties List.              *   
 *                                                      *   
 * Usage:                                               *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.adapter;

import java.util.List;

import com.blackout.mydrunkendiaries.R;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blackout.mydrunkendiaries.entites.Party;

/**
 * @author spo2
 *
 */
public class PartyListAdapter extends BaseAdapter {

	private Activity activity;
    private LayoutInflater inflater;
    private List<Party> partyItems;
    
	public PartyListAdapter(Activity activity, List<Party> partyItems) 
	{
		this.activity = activity;
        this.partyItems = partyItems;	
	}
	
	/**
	 * @return the count of items
	 */
	@Override
	public int getCount() 
	{
		return partyItems.size();
	}

	/**
	 * @return the item at the location.
	 */
	@Override
	public Object getItem(int location) 
	{
		return partyItems.get(location);
	}

	/**
	 * @return the Index of the item.
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
		  v = inflater.inflate(R.layout.party_list_row,null);	
		}
		
		Party party = partyItems.get(position);
		if (party != null)
		{
			TextView name = (TextView) v.findViewById(R.id.name);
			TextView createdAt = (TextView) v.findViewById(R.id.createdAt);
			TextView endedAt = (TextView) v.findViewById(R.id.endedAt);	
			
			name.setText(party.getName());
			createdAt.setText(party.getCreatedAt().toString("dd-MM-yyyy"));
			endedAt.setText(party.getEndedAt().toString("dd-MM-yyyy"));
		}
		return v;
	}

}
