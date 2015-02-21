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
import android.view.ActionMode;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.blackout.mydrunkendiaries.adapter.TripCursorAdapter;
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

/**
 * Activity that hold the trip for the party selected.
 * @author romain
 *
 */

/**
 * Sqlite adapter for the Trip.
 */
public class PartyDetailActivity extends Activity implements DialogButtonClick,
		ActionBar.TabListener {

	private TripSqliteAdapter tripSqliteAdapter;
	/**
	 * Current party Id.
	 */
	private Long currentPartyId;
	/**
	 * Current party object.
	 */
	private Party currentParty;
	/**
	 * Trip currently in progress (Object).
	 */
	private Trip tripInProgress;
	private TripMedia lastMedia;
	/*
	 * A cursor to the Trip (request by party).
	 */
	private Cursor cursorTripByParty;
	/**
	 * The cursor adapter for the trip listview.
	 */
	private TripCursorAdapter tripCursorAdapter;

	/**
	 * Listview that hold the ended trips.
	 */
	private ListView lv;
	/**
	 * Hold the name of the trip in progress.
	 */
	private TextView lastActivity;
	/**
	 * The hour of creation of the trip in prgress.
	 */
	private TextView partyBegin;
	/**
	 * The rating bar of the trip in progress.
	 */
	private RatingBar beerBar;
	/**
	 * End the trip in progress.
	 */
	private Button endTrip;

	/**
	 * Current action mode (used for the multiple delete).
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

		// Called each time the action mode is shown. Always called after
		// onCreateActionMode, but
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_party_detail);

		if (savedInstanceState != null) {
			// Restore value of members from saved state
			this.lastMedia = savedInstanceState.getParcelable("lastMedia");
		}

		lv = (ListView) this.findViewById(R.id.placeslistview);
		lastActivity = (TextView) this.findViewById(R.id.last_activity);
		partyBegin = (TextView) this.findViewById(R.id.party_begin);
		beerBar = (RatingBar) this.findViewById(R.id.beerbar_current);

		this.setActionBar();
		ImageView btnPhoto = (ImageView) this.findViewById(R.id.photo);
		btnPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				takePhoto();
			}
		});
		
		endTrip = (Button) this.findViewById(R.id.end_activity);
		endTrip.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (tripInProgress != null) {
					tripInProgress.setDepravity(Math.round(beerBar.getRating()));
					if (tripSqliteAdapter == null) {
						tripSqliteAdapter = new TripSqliteAdapter(
								PartyDetailActivity.this);
					}
					tripSqliteAdapter.open();
					tripInProgress.setEndedAt(DateTimeTools.getDateTime());
					tripSqliteAdapter.update(tripInProgress);
					tripSqliteAdapter.close();
					beerBar.setRating(0);
					PartyDetailActivity.this.recreate();
				}
			}
		});
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
			break;
		}
		case R.id.end_party: {
			if (this.tripInProgress != null) {
				if (tripSqliteAdapter == null) {
					tripSqliteAdapter = new TripSqliteAdapter(
							PartyDetailActivity.this);
				}
				tripSqliteAdapter.open();
				tripInProgress.setEndedAt(DateTimeTools.getDateTime());
				tripSqliteAdapter.update(tripInProgress);
				tripSqliteAdapter.close();
			}
			if ((this.currentParty != null)
					&& ((this.currentParty.getEndedAt() == null) || (this.currentParty
							.getEndedAt() == ""))) {
				this.currentParty.setEndedAt(DateTimeTools.getDateTime());
				PartySqliteAdapter partySqliteAdapter = new PartySqliteAdapter(
						this);
				partySqliteAdapter.open();
				partySqliteAdapter.update(currentParty);
				partySqliteAdapter.close();
			}
			break;
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
				beerBar.setRating(0);
				this.recreate();
				dialog.dismiss();
			}
		}
		if (dialog instanceof ConfirmDialog) {
			this.tripInProgress.setEndedAt(DateTimeTools.getDateTime());
			Float rating = ((ConfirmDialog) dialog).getRating();
			this.tripInProgress.setDepravity(Math.round(rating));
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

	private void setActionBar() {
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
			if (this.tripInProgress.getDepravity() != null) {
				beerBar.setRating(this.tripInProgress.getDepravity());
			}
			if (this.currentParty != null) {
				this.tripInProgress.setParty(this.currentParty);
			}
		}
		this.tripSqliteAdapter.close();
		this.tripSqliteAdapter.open();
		this.cursorTripByParty = this.tripSqliteAdapter
				.getByPartyEndedDataNotNullCursor(this.currentPartyId);
		this.lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mActionMode != null) {
					return false;
				}
				mActionMode = startActionMode(mActionModeCallback);
				CheckBox checkBox = (CheckBox) view
						.findViewById(R.id.check_multiple);
				checkBox.setChecked(true);
				return true;
			}
		});
		this.lv.setMultiChoiceModeListener(new MultiChoiceModeListener() {

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
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				// TODO Auto-generated method stub

			}
		});
		if (this.cursorTripByParty.moveToFirst()) {
			this.tripCursorAdapter = new TripCursorAdapter(this,
					this.cursorTripByParty);
			this.lv.setAdapter(this.tripCursorAdapter);
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
		if(this.tripInProgress != null)
		{
		String name = this.tripInProgress.getPlace().getName()
				+ SimpleMediaUtils.getPhotoDefaultName();
		String party = this.getCurrentParty().getName();
		String path = SimpleMediaUtils.getPhotoPath(name, party);
		SimpleMediaUtils.setLastMediaTakenPath(path);

		TripMedia lastMedia = new TripMedia();
		lastMedia.setName(name);
		lastMedia.setPath(path);
		lastMedia.setTrip(this.tripInProgress);

		String fullName = SimpleMediaUtils.getMediaName(name, party);
		startActivityForResult(SimpleMediaUtils.getImageIntent(fullName), SimpleMediaUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		
		}
	}

	private void saveTripMedia(String path, String name, Trip trip) {
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
			if (resultCode == RESULT_OK) {
				String path = this.lastMedia.getPath();				
				MediaScannerConnection.scanFile(this, new String[] { path },
						null, null);
				TripMediaSqliteAdapter adapter = new TripMediaSqliteAdapter(this);
				adapter.create(this.lastMedia);
			}
			else
			{
				this.lastMedia = null;
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
	/**
	 * Change the visibility of the checkbox to VISIBLE.
	 */
	public void showListViewCheckBox() {
		for (int i = 0; i < this.lv.getChildCount(); i++) {
			CheckBox checkBox = (CheckBox) this.lv.getChildAt(i).findViewById(
					R.id.check_multiple);
			checkBox.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * Change the visibility of the checkbox to GONE.
	 */
	public void hideListViewCheckBox() {
		for (int i = 0; i < this.lv.getChildCount(); i++) {
			CheckBox checkBox = (CheckBox) this.lv.getChildAt(i).findViewById(
					R.id.check_multiple);
			checkBox.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * Reset checkbox states in ListView
	 */
	public void unSelectAllItems() {
		for (int i = 0; i < this.lv.getChildCount(); i++) {
			CheckBox checkBox = (CheckBox) this.lv.getChildAt(i).findViewById(
					R.id.check_multiple);
			checkBox.setChecked(false);
		}
	}

	public void multipleItemDelete() {
		for (int i = 0; i < this.lv.getChildCount(); i++) {
			CheckBox checkBox = (CheckBox) this.lv.getChildAt(i).findViewById(
					R.id.check_multiple);
			if (checkBox.isChecked()) {
				Trip trip = tripSqliteAdapter.get((Long) lv.getChildAt(i)
						.getTag());
				tripSqliteAdapter.delete(trip);
			}
		}
	}

}
