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

import java.io.File;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.blackout.mydrunkendiaries.adapter.PlacesListAdapter;
import com.blackout.mydrunkendiaries.data.PartySqliteAdapter;
import com.blackout.mydrunkendiaries.data.PlaceSqliteAdapter;
import com.blackout.mydrunkendiaries.data.TripMediaSqliteAdapter;
import com.blackout.mydrunkendiaries.data.TripSqliteAdapter;
import com.blackout.mydrunkendiaries.entites.Party;
import com.blackout.mydrunkendiaries.entites.Place;
import com.blackout.mydrunkendiaries.entites.Trip;
import com.blackout.mydrunkendiaries.entites.TripMedia;
import com.blackout.mydrunkendiaries.externalfragment.ConfirmDialog;
import com.blackout.mydrunkendiaries.externalfragment.DialogButtonClick;
import com.blackout.mydrunkendiaries.externalfragment.NewPlaceDialogFragment;
import com.blackout.mydrunkendiaries.tools.DateTimeTools;
import com.blackout.mydrunkendiaries.tools.SimpleMediaUtils;

public class PartyDetailActivity extends Activity implements DialogButtonClick,
		ActionBar.TabListener {

	private PlacesListAdapter placesListAdapater;
	private TripSqliteAdapter tripSqliteAdapter;
	private ArrayList<Trip> trips;
	private Long currentPartyId;
	private Party currentParty;
	private Trip tripInProgress;
	private TripMedia lastMedia;

	private ListView lv;
	private TextView lastActivity;
	private TextView partyBegin;
	private RatingBar beerBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.party_detail);
		
		if (savedInstanceState != null) {
	        // Restore value of members from saved state
	        this.lastMedia = savedInstanceState.getParcelable("lastMedia");	       
		}
		
		lv = (ListView) this.findViewById(R.id.placeslistview);
		lastActivity = (TextView) this.findViewById(R.id.last_activity);
		partyBegin = (TextView) this.findViewById(R.id.party_begin);
		beerBar = (RatingBar) this.findViewById(R.id.beerbar);
		
		this.setActionBar();
		
		this.currentPartyId = this.getIntent().getLongExtra("CurrentParty", 0);
		this.setCurrentParty(this.currentPartyId);
		refreshEnv();
	}
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    // Save the user's current game state
	    savedInstanceState.putParcelable("lastMedia", this.lastMedia);
	    
	    // Always call the superclass so it can save the view hierarchy state
	    super.onSaveInstanceState(savedInstanceState);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.party_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.new_trip: {
			if (this.tripInProgress != null) {
				showConfirmDialog(getString(R.string.dialog_confirm_new_trip),
						true);
			} else {
				showNewPlaceDialog();
			}
		}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if (dialog instanceof NewPlaceDialogFragment) {
			if (this.getCurrentParty() != null) {
				NewPlaceDialogFragment newPlaceDialogFragment = (NewPlaceDialogFragment) dialog;
				Place place = addPlace(newPlaceDialogFragment.getPlaceName()
						.getText().toString());

				Trip trip = addTrip(place, this.getCurrentParty());
				PartyDetailActivity.this.tripInProgress = trip;
				this.recreate();
				dialog.dismiss();
			}
		}
		if (dialog instanceof ConfirmDialog) {
			this.tripInProgress.setEndedAt(DateTimeTools.getDateTime());
			TripSqliteAdapter tripSqliteAdapter = new TripSqliteAdapter(this);
			tripSqliteAdapter.open();
			tripSqliteAdapter.update(this.tripInProgress);
			this.tripSqliteAdapter.close();
			this.tripInProgress = null;
			refreshEnv();
			showNewPlaceDialog();
		}
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {

	}
	private void setActionBar()
	{
		final ActionBar actionBar = this.getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar
				.addTab(actionBar.newTab()
						.setText(R.string.tab_party_detail_places)
						.setTabListener(this));
		actionBar.addTab(actionBar.newTab()
				.setText(R.string.tab_party_detail_maps).setTabListener(this));
		actionBar.selectTab(actionBar.getTabAt(0));
		
	}

	/**
	 * Reset global environement
	 */
	public void refreshEnv() {
		this.tripSqliteAdapter = new TripSqliteAdapter(this);
		this.tripSqliteAdapter.open();
		this.tripInProgress = this.tripSqliteAdapter
				.getByPartyWithPlaceEndedDateNull(this.currentPartyId);
		if (this.tripInProgress != null) {
			lastActivity.setText(this.tripInProgress.getPlace().getName());
			partyBegin.setText(DateTimeTools
					.getTimeFromString(this.tripInProgress.getCreatedAt()));
			beerBar.setRating(this.tripInProgress.getDepravity());
			if (this.currentParty != null) {
				this.tripInProgress.setParty(this.currentParty);
			}
		}
		this.tripSqliteAdapter.close();
		this.tripSqliteAdapter.open();
		this.trips = this.tripSqliteAdapter
				.getByPartyWithPlaceEndedDataNotNull(this.currentPartyId);
		if (!this.trips.isEmpty() && (this.lv != null)) {
			this.placesListAdapater = new PlacesListAdapter(this, this.trips);
			this.lv.setAdapter(this.placesListAdapater);
			this.placesListAdapater.notifyDataSetChanged();
		}
	}

	private void showNewPlaceDialog() {
		NewPlaceDialogFragment newPlaceDialogFragment = new NewPlaceDialogFragment();
		newPlaceDialogFragment.show(getFragmentManager(),
				"NewPlaceDialogFragment");
	}

	/**
	 * Add a new Place in the database
	 * 
	 * @param placeName
	 * @return the new place added
	 */
	private Place addPlace(String placeName) {
		Place place = new Place();
		place.setName(placeName);
		PlaceSqliteAdapter placeSqliteAdapter = new PlaceSqliteAdapter(
				PartyDetailActivity.this);
		placeSqliteAdapter.open();
		Long newPlace = placeSqliteAdapter.create(place);
		place.setId(newPlace);
		placeSqliteAdapter.close();
		return place;
	}

	/**
	 * Add a new Trip in the database
	 * 
	 * @param place
	 * @param party
	 * @return the new trip added
	 */
	private Trip addTrip(Place place, Party party) {
		Trip trip = new Trip();
		trip.setPlace(place);
		trip.setParty(party);
		trip.setCreatedAt(DateTimeTools.getDateTime());
		TripSqliteAdapter tripSqliteAdapter = new TripSqliteAdapter(
				PartyDetailActivity.this);
		tripSqliteAdapter.open();
		Long newTrip = tripSqliteAdapter.create(trip);
		trip.setId(newTrip);
		tripSqliteAdapter.close();
		return trip;
	}

	/**
	 * Show the confirmation dialog.
	 * 
	 * @param message
	 */
	public void showConfirmDialog(String message, Boolean withRating) {
		ConfirmDialog confirmDialog = new ConfirmDialog(message, withRating);
		confirmDialog.show(getFragmentManager(), "ConfirmDialog");
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if (tab.getPosition() == 1) {
			Intent intent = new Intent(PartyDetailActivity.this,
					MapActivity.class);
			intent.putExtra("CurrentParty", this.currentPartyId);
			startActivity(intent);
			this.finish();
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	public void setCurrentParty(Long currentPartyId) {
		PartySqliteAdapter partySqliteAdapter = new PartySqliteAdapter(this);
		partySqliteAdapter.open();
		this.currentParty = partySqliteAdapter.get(currentPartyId);
		partySqliteAdapter.close();
	}

	public Party getCurrentParty() {
		return this.currentParty;
	}

	private void takePhoto() {
		String name = this.tripInProgress.getPlace().getName()
				+ SimpleMediaUtils.getPhotoDefaultName();
		String party = this.getCurrentParty().getName();		
		String path = SimpleMediaUtils.getPhotoPath(
				name, party);
		SimpleMediaUtils.setLastMediaTakenPath(path);
		
		TripMedia lastMedia = new TripMedia();
		lastMedia.setName(name);
		lastMedia.setPath(path);
		lastMedia.setTrip(this.tripInProgress);
		
		String fullName = SimpleMediaUtils.getMediaName(name, party);
		SimpleMediaUtils.getImageIntent(fullName);
	}

	private void saveTripMedia(String path, String name,Trip trip) {
		TripMedia tripMedia = new TripMedia();
		tripMedia.setPath(path);
		tripMedia.setName(name);
		tripMedia.setTrip(trip);
		TripMediaSqliteAdapter adapter = new TripMediaSqliteAdapter(this);
		adapter.create(tripMedia);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Stuff if camera intent
		if (requestCode == SimpleMediaUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE
				|| requestCode == SimpleMediaUtils.CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
			if(resultCode == RESULT_OK)
			{
			String path = this.lastMedia.getPath();
			Log.i("LastMedia", "lastMedia = "+this.lastMedia.getName());

			MediaScannerConnection.scanFile(this, new String[] { path }, null,
					null);
			}

			/*
			 * Intent intent = new Intent();
			 * intent.setAction(android.content.Intent.ACTION_VIEW); File file =
			 * new File(path); intent.setDataAndType(Uri.fromFile(file),
			 * "image/*"); startActivity(intent);
			 */

		}
	}

	/**
	 * ///* public static class TabListener<T extends Fragment> implements
	 * ActionBar.TabListener // { // private Fragment mFragment; // private
	 * final Activity mActivity; // private final String mTag; // private final
	 * Class<T> mClass; // // /** Constructor used each time a new tab is
	 * created. // * @param activity The host Activity, used to instantiate the
	 * fragment // * @param tag The identifier tag for the fragment // * @param
	 * clz The fragment's Class, used to instantiate the fragment //
	 */
	// public TabListener(Activity activity, String tag, Class<T> clz)
	// {
	// mActivity = activity;
	// mTag = tag;
	// mClass = clz;
	// }
	//
	// /* The following are each of the ActionBar.TabListener callbacks */
	//
	// public void onTabSelected(Tab tab, FragmentTransaction ft)
	// {
	// // Check if the fragment is already initialized
	// if (mFragment == null)
	// {
	// // If not, instantiate and add it to the activity
	// mFragment = Fragment.instantiate(mActivity, mClass.getName());
	// ft.add(android.R.id.content, mFragment, mTag);
	// }
	// else
	// {
	// // If it exists, simply attach it in order to show it
	// ft.attach(mFragment);
	// }
	// }
	//
	// public void onTabUnselected(Tab tab, FragmentTransaction ft)
	// {
	// if (mFragment != null)
	// {
	// // Detach the fragment, because another one is being attached
	// ft.detach(mFragment);
	// }
	// }
	//
	// public void onTabReselected(Tab tab, FragmentTransaction ft)
	// {
	// // User selected the already selected tab. Usually do nothing.
	// }
	// }

}
