/**
 * 
 */
package com.blackout.mydrunkendiaries;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackout.mydrunkendiaries.services.Weather;
import com.blackout.mydrunkendiaries.services.WeatherHttpClient;
import com.blackout.mydrunkendiaries.tools.NetworkTools;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * @author spo2
 *
 */
public class WelcomeActivity extends Activity
{
	private final static String DEG_UNITS = "°C";
	private final static String PRESS_UNITS = " hPa";
	private final static String HUMID_UNITS = "%";
	private String city;
	private TextView cityName, desc, pressure, temp, humidity;
	private ImageView icon;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        cityName = (TextView) findViewById(R.id.city_name);
        desc = (TextView) findViewById(R.id.desc);
        pressure = (TextView) findViewById(R.id.pressure);
        temp = (TextView) findViewById(R.id.temp);
        humidity = (TextView) findViewById(R.id.humidity);
        icon = (ImageView) findViewById(R.id.icon);
        WeatherTask weatherTask = new WeatherTask();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
        		.Builder(this).build();
        ImageLoader.getInstance().init(config);
        if (NetworkTools.isNetworkAvailable(this))
        {
	        try 
	        {
				Weather weather = weatherTask.execute(new String[]{city}).get();
				cityName.setText(weather.getName());
				desc.setText(weather.getWeatherDatas().get(0).getDescription());
				pressure.setText(weather.getMainData().getPressure().toString()
						+ PRESS_UNITS);
				temp.setText(weather.getMainData().getTemp().toString() + DEG_UNITS);
				humidity.setText(weather.getMainData().getHumidity().toString()
						+ HUMID_UNITS);
				String imgURI = WeatherHttpClient.getImageURI(weather
						.getWeatherDatas().get(0).getIcon());	
				ImageLoader.getInstance().displayImage(imgURI + ".png", icon);
				
			} 
	        catch (InterruptedException e) 
	        {
				e.printStackTrace();
			} 
	        catch (ExecutionException e) 
	        {
				e.printStackTrace();
			}
        }
    }
	
	private class WeatherTask extends AsyncTask<String, Void, Weather>
	{
		@Override
		protected Weather doInBackground(String... params) 
		{
			String location = params[0];
			WeatherHttpClient weatherHttpClient = new WeatherHttpClient();
			String data = weatherHttpClient.getWeatherData(location);
			Weather weather = new Weather();
			Gson gson = new Gson();
			weather = gson.fromJson(data, Weather.class);
			return weather;
		}
		
	}
	
}
