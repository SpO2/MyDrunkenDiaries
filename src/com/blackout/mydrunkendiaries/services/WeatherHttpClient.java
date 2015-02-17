/********************************************************           
 * Program MyDrunkenDiaries                             *   
 *                                                      *   
 * Author:  Romain                                      *   
 *                                                      *   
 * Purpose:  Weather web service client.                *   
 *                                                      *   
 * Usage: Client for the web service request.    	    *   
 *                                                      *   
 ********************************************************/
package com.blackout.mydrunkendiaries.services;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Contains methods to request weather data
 * @author spo2
 *
 */
public class WeatherHttpClient 
{
	private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
	private static String LANG_FR = "&lang=fr";
	private static String METRIC_UNIT = "&units=metric";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
 
    /**
     * Get current weather data
     * @param location
     * @return the json result
     */
    public String getWeatherData(String location) 
    {
        HttpURLConnection con = null ;
        InputStream is = null;
 
        try 
        {
            con = (HttpURLConnection) ( new URL(BASE_URL + location + METRIC_UNIT + LANG_FR)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
             
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");
             
            is.close();
            con.disconnect();
            return buffer.toString();
        }
        catch(Throwable t) 
        {
            t.printStackTrace();
        }
        finally 
        {
            try 
            { 
            	is.close(); 
            } 
            catch(Throwable t) 
            {
            	
            }
            try 
            { 
            	con.disconnect(); 
            } 
            catch(Throwable t) 
            {
            	
            }
        }
        return null;          
    }
     
    public byte[] getImage(String code) 
    {
        HttpURLConnection con = null ;
        InputStream is = null;
        try 
        {
            con = (HttpURLConnection) ( new URL(IMG_URL + code)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
             
            // Let's read the response
            is = con.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
             
            while ( is.read(buffer) != -1)
                baos.write(buffer);
             
            return baos.toByteArray();
        }
        catch(Throwable t) 
        {
            t.printStackTrace();
        }
        finally 
        {
            try 
            { 
            	is.close(); 
            } 
            catch(Throwable t) 
            {
            	
            }
            try 
            { 
            	con.disconnect(); 
            } 
            catch(Throwable t) 
            {
            	
            }
        } 
        return null;   
    }
    
    /**
     * Build the image uri
     * @param code
     * @return the uri of the image
     */
    public static String getImageURI(String code)
    {
    	return WeatherHttpClient.IMG_URL + code;
    }

}
