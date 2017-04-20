package recurrent_CSCI201L_Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.*;

public class LocationParser {

	private String strNum = null;
	private String routeName = null;
	private String cityName = null;
	private String state = null;
	private String zipCode = null;

	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public LocationParser(String str, String rou, String cit, String sta, String zip) throws IOException{
		String url = "https://maps.googleapis.com/maps/api/geocode/"
				+ "json?address="+str+"+"+rou+","+"+"+cit+","+"+"+sta+"&"
				+ "key=AIzaSyC-fSJYjRrDlEQTwVphjJc8wzBydKGzt88";
		
		URL googlemap;
		try {
			googlemap = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(googlemap.openStream()));
			String inputLine = readAll(in);
			
			//get gps
			//lat
			System.out.println("lat: "+lat(inputLine)); 
			//lng
		
			System.out.println("lng: "+lng(inputLine)); 
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String lng(String json){
		String lng = null;
		try {
			
			JSONObject jo = new JSONObject(json);
			JSONArray ja = jo.getJSONArray("results");
			JSONObject jo_geo = (JSONObject) ja.getJSONObject(0).get("geometry");
			JSONObject jo_loc = (JSONObject) jo_geo.get("location");
			Double d_lng = jo_loc.getDouble("lng");
			lng = d_lng.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lng;
	}
	public String lat(String json){
		String lat = null;
		try {
			
			JSONObject jo = new JSONObject(json);
			JSONArray ja = jo.getJSONArray("results");
			JSONObject jo_geo = (JSONObject) ja.getJSONObject(0).get("geometry");
			JSONObject jo_loc = (JSONObject) jo_geo.get("location");
			Double d_lat = jo_loc.getDouble("lat");
			lat = d_lat.toString();
			//System.out.println(jo_loc.getDouble("lng"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lat;
	}
	public static void main(String [] args) throws IOException{
		Integer strnum = 3150;
		String routename = "wishire+blvd";
		String city = "los+"+"angeles";
		String state = "ca";
		Integer zip = 90010;
		
		new LocationParser(strnum.toString(),routename,city,state,zip.toString());
	}
}
