package recurrent_CSCI201L_Project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LocationParser {
	
	private String strNum = null;
	private String routeName = null;
	private String cityName = null;
	private String state = null;
	      
	private String zipCode = null;
	
	public LocationParser(String str, String rou, String cit, String sta, String zip){
	String url = "https://maps.googleapis.com/maps/api/geocode/"
			+ "json?address="+str+"+"+rou+","+"+"+cit+","+"+"+sta+"&"
			+ "key=AIzaSyC-fSJYjRrDlEQTwVphjJc8wzBydKGzt88";
	System.out.println(url);
//	URL googlemap = new URL(url);
//	BufferedReader in = new BufferedReader(new InputStreamReader(omdb.openStream()));
//	String inputLine = in.readLine();
	}
	public void main(String [] args){
		Integer strnum = 3150;
		String routename = "wishire blvd";
		String city = "los+"+"angeles";
		String state = "ca";
		Integer zip = 90010;
		
		new LocationParser(strnum.toString(),routename,city,state,zip.toString());
	}
}
