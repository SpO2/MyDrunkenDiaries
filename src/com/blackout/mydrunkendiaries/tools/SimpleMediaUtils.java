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
 * Provide an abstraction of media management.
 * this class provide only a very basic implementation of media management for 
 * standard features.
 */
public class SimpleMediaUtils {
	/**
	 * code for video intent request.
	 */
	public static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	/**
	 * code for image intent request.
	 */
	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	/**
	 * Code used for media type image.
	 */
	public static final int MEDIA_TYPE_IMAGE = 1;
	/**
	 * Code used for media type video.
	 */
	public static final int MEDIA_TYPE_VIDEO = 2;

	// Static default application values.
	private static final String BASE_MEDIA_URL = "MyDrunkenMedia";
	private static String lastMediaTakenPath;

	private static Uri fileUri;

	// getters setters
	
	/**
	 * get Static temporary variable to stock last media path. 
	 */
	public static String getLastMediaTakenPath() {
		return lastMediaTakenPath;
	}
	/**
	 * set  Static temporary variable to stock last media path.
	 * @param path 
	 */
	public static void setLastMediaTakenPath(String path) {
		lastMediaTakenPath = path;
	}
	/**
	 * get the photo default name, based on datetime format "yyyyMMdd_HHmmss".
	 * @return IMG_"datetimeformat".jpg
	 */
	public static String getPhotoDefaultName() {
		return String.format("IMG_%s.jpg", new SimpleDateFormat(
				"yyyyMMdd_HHmmss").format(new Date()));
	}
	/**
	 * get the video default name, based on datetime format "yyyyMMdd_HHmmss".
	 * @return VID_"datetimeformat".mp4
	 */
	public static String getVideoDefaultName() {
		return String.format("VID_%s.mp4", new SimpleDateFormat(
				"yyyyMMdd_HHmmss").format(new Date()));
	}
	
	/**
	 * get the video path.
	 * @param name of the video.	 
	 */
	public static String getVideoPath(String name) {
		return getMediaPath(MEDIA_TYPE_VIDEO, name);
	}

	/**
	 * set the video path. If folder doesn't exist, it'll be created.
	 *  @param name the name of the video.
	 *  @param folder the folder of the media.
	 *  @return the full path of the media
	 */
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
	/**
	 * get the photo path.
	 * @param name of the photo	.  
	 */
	public static String getPhotoPath(String name) {
		return getMediaPath(MEDIA_TYPE_IMAGE, name);
	}
	
	/**
	 * set the photo path. If folder doesn't exist, it'll be created.
	 *  @param name the name of the photo.
	 *  @param folder the folder of the media.
	 * @return the full path of the media
	 */
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

	/**
	 * get the media name based on folder.
	 * @param name name of the media.
	 * @param folder folder of the media.
	 */
	public static String getMediaName(String name, String folder) {
		return folder + File.separator + name;
	}
	
	/**
	 * get the intent for the camera, for image capture.
	 * @param name name of the photo. 
	 * If the image is in a sub folder, the whole name is required (sub_folder/myPhoto.jpg).
	 * @return the intent with MediaStore.EXTRA_OUTPUT set.
	 */
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
	/**
	 * get the intent for the camera, for video capture.
	 * @param name name of the video. 
	 * If the video is in a sub folder, the whole name is required (sub_folder/myPhoto.jpg).
	 * @return the intent with MediaStore.EXTRA_OUTPUT set.
	 */
	public static Intent getVideoIntent(String name) {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		fileUri = getFileUri(MEDIA_TYPE_VIDEO, name); 
		if (fileUri == null) {
			return null;
		}
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		return intent;
	}
	
	/**
	 * extract a thumbnail for an image.
	 * @param imagePath the path of the image.	 
	 * @param width width for the thumbnail.
	 * @param height height for the thumbnail.
	 * @return the thumbnail
	 */
	public static Bitmap extractThumbnails(Uri imagePath, int width, int height) {
		Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(
				BitmapFactory.decodeFile(imagePath.toString()), width, height);
		return ThumbImage;
	}
	/**
	 * extract a thumbnail for an image.
	 * @param imagePath the path of the image.	 
	 * @param width width for the thumbnail.
	 * @param height height for the thumbnail.
	 * @return the thumbnail
	 */
	public static Bitmap extractThumbnails(String imagePath, int width, int height) {
		Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(
				BitmapFactory.decodeFile(imagePath), width, height);
		return ThumbImage;
	}


	/**
	 * get the image list from the MediaStore by album name.
	 * @param album the name of the album, usually the folder.
	 */
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

	/**
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
	
	/**
	 * get the file Uri for a media type.
	 * @param type type of the media (referenced by constant values).
	 * @param name name of the media.
	 */
	private static Uri getFileUri(int type, String name) {
		File file = getOutputMediaFile(type, name);
		if (file == null) {
			return null;
		}
		return Uri.fromFile(file);
	}

	/**
	 * get the file for a media type.
	 * @param type type of the media.
	 * @param name name of tthe media.
	 */
	private static File getOutputMediaFile(int type, String name) {
		/*
		 * An error was spotted during debug: Environment.getExternalStorageState() return "mounted" but !=   Environment.MEDIA_MOUNTED
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