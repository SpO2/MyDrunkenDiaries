package com.blackout.mydrunkendiaries;

import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.blackout.mydrunkendiaries.adapter.PartyListAdapter;
import com.blackout.mydrunkendiaries.data.PartySqliteAdapter;
import com.blackout.mydrunkendiaries.entites.Party;
import com.blackout.mydrunkendiaries.externalfragment.DialogButtonClick;
import com.blackout.mydrunkendiaries.externalfragment.NewPartyDialogFragment;
import com.blackout.mydrunkendiaries.tools.DateTimeTools;



public class MainActivity extends Activity implements DialogButtonClick
{

	private ListView listView;
	private List<Party> parties;
	private PartyListAdapter adapter;
	private PartySqliteAdapter partySqlAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
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
        switch (id)
        {
	        case R.id.new_party:
	        {
	        	NewPartyDialogFragment newPartyDialogFragment = new NewPartyDialogFragment();
	        	newPartyDialogFragment.show(getFragmentManager(),
	        			"NewPartyDialogFragment");
	        }
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) 
    {
       Party party = new Party();
       NewPartyDialogFragment newPartyDialogFragment = 
    		   (NewPartyDialogFragment) dialog;
 	   party.setName(newPartyDialogFragment.getActivityName().getText().toString());
 	   party.setCreatedAt(DateTimeTools.getDateTime());
 	   PartySqliteAdapter partySqliteAdapter = 
 			   new PartySqliteAdapter(MainActivity.this);
 	   partySqliteAdapter.open();
 	   partySqliteAdapter.create(party);
 	   partySqliteAdapter.close();
 	   refreshEnv();
    }
    
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) 
    {
    	
    }
    
    /**
     * Called when activity resume is complete.
     */
    @Override
    public void onPostResume()
    {
    	super.onPostResume();
    	refreshEnv();
    }
    
    public void refreshEnv()
    {
    	this.setPartySqliteAdapter(new PartySqliteAdapter(this));
        this.getPartySqliteAdapter().open();
        this.setParties(this.getPartySqliteAdapter().getAll());
        this.setPartyListView((ListView) this.findViewById(R.id.listView));
        if ((!this.getParties().isEmpty()) && (this.getPartyListView() != null))
    	{
        	this.setPartyListAdapter(new PartyListAdapter(this, 
        			 this.getParties()));
        	this.getPartyListView()
        	         .setAdapter(this.getPartyListAdapter());
        	this.getPartyListAdapter().notifyDataSetChanged();
        	this.getPartyListView().setOnItemClickListener(
        			new OnItemClickListener() 
        			{
	        		   @Override
	        		   public void onItemClick(AdapterView<?> parent, 
	        				       View view,int position, long id) 
	        		   {
	        		        Party party = (Party) MainActivity.this
	        		        		.getPartyListAdapter().getItem(position);
	        		        if (party != null)
	        		        {
	        		        	Intent intent = new Intent(MainActivity.this, 
	        		        			PartyDetailActivity.class);
	        		        	intent.putExtra("CurrentParty", party.getId());
	        		        	startActivity(intent);
	        		        }
	        		   }
        		});
    	}
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
}
