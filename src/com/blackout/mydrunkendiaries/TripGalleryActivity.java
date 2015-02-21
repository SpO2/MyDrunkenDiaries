package com.blackout.mydrunkendiaries;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.blackout.mydrunkendiaries.adapter.TripMediaCursorAdapter;
import com.blackout.mydrunkendiaries.data.TripMediaSqliteAdapter;

public class TripGalleryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trip_gallery);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {		    
		    //bindImages(extras.getLong("tripId"));
		    setGridView(extras.getLong("tripId"));
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trip_gallery, menu);
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
	private void setGridView(long tripId)
	{
		TripMediaSqliteAdapter tripMediaAdapter = new TripMediaSqliteAdapter(this);
		tripMediaAdapter.open();
		Cursor cursor = tripMediaAdapter.getCursorByTrip(tripId);
		if(cursor.moveToFirst())
		{
			GridView gridview = (GridView) findViewById(R.id.gallery_layout);
		    gridview.setAdapter(new TripMediaCursorAdapter(this,cursor,0));
	    
		}
		tripMediaAdapter.close();
	}

}
