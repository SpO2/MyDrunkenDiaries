package com.blackout.mydrunkendiaries;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.blackout.mydrunkendiaries.adapter.PartyListAdapter;
import com.blackout.mydrunkendiaries.data.PartySqliteAdapter;
import com.blackout.mydrunkendiaries.entites.Party;



public class MainActivity extends Activity {

	private ListView listView;
	private List<Party> parties;
	private PartyListAdapter adapter;
	private PartySqliteAdapter partySqlAdapter;
	private PlaceholderFragment fragment;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) 
        {
        	FragmentManager fragmentManager = getFragmentManager();
        	FragmentTransaction fragmentTransaction = fragmentManager
        			                                   .beginTransaction();
        	fragment = new PlaceholderFragment();
        	fragmentTransaction.add(R.id.container, fragment);
        	fragmentTransaction.commit();
        }
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * 
     * @return the Sqlite adapter for the parties
     */
    public PartySqliteAdapter getPartySqliteAdapter()
    {
    	return this.partySqlAdapter;
    }
    
    /**
     * 
     * @return the list adapter for the parties
     */
    public PartyListAdapter getPartyListAdapter()
    {
    	return this.adapter;
    }
    
    /**
     * Set the Sqlite Adapter for the parties
     * @param partySqliteAdapter
     */
    public void setPartySqliteAdapter(PartySqliteAdapter partySqliteAdapter)
    {
    	this.partySqlAdapter = partySqliteAdapter;
    }
    
    /**
     * Set the list adapter for the parties
     * @param adapter 
     */
    public void setPartyListAdapter(PartyListAdapter adapter)
    {
    	this.adapter = adapter;
    }
    
    /**
     * 
     * @return List of the parties
     */
    public List<Party> getParties()
    {
    	return this.parties;
    }
    
    /**
     * Set the list of the parties
     * @param parties
     */
    public void setParties(List<Party> parties)
    {
    	this.parties = parties;
    }
    
    /**
     * 
     * @return the listView that contains the parties
     */
    public ListView getPartyListView()
    {
    	return this.listView;
    }
    
    /**
     * Set the listView for the parties
     * @param partyListView
     */
    public void setPartyListView(ListView partyListView)
    {
    	this.listView = partyListView;
    }
    
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
    	
    	private MainActivity activity;

        public PlaceholderFragment() 
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) 
        {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
        
        @Override
        public void onActivityCreated(Bundle savedInstanceState) 
        {
            super.onActivityCreated(savedInstanceState);
            activity = (MainActivity) this.getActivity();
            if ((activity != null) && (activity instanceof MainActivity))
            {
	            activity.setPartySqliteAdapter(new PartySqliteAdapter(activity));
	            activity.getPartySqliteAdapter().open();
	            activity.setParties(activity.getPartySqliteAdapter().getAll());
	            activity.setPartyListView((ListView) activity
	            		 .findViewById(R.id.listView));
	            
	            if ((!activity.getParties().isEmpty()) && 
	            	(activity.getPartyListView() != null))
            	{
    	        	activity.setPartyListAdapter(new PartyListAdapter(activity, 
    	        			 activity.getParties()));
    	        	activity.getPartyListView()
    	        	         .setAdapter(activity.getPartyListAdapter());
    	        	activity.getPartyListAdapter().notifyDataSetChanged();
    	        	activity.getPartyListView().setOnItemClickListener(
    	        			new OnItemClickListener() 
    	        			{
	    	        		   @Override
	    	        		   public void onItemClick(AdapterView<?> parent, 
	    	        				       View view,int position, long id) 
	    	        		   {
	    	        		        Party party = (Party) activity.getPartyListAdapter()
	    	        		        		.getItem(position);
	    	        		        if (party != null)
	    	        		        {
	    	        		        	Intent intent = new Intent(getActivity(), 
	    	        		        			PartyDetailActivity.class);
	    	        		        	intent.putExtra("CurrentParty", party.getId());
	    	        		        	startActivity(intent);
	    	        		        }
	    	        		   }
    	        		});
            	}
            }
        }
    }
}
