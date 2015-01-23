package com.blackout.mydrunkendiaries;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public PartySqliteAdapter getPartySqliteAdapter()
    {
    	return this.partySqlAdapter;
    }
    
    public PartyListAdapter getPartyListAdapter()
    {
    	return this.adapter;
    }
    
    public void setPartySqliteAdapter(PartySqliteAdapter partySqliteAdapter)
    {
    	this.partySqlAdapter = partySqliteAdapter;
    }
    
    public void setPartyListAdapter(PartyListAdapter adapter)
    {
    	this.adapter = adapter;
    }
    
    public List<Party> getParties()
    {
    	return this.parties;
    }
    
    public void setParties(List<Party> parties)
    {
    	this.parties = parties;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
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
            MainActivity activity = (MainActivity) this.getActivity();
            
            activity.setPartySqliteAdapter(new PartySqliteAdapter(activity));
            activity.getPartySqliteAdapter().open();
            activity.setParties(activity.getPartySqliteAdapter().getAll());
            ListView list = (ListView) activity.findViewById(R.id.listView);
            if (!activity.getParties().isEmpty())
            	{
    	        	activity.setPartyListAdapter(new PartyListAdapter(activity, 
    	        			 activity.getParties()));
    	        	list.setAdapter(activity.getPartyListAdapter());
    	        	activity.getPartyListAdapter().notifyDataSetChanged();
            	}
        }    
    }
}
