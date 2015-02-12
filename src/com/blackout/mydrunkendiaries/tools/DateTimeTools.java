/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Date Time Utils.                           *   
 *                                                      *   
 * Usage: Class that gives tools to convert or format   *
 * datetime values                                      *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.tools;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author spo2
 *
 */
public abstract class DateTimeTools 
{
	/**
	 * Get the current datetime
	 * @return Date and time in a string (format "dd-MM-yyyy HH:mm:ss)
	 */
	public static String getDateTime()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	/**
	 * Get hours and minutes from datetime value
	 * @param dt
	 * @return a string with the time of the date (format HH:mm)
	 */
	@SuppressLint("DefaultLocale")
	public static String getTimeFromDate(Date dt)
	{
		String time = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		time = String.format("%02d:%02d", hours, minutes);
		return time;
	}
	
	/**
	 * Get hours and minutes in string
	 * @param dateString
	 * @return a string with the time of the date (format HH:mm)
	 */
	public static String getTimeFromString(String dateString)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
		try 
		{
			Date dt = dateFormat.parse(dateString);
			return getTimeFromDate(dt);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return "";
	}
}
