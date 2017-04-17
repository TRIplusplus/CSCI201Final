package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.io.*;
import org.json.*;
import java.net.*;

public class A5_db {
	//maps a username to a user object
	protected Map<String, User> usersMap;
	//maps a movie title to a movie object
	protected Map<String, Movie> moviesMap;
	//maps a case insensitive username to a list of case sensitive matches
	protected Map<String, Set<User>> usernameToUsers;
	protected Map<String, Set<User>> firstNameToUsers;
	protected Map<String, Set<User>> lastNameToUsers;
	//maps a username to a list of followers
	private Map<String, Set<String>> usernameToFollowers;
	//maps an actor to a list of movies
	protected Map<String, Set<Movie>> actorToMovies;
	//maps a genre to a list of movies
	//protected Map<String, Set<Movie>> genreToMovies;
	//maps a case insensitive movie title to a list of case sensitive matches
	protected Map<String, Set<Movie>> titleToMovies;
	//genres
	//private List<String> genresList;
	//actions
	//private List<String> actionsList;
	//maps an action to the icon associated with it
	//unfortunately doing this makes the assumption we only have the actions: rated, liked, disliked and watched
	protected Map<String, String> actionToIcon;

	Connection conn = null;
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public A5_db() throws Exception{
		usersMap = new HashMap<>();
		moviesMap = new HashMap<>();
		usernameToUsers = new HashMap<>();
		firstNameToUsers = new HashMap<>();
		lastNameToUsers = new HashMap<>();
		usernameToFollowers = new HashMap<>();
		actorToMovies = new HashMap<>();
		//genreToMovies = new HashMap<>();
		titleToMovies = new HashMap<>();
		//genresList = new ArrayList<>();
		//actionsList = new ArrayList<>();
		actionToIcon = new HashMap<>();
		//add the actions and icons to the actionToIconMap
		createActionIconMap();

		//extract movie info
		movieinfo();
		//extract user information
		userinfo();

	}
	public void movieinfo() throws Exception{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_a5db?user=root&password=shp93128&useSSL=false");

			//execute a query
			st = conn.createStatement();
			String sql_ = "SELECT * FROM my_a5db.movies;";

			rs = st.executeQuery(sql_);
			while(rs.next()){
				try{
					Movie m = new Movie();
					String movid = rs.getString("imdb_id");
					URL omdb = new URL("http://www.omdbapi.com/?i="+movid);
					BufferedReader in = new BufferedReader(new InputStreamReader(omdb.openStream()));
					String inputLine = in.readLine();
					//System.out.println(inputLine);
					String title = rs.getString(StringConstants.TITLE);
					m.setTitle(title);
					//while ((inputLine = in.readLine()) != null)
					JSONObject ja = new JSONObject(inputLine);
				
					
					String year = ja.getString("Year");
					m.setYear(Integer.parseInt(year));
					String director = ja.getString("Director");
					//System.out.println(inputLine);
					m.setDirector(director);
					String description = ja.getString("Plot");
					m.setDescription(description);
					//parse writers
					String writers = ja.getString("Writer");
					parseWriter(m,writers);
					//parse actors
					String actors = ja.getString("Actors");
					parseActor(m,actors);
					//image
					String image = ja.getString("Poster");
					m.setImage(image);
					//number ratings
					int numRating = rs.getInt("rating_total");
					m.setRatingCount(numRating);
					//total ratings
					int totalrating = rs.getInt("rating_count");
					m.setRatingTotal(totalrating);
					//parse genres
					String genres = rs.getString("Genre");

					//movies map
					moviesMap.put(title, m);
					//add the movie to the correct title's set
					addObjectToMap(titleToMovies, m.getTitle().toLowerCase(), m);
					in.close();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}  catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}

	public void parseGenres(Movie m, String s){
		while(s.indexOf(",")!=-1){
			m.addGenre(s.substring(0, s.indexOf(",")));	
			s=s.substring(s.indexOf(",")+1);
		}
	}
	public void parseWriter(Movie m, String s){
		while(s.indexOf(",")!=-1){
			m.addWriter(s.substring(0, s.indexOf(",")));	
			s=s.substring(s.indexOf(",")+1);
		}
	}
	public void parseActor(Movie m, String s) throws Exception{
	//	System.out.println("s: "+s);
		while(s.indexOf(",")!=-1){
			Actor ac = new Actor();
			String act_name = s.substring(0, s.indexOf(",")).trim();

			//System.out.println("act_name: "+act_name);
			//first name , last name
			String fname = act_name.substring(0, act_name.indexOf(" "));
		//	System.out.println("fname: "+fname);
			ac.setFName(fname);
			String lname = act_name.substring(act_name.indexOf(" ")+1);
		//	System.out.println("lname: "+lname);
			ac.setLName(lname);
			//image
			String imageURL = actorImage(fname,lname);
			ac.setImage(imageURL);
			s=s.substring(s.indexOf(",")+1);
		//	System.out.println("s1: "+s);
			m.addActor(ac);
			addObjectToMap(actorToMovies, ac.getFullName().toLowerCase(), m);
		}
	}

	public String actorImage(String fn,String ln) throws Exception{
		String url = null;
		fn = fn.trim();
		ln = ln.trim();
		//System.out.println("fn: "+fn);
		//System.out.println("ln: "+ln);
		String nnn = "https://api.themoviedb.org/3/search/person?api_key=6fcdd94b2c3de6dca333cce3a2789227&language=en-US&query="+fn+"+"+ln+"+&page=1&include_adult=false";
	//	System.out.println("url"+nnn);
		URL infoUrl = new URL("https://api.themoviedb.org/3/search/person?api_key=6fcdd94b2c3de6dca333cce3a2789227&language=en-US&query="+fn+"+"+ln+"+&page=1&include_adult=false");

		BufferedReader in = new BufferedReader(new InputStreamReader(infoUrl.openStream()));
		String inputLine=in.readLine();
		//System.out.println("2"+inputLine);
		JSONObject ja = new JSONObject(inputLine);
		JSONArray jarry = ja.getJSONArray("results");
		if(jarry.getJSONObject(0).isNull("profile_path"))
		{
			url = StringConstants.NO_IMAGE + StringConstants.Not_available;
		}else {
			String image_path = jarry.getJSONObject(0).getString("profile_path");
			//JSONObject jarr = new JSONObject(ja.get("results"));
			//String image_path = jarr.getString("profile_path");
			//System.out.println("3"+image_path);
			url = "https://image.tmdb.org/t/p/w500"+image_path;
		}
		//https://image.tmdb.org/t/p/w500/
		return url;
	}

	public void userinfo(){

		try {
			//create connection
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_a5db?user=root&password=shp93128&useSSL=false");

			//execute a query
			st = conn.createStatement();
			String sql_us = "SELECT * FROM my_a5db.users;";

			rs = st.executeQuery(sql_us);

			//extract data
			while (rs.next()) {
				try {
					User u = new User();
					String username = rs.getString(StringConstants.USERNAME);
					u.setUsername(username);
					//id
					int id = rs.getInt("user_id");
					u.setId(id);
					String password = rs.getString("password");
					u.setPassword(password);
					String fname = rs.getString("fname");
					u.setFName(fname);
					String lname = rs.getString("lname");
					u.setLName(lname);
					String url = rs.getString("image");
					u.setImage(url);
					String json_following = rs.getString("following");
					//System.out.println("j following: " + json_following);
					
					JSONArray jf = new JSONArray(json_following);
					//System.out.println("length:" + jf.length());
					for(int i=0;i<jf.length();i++){
						if(!jf.isNull(i)){
						String following = (String) jf.getJSONObject(i).get("username");
						u.addFollowing(following);
						addObjectToMap(usernameToFollowers, following, u.getUsername());
						}
					}
					String json_feed = rs.getString("feed");
					//System.out.println("jf:"+json_feed);
					JSONArray jfe = new JSONArray(json_feed);
				
					for(int j=0;j<jfe.length();j++){
						Event et = new Event();
						String action = (String) jfe.getJSONObject(j).get("action");
						String movie = (String) jfe.getJSONObject(j).get("movie");
						String rating = (String) jfe.getJSONObject(j).get("rating");
						if(!action.equals("null")){
							et.setAction(action);

							//movie
							et.setMovie(moviesMap.get(movie));
							if(rating.equals("null")){
								et.setRating(0);
							}
							else {
								et.setRating(Integer.parseInt(rating));
							}
						}
						u.addEvent(et);
					}
					usersMap.put(username, u);

					//add the user with their lowercased username to the usernameToUsers map
					addObjectToMap(usernameToUsers, u.getUsername().toLowerCase(), u);
					addObjectToMap(firstNameToUsers, u.getFName().toLowerCase(), u);
					addObjectToMap(lastNameToUsers, u.getLName().toLowerCase(), u);

				} catch (JSONException e) {

					e.printStackTrace();
				}

			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}

	protected <T> void addObjectToMap(Map<String, Set<T>> map, String key, T object){
		//if the map already contains the provided key, retrieve the value (which is a set) and add the new object
		if (map.containsKey(key)) map.get(key).add(object);
		//else create a new set with the object, and add the provided key and created set to the map
		else{
			Set<T> objects = new HashSet<>();
			objects.add(object);
			map.put(key, objects);
		}
	}

	private void createActionIconMap(){
		actionToIcon.put("0"+StringConstants.ACTION_RATED, StringConstants.RATING_0_ICON);
		actionToIcon.put("1"+StringConstants.ACTION_RATED, StringConstants.RATING_1_ICON);
		actionToIcon.put("2"+StringConstants.ACTION_RATED, StringConstants.RATING_2_ICON);
		actionToIcon.put("3"+StringConstants.ACTION_RATED, StringConstants.RATING_3_ICON);
		actionToIcon.put("4"+StringConstants.ACTION_RATED, StringConstants.RATING_4_ICON);
		actionToIcon.put("5"+StringConstants.ACTION_RATED, StringConstants.RATING_5_ICON);
		actionToIcon.put(StringConstants.ACTION_LIKED, StringConstants.LIKED_ICON);
		actionToIcon.put(StringConstants.ACTION_DISLIKED, StringConstants.DISLIKED_ICON);
		actionToIcon.put(StringConstants.ACTION_WATCHED, StringConstants.WATCHED_ICON);
	}
	public void addfollowing(User u) throws JSONException{
		
		//add to loggedin user's list
		
		//write back to database
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_a5db?user=root&password=shp93128&useSSL=false");
			
			//execute a query
			st = conn.createStatement();
		//	String adding = u.getUsername();
			String adding = "jack";
			
			String select_user = "select u.following from users u where u.user_id = "+"'" + 2 + "'";
			rs = st.executeQuery(select_user);
			rs.next();
			
			String tochange = rs.getString("following");
			JSONArray js = new JSONArray(tochange);
			JSONObject jo = new JSONObject();
			jo.put("following", adding);
			js.put(jo);
			String updater = "update users u set u.following ="+"'"+ js.toString() + "'" +" where u.user_id = "+"'" + 2 +"'";
			System.out.println(updater);
			st.executeUpdate(updater);

		}catch (SQLException sqle) {
			System.out.println ("sssSQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
	public static void main(String[] args) throws Exception{
		A5_db a = new A5_db();
		//a.addfollowing();
	}
}

