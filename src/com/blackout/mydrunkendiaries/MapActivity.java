/**
 * 
 */
package com.blackout.mydrunkendiaries;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

/**
 * Activity that hold map itinerary of the party.
 * @author romain
 *
 */
public class MapActivity extends Activity implements ActionBar.TabListener
{
	/**
	 * The current party Id.
	 */
	private Long currentPartyId;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        final ActionBar actionBar = this.getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.addTab(actionBar.newTab().setText(R.string.tab_party_detail_places)
        		 .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.tab_party_detail_maps)
       		 .setTabListener(this));
        this.currentPartyId = this.getIntent().getLongExtra("CurrentParty",0);
        actionBar.selectTab(actionBar.getTabAt(1));
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) 
	{

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) 
	{
		if (tab.getPosition() == 0 && this.getActionBar().getNavigationItemCount() > 1)
		{
			Intent intent = new Intent(MapActivity.this, PartyDetailActivity.class);
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
}
