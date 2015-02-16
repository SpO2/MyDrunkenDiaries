/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Activity                                   *   
 *                                                      *   
 * Usage: Show the detail informations for the parties. *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.blackout.mydrunkendiaries.adapter.PlacesListAdapter;
import com.blackout.mydrunkendiaries.data.PartySqliteAdapter;
import com.blackout.mydrunkendiaries.data.PlaceSqliteAdapter;
import com.blackout.mydrunkendiaries.data.TripSqliteAdapter;
import com.blackout.mydrunkendiaries.entites.Party;
import com.blackout.mydrunkendiaries.entites.Place;
import com.blackout.mydrunkendiaries.entites.Trip;
import com.blackout.mydrunkendiaries.externalfragment.NewPlaceDialogFragment;
import com.blackout.mydrunkendiaries.externalfragment.NewPlaceDialogFragment.NewPlaceDialogListener;
import com.blackout.mydrunkendiaries.tools.DateTimeTools;

public class PartyDetailActivity extends Activity 
								 implements NewPlaceDialogListener, ActionBar.TabListener{
	
	private PlacesListAdapter placesListAdapater;
	private TripSqliteAdapter tripSqliteAdapter;
	private ArrayList<Trip> trips;
	private Long currentPartyId;
	private Party currentParty;
	private Trip tripInProgress;	
	
	private ListView lv;
	private TextView lastActivity;
	private TextView partyBegin;
	private RatingBar beerBar;

	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_detail);
        lv = (ListView) this.findViewById(R.id.placeslistview);
		lastActivity = (TextView) this.findViewById(R.id.last_activity);
		partyBegin = (TextView) this.findViewById(R.id.party_begin);
		beerBar = (RatingBar) this.findViewById(R.id.beerbar);
        final ActionBar actionBar = this.getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.addTab(actionBar.newTab().setText(R.string.tab_party_detail_places)
        		 .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.tab_party_detail_maps)
       		 .setTabListener(this));
        actionBar.selectTab(actionBar.getTabAt(0));
        this.currentPartyId = this.getIntent().getLongExtra("CurrentParty", 0);
        this.setCurrentParty(this.currentPartyId);
        refreshEnv();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.party_detail, menu);
        return true;
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
	        case R.id.new_trip:
	        {
	        	NewPlaceDialogFragment newPlaceDialogFragment = new NewPlaceDialogFragment();
	        	newPlaceDialogFragment.show(getFragmentManager(),
	        			"NewPlaceDialogFragment");
	        }
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog)
	{
		if (this.getCurrentParty() != null)
		{
			Place place = new Place();
			NewPlaceDialogFragment newPlaceDialogFragment = 
					(NewPlaceDialogFragment) dialog;
			place.setName(newPlaceDialogFragment.getPlaceName().getText().toString());
			PlaceSqliteAdapter placeSqliteAdapter = 
					new PlaceSqliteAdapter(PartyDetailActivity.this);
			placeSqliteAdapter.open();
			Long newPlace = placeSqliteAdapter.create(place);
			place = placeSqliteAdapter.get(newPlace);
			placeSqliteAdapter.close();
			
			Trip trip = new Trip();		
			trip.setPlace(place);
			trip.setParty(this.getCurrentParty());
			trip.setCreatedAt(DateTimeTools.getDateTime());
			TripSqliteAdapter tripSqliteAdapter = 
					new TripSqliteAdapter(PartyDetailActivity.this);
			tripSqliteAdapter.open();
			Long newTrip = tripSqliteAdapter.create(trip);
			trip = tripSqliteAdapter.get(newTrip);
			tripSqliteAdapter.close();
			PartyDetailActivity.this.tripInProgress = trip;
			this.recreate();
			dialog.dismiss();
		}
	}
	
	/**
	 * Reset global environement
	 */
	public void refreshEnv()
	{
       	this.tripSqliteAdapter  = new TripSqliteAdapter(this);
       	this.tripSqliteAdapter.open();
       	this.tripInProgress = this.tripSqliteAdapter
       			.getByPartyWithPlaceEndedDateNull(this.currentPartyId);
       	if (this.tripInProgress != null)
       	{
       		lastActivity.setText(this.tripInProgress.getPlace().getName());
       	    partyBegin.setText(DateTimeTools.getTimeFromString(this.tripInProgress
       	    		.getCreatedAt()));
       	    beerBar.setRating(this.tripInProgress.getDepravity());    
       	}
       	this.tripSqliteAdapter.close();
       	this.tripSqliteAdapter.open();
       	this.trips = this.tripSqliteAdapter
        		.getByPartyWithPlaceEndedDataNotNull(this.currentPartyId);
        if (!this.trips.isEmpty() && (this.lv != null))
        {
        	this.placesListAdapater = new PlacesListAdapter(this, this.trips);
           	this.lv.setAdapter(this.placesListAdapater);
           	this.placesListAdapater.notifyDataSetChanged();
        }   			
	}
	
	@Override
    public void onPostResume()
    {
    	super.onPostResume();
    	//refreshEnv();
    }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) 
	{
		// TODO Auto-generated method stub
		if (tab.getPosition() == 1)
		{
			Intent intent = new Intent(PartyDetailActivity.this, MapActivity.class);
			intent.putExtra("CurrentParty", this.currentPartyId);
			startActivity(intent);
			this.finish();
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void setCurrentParty(Long currentPartyId)
	{
		PartySqliteAdapter partySqliteAdapter = 
				new PartySqliteAdapter(this);
		partySqliteAdapter.open();
		this.currentParty = partySqliteAdapter.get(currentPartyId);
		partySqliteAdapter.close();
	}
	
	public Party getCurrentParty()
	{
		return this.currentParty;
	}
	
	@Override
	public void onDialogNegativeClick(DialogFragment dialog)
	{
		
	}

///*	public static class TabListener<T extends Fragment> implements ActionBar.TabListener 
//	{
//	    private Fragment mFragment;
//	    private final Activity mActivity;
//	    private final String mTag;
//	    private final Class<T> mClass;
//
//	    /** Constructor used each time a new tab is created.
//	      * @param activity  The host Activity, used to instantiate the fragment
//	      * @param tag  The identifier tag for the fragment
//	      * @param clz  The fragment's Class, used to instantiate the fragment
//	      */
//	    public TabListener(Activity activity, String tag, Class<T> clz) 
//	    {
//	        mActivity = activity;
//	        mTag = tag;
//	        mClass = clz;
//	    }
//
//	    /* The following are each of the ActionBar.TabListener callbacks */
//
//	    public void onTabSelected(Tab tab, FragmentTransaction ft) 
//	    {
//	        // Check if the fragment is already initialized
//	        if (mFragment == null) 
//	        {
//	            // If not, instantiate and add it to the activity
//	            mFragment = Fragment.instantiate(mActivity, mClass.getName());
//	            ft.add(android.R.id.content, mFragment, mTag);
//	        } 
//	        else 
//	        {
//	            // If it exists, simply attach it in order to show it
//	            ft.attach(mFragment);
//	        }
//	    }
//
//	    public void onTabUnselected(Tab tab, FragmentTransaction ft) 
//	    {
//	        if (mFragment != null) 
//	        {
//	            // Detach the fragment, because another one is being attached
//	            ft.detach(mFragment);
//	        }
//	    }
//
//	    public void onTabReselected(Tab tab, FragmentTransaction ft) 
//	    {
//	        // User selected the already selected tab. Usually do nothing.
//	    }
//	}

}
