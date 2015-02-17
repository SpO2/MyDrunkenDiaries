/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Network Utils.                             *   
 *                                                      *   
 * Usage: Class that gives tools to check network       *
 * availability                                         *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Network functions
 * @author spo2
 *
 */
public abstract class NetworkTools 
{
	public static boolean isNetworkAvailable(Context context) 
	{
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
