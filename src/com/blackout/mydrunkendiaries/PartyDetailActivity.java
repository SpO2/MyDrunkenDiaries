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
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.blackout.mydrunkendiaries.adapter.PlacesListAdapter;
import com.blackout.mydrunkendiaries.data.PlaceSqliteAdapter;
import com.blackout.mydrunkendiaries.data.TripSqliteAdapter;
import com.blackout.mydrunkendiaries.entites.Trip;

public class PartyDetailActivity extends Activity {
	
	private PlacesFragment placesfragment;
	private MapsFragment mapsfragment;
	private PlacesListAdapter placesListAdapater;
	private TripSqliteAdapter tripSqliteAdapter;
	private ArrayList<Trip> trips;
	private Long currentPartyId;
	

	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_detail);
        if (savedInstanceState == null) 
        {
        	FragmentManager fragmentManager = getFragmentManager();
        	FragmentTransaction fragmentTransaction = fragmentManager
        			                                   .beginTransaction();
        	placesfragment = new PlacesFragment();
        	mapsfragment = new MapsFragment();
        	fragmentTransaction.add(R.id.pager, placesfragment);
        	fragmentTransaction.add(R.id.pager, mapsfragment);
        	fragmentTransaction.commit();
        } 
        final ActionBar actionBar = this.getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.addTab(actionBar.newTab().setText(R.string.tab_party_detail_places)
        		 .setTabListener(new TabListener<PlacesFragment>(this, "places",
        				              PlacesFragment.class)));
        actionBar.addTab(actionBar.newTab().setText(R.string.tab_party_detail_maps)
       		 .setTabListener(new TabListener<MapsFragment>(this, "maps",
       				              MapsFragment.class)));
        actionBar.selectTab(actionBar.getTabAt(0));
        this.setCurrentPartyId(this.getIntent().getLongExtra("CurrentParty", 0));
	}
	
	/**
	 * 
	 * @return the adapter for the places listview
	 */
	public PlacesListAdapter getPlacesListAdapter()
	{
		return this.placesListAdapater;
	}
	
	/**
	 * Set the adapter for the places listview
	 * @param placesListAdapter
	 */
	public void setPlacesListAdapter(PlacesListAdapter placesListAdapter)
	{
		this.placesListAdapater = placesListAdapter;
	}
	
	/**
	 * 
	 * @return the sqlite adapter for the trip
	 */
	public TripSqliteAdapter getTripSqliteAdapter()
	{
		return this.tripSqliteAdapter;
	}
	
	/**
	 * Set the sqlite adapter for the trip
	 * @param tripSqliteAdapter
	 */
	public void setTripSqliteAdapter(TripSqliteAdapter tripSqliteAdapter)
	{
		this.tripSqliteAdapter = tripSqliteAdapter;
	}
	
	/**
	 * 
	 * @return the current trip item list
	 */
	public ArrayList<Trip> getTrips()
	{
		return this.trips;
	}
	
	/**
	 * Set the current trip item list
	 * @param trips
	 */
	public void setTrips(ArrayList<Trip> trips)
	{
		this.trips = trips;
	}
	
	/**
	 * 
	 * @return the current party Id
	 */
	public Long getCurrentPartyId()
	{
		return this.currentPartyId;
	}
	
	/**
	 * Set the current party Id
	 * @param currentPartyId
	 */
	public void setCurrentPartyId(Long currentPartyId)
	{
		this.currentPartyId = currentPartyId;
	}

	public static class TabListener<T extends Fragment> implements ActionBar.TabListener 
	{
	    private Fragment mFragment;
	    private final Activity mActivity;
	    private final String mTag;
	    private final Class<T> mClass;

	    /** Constructor used each time a new tab is created.
	      * @param activity  The host Activity, used to instantiate the fragment
	      * @param tag  The identifier tag for the fragment
	      * @param clz  The fragment's Class, used to instantiate the fragment
	      */
	    public TabListener(Activity activity, String tag, Class<T> clz) 
	    {
	        mActivity = activity;
	        mTag = tag;
	        mClass = clz;
	    }

	    /* The following are each of the ActionBar.TabListener callbacks */

	    public void onTabSelected(Tab tab, FragmentTransaction ft) 
	    {
	        // Check if the fragment is already initialized
	        if (mFragment == null) 
	        {
	            // If not, instantiate and add it to the activity
	            mFragment = Fragment.instantiate(mActivity, mClass.getName());
	            ft.add(android.R.id.content, mFragment, mTag);
	        } 
	        else 
	        {
	            // If it exists, simply attach it in order to show it
	            ft.attach(mFragment);
	        }
	    }

	    public void onTabUnselected(Tab tab, FragmentTransaction ft) 
	    {
	        if (mFragment != null) 
	        {
	            // Detach the fragment, because another one is being attached
	            ft.detach(mFragment);
	        }
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft) 
	    {
	        // User selected the already selected tab. Usually do nothing.
	    }
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlacesFragment extends Fragment 
	{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private PartyDetailActivity partyDetailActivity;
		private ListView lv;
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlacesFragment newInstance(int sectionNumber) 
		{
			PlacesFragment fragment = new PlacesFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlacesFragment() 
		{
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.fragment_party_detail,
					container, false);
			lv = (ListView) rootView.findViewById(R.id.placeslistview);
			return rootView;
		}
		
		@Override
        public void onActivityCreated(Bundle savedInstanceState) 
        {
            super.onActivityCreated(savedInstanceState);
            this.partyDetailActivity = (PartyDetailActivity) this.getActivity();
            if ((this.partyDetailActivity != null) && 
                (this.partyDetailActivity instanceof PartyDetailActivity))
            {
            	PlaceSqliteAdapter placetestAdapter = new PlaceSqliteAdapter(
            			partyDetailActivity);
            	
            	this.partyDetailActivity.setTripSqliteAdapter(
            			new TripSqliteAdapter(this.partyDetailActivity));
            	this.partyDetailActivity.getTripSqliteAdapter().open();
                this.partyDetailActivity.setTrips(this.partyDetailActivity
        		.getTripSqliteAdapter().getByPartyWithPlace(
        				this.partyDetailActivity.getCurrentPartyId()));
                if (!this.partyDetailActivity.getTrips().isEmpty() && 
                		(this.lv != null))
            	{
                	this.partyDetailActivity.setPlacesListAdapter(
                			new PlacesListAdapter(this.partyDetailActivity,
                					this.partyDetailActivity.getTrips()));
                	this.lv.setAdapter(
                			this.partyDetailActivity.getPlacesListAdapter());
                	this.partyDetailActivity.getPlacesListAdapter()
                			.notifyDataSetChanged();
            	}
            			
            }
            
        }
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class MapsFragment extends Fragment 
	{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static MapsFragment newInstance(int sectionNumber) 
		{
			MapsFragment fragment = new MapsFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public MapsFragment() 
		{
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.fragment_map,
					container, false);
			return rootView;
		}
	}

}
