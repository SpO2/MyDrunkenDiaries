/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  PartyActivity                              *   
 *                                                      *   
 * Usage: List all the parties in the application.      *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.ListView;

import com.blackout.mydrunkendiaries.adapter.PartyCursorAdapter;
import com.blackout.mydrunkendiaries.data.PartySqliteAdapter;
import com.blackout.mydrunkendiaries.entites.Party;
import com.blackout.mydrunkendiaries.externalfragment.DialogButtonClick;
import com.blackout.mydrunkendiaries.externalfragment.NewPartyDialogFragment;
import com.blackout.mydrunkendiaries.tools.DateTimeTools;

/**
 * Activity that hold the parties.
 * @author romain
 *
 */
public class PartyActivity extends Activity implements DialogButtonClick
{
	/**
	 * Hold the party list.
	 */
	private ListView listView;
	/**
	 * Sqlite adapter for the parties.
	 */
	private PartySqliteAdapter partySqlAdapter;
	/**
	 * Cursor adapter for the party listview.
	 */
	private PartyCursorAdapter partyCursorAdapter;
	/**
	 * A cursor.
	 */
	private Cursor partyCursor;
	/**
	 * Current actionMode (use for multiple delete in listview).
	 */
	private ActionMode mActionMode;
	
	/**
	 * Callback for the actionMode.
	 */
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

	    // Called when the action mode is created; startActionMode() was called
	    @Override
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        // Inflate a menu resource providing context menu items
	        MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.action_mode_list, menu);
	        return true;
	    }

	    // Called each time the action mode is shown. Always called after onCreateActionMode, but
	    // may be called multiple times if the mode is invalidated.
	    @Override
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	    	showListViewCheckBox();
	        return true; // Return false if nothing is done
	    }

	    // Called when the user selects a contextual menu item
	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.delete_select:
	                multipleItemDelete();
	                refreshEnv();
	                mode.finish(); // Action picked, so close the CAB
	                return true;
	            default:
	                return false;
	        }
	    }

	    // Called when the user exits the action mode
	    @Override
	    public void onDestroyActionMode(ActionMode mode) {
	    	hideListViewCheckBox();
	    	unSelectAllItems();
	        mActionMode = null;
	    }
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);  
        setTitle(R.string.title_activity_party);
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
 			   new PartySqliteAdapter(PartyActivity.this);
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
    	this.partySqlAdapter = new PartySqliteAdapter(this);
    	this.partySqlAdapter.open();
        this.partyCursor = this.partySqlAdapter.getAllCursor();
        this.listView = (ListView) this.findViewById(R.id.listView);
        this.listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        if (this.partyCursor.moveToFirst())
        {
        	this.partyCursorAdapter = new PartyCursorAdapter(this, this.partyCursor);
        	this.listView.setAdapter(this.partyCursorAdapter);
        	this.listView.setOnItemClickListener(
        			new OnItemClickListener() 
        			{
	        		   @Override
	        		   public void onItemClick(AdapterView<?> parent, 
	        				       View view,int position, long id) 
	        		   {
        		        	Intent intent = new Intent(PartyActivity.this, 
        		        			PartyDetailActivity.class);
        		        	intent.putExtra("CurrentParty", (Long) view.getTag());
        		        	startActivity(intent);
	        		   }
        		});
        	this.listView.setOnItemLongClickListener(new OnItemLongClickListener() 
        	{
        		public boolean onItemLongClick(AdapterView<?> parent, View view,
        				int position, long id) 
        		{
        			if (mActionMode != null) 
        			{
        	            return false;
        	        }
        			mActionMode = startActionMode(mActionModeCallback);
        			CheckBox checkBox = (CheckBox) view.findViewById(R.id.check_multiple);
        			checkBox.setChecked(true);
        			return true;
        		}
			});
        	this.listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
				
				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public void onDestroyActionMode(ActionMode mode) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public void onItemCheckedStateChanged(ActionMode mode, int position,
						long id, boolean checked) {
					mode.setTitle(R.string.item_selected + 
							partyCursorAdapter.getSelectedCount().toString());
					
				}
			});
        }
    }
    
    /**
     * Change the visibility of the checkbox to VISIBLE.
     */
    public void showListViewCheckBox(){
    	for (int i=0;i<this.listView.getChildCount();i++){
    		CheckBox checkBox = (CheckBox) this.listView.getChildAt(i)
    				.findViewById(R.id.check_multiple);
    		checkBox.setVisibility(View.VISIBLE);
    	}
    }
    
    /**
     * Change the visibility of the checkbox to GONE.
     */
    public void hideListViewCheckBox(){
    	for (int i=0;i<this.listView.getChildCount();i++){
    		CheckBox checkBox = (CheckBox) this.listView.getChildAt(i)
    				.findViewById(R.id.check_multiple);
    		checkBox.setVisibility(View.INVISIBLE);
    	}
    }
    
    /**
     * Reset checkbox states in ListView
     */
    public void unSelectAllItems(){
    	for (int i=0;i<this.listView.getChildCount();i++){
    		CheckBox checkBox = (CheckBox) this.listView.getChildAt(i)
    				.findViewById(R.id.check_multiple);
    		checkBox.setChecked(false);
    	}
    }
    
    public void multipleItemDelete(){
    	for (int i=0;i<this.listView.getChildCount();i++){
    		CheckBox checkBox = (CheckBox) this.listView.getChildAt(i)
    				.findViewById(R.id.check_multiple);
    		if (checkBox.isChecked()){
    			Party party = partySqlAdapter.get((Long)listView.getChildAt(i).getTag());
    			partySqlAdapter.delete(party);
    		}
    	}
    }
    
}
