package com.blackout.mydrunkendiaries.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.blackout.mydrunkendiaries.data.TripMediaSqliteAdapter;
import com.blackout.mydrunkendiaries.tools.SimpleMediaUtils;


public class TripMediaCursorAdapter extends CursorAdapter {
	private Cursor cursor;	
	private LayoutInflater mInflater;
	public TripMediaCursorAdapter(Context context, Cursor cur,int flags) {
		super(context, cur, flags);
		this.cursor = cur;
		this.mInflater  = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
		// TODO Auto-generated constructor stub
		 
	}

	@Override
	 public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(500, 500));
            imageView.setAdjustViewBounds(true);
            imageView.setMaxHeight(500);
            imageView.setMaxWidth(500);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        String path = cursor.getString(cursor.getColumnIndex(TripMediaSqliteAdapter.COLUMN_PATH));
        bindImage(path,imageView);
        return imageView;
    }
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return null;
		//return mInflater.inflate(R.layout.photo_list_layout, parent, false);
	}
	
	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Bind imageView with thumbnail bitmap from imageFile.
	 * @param uri Uri of the image to bind.
	 * @param imageView the imageView to bind.
	 */
	private void bindImage(String uri, ImageView imageView)
	{
		BitmapFactory.Options op = new BitmapFactory.Options();
		Bitmap bitmap = SimpleMediaUtils.extractThumbnails(uri, 600, 600);
		imageView.setImageBitmap(bitmap);
		  
	}

}
