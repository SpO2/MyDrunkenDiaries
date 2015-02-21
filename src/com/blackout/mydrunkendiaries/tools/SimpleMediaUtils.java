package com.blackout.mydrunkendiaries.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

/**
 * @author damien
 *
 */
public class SimpleMediaUtils {
	public static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	// Static default application values.
	private static final String BASE_MEDIA_URL = "MyDrunkenMedia";
	private static String lastMediaTakenPath;

	private static Uri fileUri;

	// getters setters
	public static String getLastMediaTakenPath() {
		return lastMediaTakenPath;
	}

	public static void setLastMediaTakenPath(String path) {
		lastMediaTakenPath = path;
	}

	public static String getPhotoDefaultName() {
		return String.format("IMG_%s.jpg", new SimpleDateFormat(
				"yyyyMMdd_HHmmss").format(new Date()));
	}

	public static String getVideoDefaultName() {
		return String.format("VID_%s.mp4", new SimpleDateFormat(
				"yyyyMMdd_HHmmss").format(new Date()));
	}

	public static String getVideoPath(String name) {
		return getMediaPath(MEDIA_TYPE_VIDEO, name);
	}

	public static String setVideoPath(String name, String folder) {
		String mediaDirectory = Environment.DIRECTORY_DCIM;
		mediaDirectory = Environment.DIRECTORY_MOVIES;
		File mediaStorageDir = new File(
				Environment.getExternalStoragePublicDirectory(mediaDirectory),
				BASE_MEDIA_URL + File.separator + folder);

		// Create the party directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(BASE_MEDIA_URL, "failed to create directory");
				return null;
			}
		}
		name = folder + File.separator + name;
		return getMediaPath(MEDIA_TYPE_VIDEO, name);
	}

	public static String getPhotoPath(String name) {
		return getMediaPath(MEDIA_TYPE_IMAGE, name);
	}

	public static String setPhotoPath(String name, String folder) {
		String mediaDirectory = Environment.DIRECTORY_DCIM;
		mediaDirectory = Environment.DIRECTORY_PICTURES;
		File mediaStorageDir = new File(
				Environment.getExternalStoragePublicDirectory(mediaDirectory),
				BASE_MEDIA_URL + File.separator + folder);

		// Create the party directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(BASE_MEDIA_URL, "failed to create directory");
				return null;
			}
		}
		name = folder + File.separator + name;
		return getMediaPath(MEDIA_TYPE_IMAGE, name);
	}

	public static String getMediaName(String name, String folder) {
		return folder + File.separator + name;
	}

	public static Intent getImageIntent(String name) {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = getFileUri(MEDIA_TYPE_IMAGE, name); // create a file to save
														// the image

		if (fileUri == null) {
			return null;
		}
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image
		// file name
		return intent;

	}

	public static Intent getVideoIntent(String name) {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		fileUri = getFileUri(MEDIA_TYPE_VIDEO, name); // create a file to save
														// the image

		if (fileUri == null) {
			return null;
		}
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		return intent;
	}
	public static Bitmap extractThumbnails(Uri imagePath, int width, int height) {
		Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(
				BitmapFactory.decodeFile(imagePath.toString()), width, height);
		return ThumbImage;
	}
	public static Bitmap extractThumbnails(String imagePath, int width, int height) {
		Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(
				BitmapFactory.decodeFile(imagePath), width, height);
		return ThumbImage;
	}
	public static void getImagesList(Context context, String folder)
	{
	    // which image properties are we querying
	    String[] projection = new String[]{
	            MediaStore.Images.Media._ID,
	            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
	            MediaStore.Images.Media.DATE_TAKEN,
	            MediaStore.Images.Media.DISPLAY_NAME,
	            MediaStore.Images.Media.DESCRIPTION
	            
	    };

	   
	    // Make the query.
	    Cursor cur = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
	            projection, 
	            MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " like ? ",
	            new String[] {"%party%"},
	            null
	            );

	    if (cur.moveToFirst()) {
	        String bucket;
	        String date;
	        String name;
	        String description;
	        int bucketColumn = cur.getColumnIndex(
	            MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

	        int dateColumn = cur.getColumnIndex(
	            MediaStore.Images.Media.DATE_TAKEN);
	        int nameColumn = cur.getColumnIndex(
		            MediaStore.Images.Media.DISPLAY_NAME);
	        int descriptionColumn =cur.getColumnIndex(
		            MediaStore.Images.Media.DESCRIPTION);

	        do {
	            // Get the field values
	            bucket = cur.getString(bucketColumn);
	            date = cur.getString(dateColumn);
	            name = cur.getString(nameColumn);
	            description = cur.getString(descriptionColumn);

	            // Do something with the values.
	            Log.i("ListingImages", " bucket=" + bucket 
	                   + "  date_taken=" + date +" Name="+name+" Description="+description);
	        } while (cur.moveToNext());

	    }
	}
	public static Cursor getImagesFromAblum(Context context, String album) {
		String[] projection = new String[] { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
				MediaStore.Images.Media.DATE_TAKEN,
				MediaStore.Images.Media.DISPLAY_NAME,
				MediaStore.Images.Media.DESCRIPTION,
				MediaStore.Images.Media.DATA

		};
		Cursor cur = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
				MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " = ? ",
				new String[] { album }, null);
		return cur;
	}

	private static String getMediaPath(int type, String name) {
		return getMediaBasePath(type) + name;
	}

	/*
	 * Get the Media file base path.
	 * 
	 * @param type : the type of the media.
	 */
	private static String getMediaBasePath(int type) {
		File mediaStorageDir = null;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					BASE_MEDIA_URL);
		}
		if (type == MEDIA_TYPE_VIDEO) {
			mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
					BASE_MEDIA_URL);
		}
		return mediaStorageDir.getPath() + File.separator;
	}

	private static Uri getFileUri(int type, String name) {
		File file = getOutputMediaFile(type, name);
		if (file == null) {
			return null;
		}
		return Uri.fromFile(file);
	}

	private static File getOutputMediaFile(int type, String name) {
		/*
		 * if(Environment.getExternalStorageState() !=
		 * Environment.MEDIA_MOUNTED) { return null; }
		 */
		String mediaDirectory = Environment.DIRECTORY_DCIM;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaDirectory = Environment.DIRECTORY_PICTURES;
		}
		if (type == MEDIA_TYPE_VIDEO) {
			mediaDirectory = Environment.DIRECTORY_MOVIES;
		}
		File mediaStorageDir = new File(
				Environment.getExternalStoragePublicDirectory(mediaDirectory),
				BASE_MEDIA_URL);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(BASE_MEDIA_URL, "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		File mediaFile;

		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ name);
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ name);
		} else {
			return null;
		}

		return mediaFile;
	}

}