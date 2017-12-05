package weather;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

public class DBResponse {
	
	private String ip;
	private CityResponse cr;
	
	public DBResponse() {
		this.setIP();
	}
	
	public String getIP() {
		return this.ip;
	}
	
	public String getCity() {
		return this.cr.getCity().getName();
	}
	
	public String getState() {
		return this.cr.getMostSpecificSubdivision().getIsoCode();
	}
	
	private void setIP() {
		URL getIP = null;
		try {
			getIP = new URL("http://ipecho.net/plain");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(getIP.openStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String ip = null;
		try {
			ip = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.getResponse(ip);
	}
	
	private void getResponse(String ip) {
		File database = new File("GeoLite2-City.mmdb");

		DatabaseReader reader = null;
		try {
			reader = new DatabaseReader.Builder(database).build();
		} catch (IOException e) {
			e.printStackTrace();
		}

		InetAddress ipAddress = null;
		try {
			ipAddress = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		try {
			cr = reader.city(ipAddress);
			this.ip = ip;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeoIp2Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
