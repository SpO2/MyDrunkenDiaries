/**
 * 
 */
package com.blackout.mydrunkendiaries;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.blackout.mydrunkendiaries.adapter.CityListAdapter;
import com.blackout.mydrunkendiaries.services.City;
import com.blackout.mydrunkendiaries.services.Weather;
import com.blackout.mydrunkendiaries.services.WeatherHttpClient;
import com.blackout.mydrunkendiaries.tools.NetworkTools;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Welcome activity - Show on application startup.
 * @author romain
 *
 */
public class WelcomeActivity extends Activity implements OnClickListener,
							OnItemClickListener
{
	/**
	 * Degrees unit. (Weather Webservice)
	 */
	private final static String DEG_UNITS = "°C";
	/**
	 * Pressure unit. (Weather Webservice)
	 */
	private final static String PRESS_UNITS = " hPa";
	/**
	 * Humidity unit. (Weather Webservice)
	 */
	private final static String HUMID_UNITS = "%";
	/**
	 * Name of the pref file to store the last city for the weather webservice.
	 */
	private final static String PREF_FILE= "MyDrunkenPrefs";
	/**
	 * Key to store the last city for the weather webservice.
	 */
	private final static String KEY_CITY = "CITY";
	/**
	 * City Name. (Weather Webservice)
	 */
	private String city;
	/**
	 * List of the city. (Weather Webservice)
	 */
	private List<Weather> resultSearch;
	/**
	 * Hold information of the current weather.
	 */
	private TextView cityName, desc, pressure, temp, humidity;
	/**
	 * Image of the current weather.
	 */
	private ImageView icon;
	/**
	 * Search city.
	 */
	private EditText citySearch;
	/**
	 * Hold city search results.
	 */
	private ListView cityResult;
	/**
	 * Layout to show/hide on city search.
	 */
	private LinearLayout listLayout;
	/**
	 * Button search.
	 */
	private ImageButton searchCity;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setTitle(R.string.app_name);
        cityName = (TextView) findViewById(R.id.city_name);
        desc = (TextView) findViewById(R.id.desc);
        desc.requestFocus();
        pressure = (TextView) findViewById(R.id.pressure);
        temp = (TextView) findViewById(R.id.temp);
        humidity = (TextView) findViewById(R.id.humidity);
        icon = (ImageView) findViewById(R.id.icon);
        searchCity = (ImageButton) findViewById(R.id.action_search);
        searchCity.setOnClickListener(this);
        cityResult = (ListView) findViewById(R.id.city_list);
        cityResult.setOnItemClickListener(this);
        citySearch = (EditText) findViewById(R.id.city_search);
        listLayout = (LinearLayout) findViewById(R.id.list_layout);
        Button btn = (Button) findViewById(R.id.go_to_parties);
        btn.setOnClickListener(this);
        refreshWeather();
    }
	
	/**
	 * Refresh the current weather.
	 */
	public void refreshWeather()
	{
		collapse(listLayout);
		citySearch.setText("");
		SharedPreferences sharedPreferences = getSharedPreferences(PREF_FILE, 0);
        city = sharedPreferences.getString(KEY_CITY, "Paris");
		WeatherTask weatherTask = new WeatherTask(this);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
        		.Builder(this).build();
        ImageLoader.getInstance().init(config);
        collapse(listLayout);
        weatherTask.execute(new String[]{city});
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }
	
	/**
	 * AsyncTask - Get the weather data
	 * @author spo2
	 *
	 */
	private class WeatherTask extends AsyncTask<String, Void, Weather>
	{
		private Activity activity;
		
		public WeatherTask(Activity activity)
		{
			this.activity = activity;
		}
		@Override
		protected Weather doInBackground(String... params) 
		{
			Weather weather = null;
			if (NetworkTools.isNetworkAvailable(this.activity))
	        {
				weather = new Weather();
				String location = params[0];
				WeatherHttpClient weatherHttpClient = new WeatherHttpClient();
				String data = weatherHttpClient.getWeatherData(location);
				
				Gson gson = new Gson();
				weather = gson.fromJson(data, Weather.class);
	        }
			return weather;
		}

		@Override
		protected void onPostExecute(final Weather weather) {
			super.onPostExecute(weather);
			
			this.activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					if (weather != null)
					{
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
				}
			});
		}
	}
	
	/**
	 * AsyncTask - Get a list of city by name
	 * @author spo2
	 *
	 */
	private class CityTask extends AsyncTask<String, Void, City>
	{
		private Activity activity;
		
		public CityTask(Activity activity) 
		{
			this.activity = activity;
		}
		
		public Activity getActivity()
		{
			return this.activity;
		}
		
		@Override
		protected City doInBackground(String... params) 
		{
			String location = params[0];
			WeatherHttpClient weatherHttpClient = new WeatherHttpClient();
			String data = weatherHttpClient.getCityList(location);
			City city = new City();
			Gson gson = new Gson();
			city = gson.fromJson(data, City.class);
			return city;
		}

		@Override
		protected void onPostExecute(final City city) 
		{
			super.onPostExecute(city);
			this.activity.runOnUiThread(new Runnable() 
			{	
				@Override
				public void run() 
				{
					CityListAdapter cityListAdapter = new CityListAdapter(
							getActivity(), city.getWeathers());
					if (getActivity() instanceof WelcomeActivity)
					{
						WelcomeActivity wa = (WelcomeActivity) getActivity();
						wa.setResultSearch(city.getWeathers());
						wa.getCityResult().setAdapter(cityListAdapter);
						cityListAdapter.notifyDataSetChanged();
						expand(listLayout);
					}
				}
			});
		}
		
	}

	/**
	 * Expand a view
	 * @param v the view to expand
	 */
	public static void expand(final View v) 
	{
	    v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    final int targetHeight = v.getMeasuredHeight();

	    v.getLayoutParams().height = 0;
	    v.setVisibility(View.VISIBLE);
	    Animation a = new Animation()
	    {
	        @Override
	        protected void applyTransformation(float interpolatedTime, Transformation t) 
	        {
	            v.getLayoutParams().height = interpolatedTime == 1
	                    ? LayoutParams.WRAP_CONTENT
	                    : (int)(targetHeight * interpolatedTime);
	            v.requestLayout();
	        }

	        @Override
	        public boolean willChangeBounds() 
	        {
	            return true;
	        }
	    };

	    // 1dp/ms
	    a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
	    v.startAnimation(a);
	}

	/**
	 * Collapse a view
	 * @param v the view to collapse
	 */
	public static void collapse(final View v) 
	{
	    final int initialHeight = v.getMeasuredHeight();

	    Animation a = new Animation()
	    {
	        @Override
	        protected void applyTransformation(float interpolatedTime, Transformation t) 
	        {
	            if(interpolatedTime == 1){
	                v.setVisibility(View.GONE);
	            }
	            else
	            {
	                v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
	                v.requestLayout();
	            }
	        }

	        @Override
	        public boolean willChangeBounds() 
	        {
	            return true;
	        }
	    };

	    // 1dp/ms
	    a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
	    v.startAnimation(a);
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
			case R.id.action_search:
			{
				collapse(listLayout);
				if ((citySearch.getText() != null))
				{
					String searchQuery = citySearch.getText().toString();
					if ((searchQuery != "") && (searchQuery.length() > 2))
					{
						CityTask  cityTask = new CityTask(WelcomeActivity.this);
						cityTask.execute(new String[]{citySearch.getText().toString()});
					}
				}
				break;
			}
			case R.id.go_to_parties:
			{
				Intent intent = new Intent(WelcomeActivity.this, PartyActivity.class);
				startActivity(intent);
				break;
			}
			default:
				break;
			}		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) 
	{
		Weather weather = getResultSearch().get(position);
		SharedPreferences sharedPreferences = getSharedPreferences(PREF_FILE, 0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.remove(KEY_CITY);
		editor.putString(KEY_CITY, weather.getName());
		editor.commit();
		refreshWeather();
	}
	
	public ListView getCityResult()
	{
		return this.cityResult;
	}
	
	public List<Weather> getResultSearch()
	{
		return this.resultSearch;
	}
	public void setResultSearch(List<Weather> resultSearch)
	{
		this.resultSearch = resultSearch;
	}
}
