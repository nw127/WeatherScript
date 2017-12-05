package weather;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


public class WeatherData {
	
	private String city;
	private String state;
	private String token;
	private String weather;
	private String temp;
	private int hour;
	
	public WeatherData(String city, String state, String filename) {
		this.city = city;
		this.state = state;
		this.setToken(filename);
		if (!this.getData()) {
			System.out.println("Error occurred while fetching weather data.");
			System.exit(0);
		}
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getWeather() {
		return this.weather;
	}
	
	public String getTemp() {
		return this.temp;
	}
	
	public int getHour() {
		return this.hour;
	}
	
	public String choosePicture() {
		final String[] cloudy = new String[] {"Partly Cloudy", "Scattered Clouds", "Overcast, Cloudy",  "Mostly Cloudy"};
		final String[] rain = new String[] {"Rain", "Rain Showers", "Rain Mist", "Mist", "Drizzle"};
		final String[] fog = new String[] {"Patches of Fog", "Partial Fog", "Shallow Fog", "Fog"};
		final String[] snow = new String[] {"Snow Mist", "Snow Showers", "Low Drifting Snow", "Snow", "Snow Grains", "Blowing Snow"};
		final String[] thunderstorm = new String[] {"Thunderstorms with Hail", "Thunderstorm", "Thunderstorms and Rain"};
		
		String w = this.weather;
		if (this.weather.substring(0, 5).equals("Heavy") || this.weather.substring(0, 5).equals("Light")) {
			w = this.weather.substring(6);
		}
		
		String time = getTimeOfDay();
		
		if (Arrays.asList(cloudy).contains(w)) {
			if (time.equals("day")) return "cloudy_day.jpg";
			if (time.equals("evening")) return "cloudy_evening.jpg";
			if (time.equals("night")) return "cloudy_night.jpg";
		}
		if (Arrays.asList(rain).contains(w)) {
			if (time.equals("day")) return "rain_day.jpg";
			if (time.equals("evening")) return "rain_evening.jpg";
			if (time.equals("night")) return "rain_night.jpg";
		}
		if (Arrays.asList(fog).contains(w)) {
			if (time.equals("day")) return "fog_day.jpg";
			if (time.equals("evening")) return "fog_evening.jpg";
			if (time.equals("night")) return "fog_night.jpg";
		}
		if (Arrays.asList(snow).contains(w)) {
			if (time.equals("day")) return "snow_day.jpg";
			if (time.equals("evening")) return "snow_evening.jpg";
			if (time.equals("night")) return "snow_night.jpg";
		}
		if (Arrays.asList(thunderstorm).contains(w)) {
			if (time.equals("day")) return "thunderstorm_day.jpg";
			if (time.equals("evening")) return "thunderstorm_evening.jpg";
			if (time.equals("night")) return "thunderstorm_night.jpg";
		}
		
		if (time.equals("day")) {
			return "clear_day.jpg";
		} else if (time.equals("evening")) {
			return "clear_evening.jpg";
		} else {
			return "clear_night.jpg";
		}
	}
	
	private String getTimeOfDay() {
		if (this.hour >= 7 && this.hour <= 15) return "day";
		if (this.hour >= 16 && this.hour <= 21) return "evening";
		return "night";
	}
	
	private boolean getData() {
		String url = "http://api.wunderground.com/api/" + this.token +"/conditions/q/" +
				this.state + "/" + this.city + ".json";
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		String jsonString = null;
		try {
			jsonString = EntityUtils.toString(response.getEntity());
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		JSONObject j = null;
		try {
			j = new JSONObject(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		try {
			this.weather = j.getJSONObject("current_observation").get("weather").toString();
			this.temp = j.getJSONObject("current_observation").get("temp_f").toString();
			this.hour = LocalDateTime.now().getHour();
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private void setToken(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			this.token = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
}
