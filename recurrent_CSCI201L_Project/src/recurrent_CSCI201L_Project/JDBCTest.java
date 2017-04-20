package recurrent_CSCI201L_Project;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JDBCTest {

	private static Connection conn = null;
	private static ParseConfig pc;
	private static Statement st;
	private static PreparedStatement ps;
	private static ResultSet rs;
	String loggedUser;
	String loggedUserType;

	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public JDBCTest() throws IOException {

		pc = new ParseConfig();

		st = null;
		ps = null;
		rs = null;
		// connect();
	}

	public static Connection connect() {
		try {
			String connection = "jdbc:mysql://";
			String ipAddress = pc.getAddress();
			connection += ipAddress;
			connection += "/";

			String dataBase = pc.getDataBase();
			connection += dataBase;
			connection += "?user=";

			String user = pc.getuser();
			connection += user;
			connection += "&password=";

			String password = pc.getPassword();
			connection += password;
			connection += "&useSSL=false";

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(connection);

			return conn;
		} catch (SQLException sqle) {
			// System.out.println("SQLException: " + sqle.getMessage());
			sqle.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ClassNotFoundException: " + cnfe.getMessage());
		}
		return conn;
	}

	public String getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(String username) {
		this.loggedUser = username;
	}

	public String getLoggedUserType() {
		return loggedUserType;
	}

	public void setLoggedUserType(String userType) {
		this.loggedUserType = userType;
	}

	public void addRenter(String username, String password, String image, String email) {
		System.out.println("got here");
		String sql = "INSERT INTO renters(username, password1, image, email) VALUES(?,?,?,?)";

		try (Connection conn = JDBCTest.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, image);
			pstmt.setString(4, email);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "boo");
		}
	}

	public void addLender(String username, String password, String image, String email) {
		String sql = "INSERT INTO lenders(username, password1, image, email) VALUES(?,?,?,?)";

		try (Connection conn = JDBCTest.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, image);
			pstmt.setString(4, email);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "boo");
		}
	}

	public void addItem(Item item) {
		String sql = "INSERT INTO items(lender, renter, title, image, startdate, enddate, description, price, xcoord, ycoord) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";

		try (Connection conn = JDBCTest.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, item.getLender());
			pstmt.setString(2, item.getRenter());
			pstmt.setString(3, item.getTitle());
			pstmt.setString(4, item.getImage());
			pstmt.setDate(5, item.getStartDate());
			pstmt.setDate(6, item.getEndDate());
			pstmt.setString(7, item.getDescription());
			pstmt.setDouble(8, item.getPrice());
			pstmt.setDouble(9, item.getX());
			pstmt.setDouble(10, item.getY());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "boo");
		}
	}

	public boolean validUsernameRenter(String username_) throws SQLException {

		String sql = "SELECT username FROM renters WHERE username = '" + username_ + "'";

		Connection conn = JDBCTest.connect();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql); // execute the query, and get a
		// java resultset

		// if this ID already exists, we quit
		if (rs.absolute(1)) {
			// System.out.println("invalid");
			return false;
		} else {
			// System.out.println("good");
			return true;
		}
	}

	public boolean validUsernameLender(String username_) throws SQLException {

		String sql = "SELECT username FROM lenders WHERE username = '" + username_ + "'";

		Connection conn = JDBCTest.connect();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql); // execute the query, and get a
		// java resultset

		// if this ID already exists, we quit
		if (rs.absolute(1)) {
			// System.out.println("invalid");
			return false;
		} else {
			// System.out.println("good");
			return true;
		}
	}

	public boolean correctPasswordRenter(String username_, String password_) throws SQLException {
		String sql = "SELECT username FROM renters WHERE username = '" + username_ + "' AND password1 ='" + password_
				+ "'";

		Connection conn = JDBCTest.connect();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql); // execute the query, and get a
		// java resultset

		// if this ID already exists, we quit
		if (rs.absolute(1)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean correctPasswordLender(String username_, String password_) throws SQLException {
		String sql = "SELECT username FROM lenders WHERE username = '" + username_ + "' AND password1 ='" + password_
				+ "'";

		Connection conn = JDBCTest.connect();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql); // execute the query, and get a
		// java resultset

		// if this ID already exists, we quit
		if (rs.absolute(1)) {
			return false;
		} else {
			return true;
		}
	}

	//getters
	public User getUser(String username, String type) {
		try {
			String sql = "SELECT * FROM " + type + "s WHERE username = '" + username + "'";

			Connection conn = JDBCTest.connect();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				System.out.println("user found");
				username = rs.getString(1);
				String password = rs.getString(2);
				String image = rs.getString(3);
				String email = rs.getString(4);
				if (type.equals("renter")) return new Renter(username, password, image, email);
				else return new Lender(username, password, image, email); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("user not found");
		return null;
	}

	public void sendMessage(Message message) {
		String sql = "INSERT INTO messages(sender, receiver, title, message, unread, date) VALUES(?,?,?,?,?,?)";
		try (Connection conn = JDBCTest.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, message.getSender());
			pstmt.setString(2, message.getReceiver());
			pstmt.setString(3, message.getTitle());
			pstmt.setString(4, message.getMessage());
			pstmt.setBoolean(5, true);
			pstmt.setDate(6, Date.valueOf(LocalDate.now()));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void readMessage(int ID) {
		String sql = "UPDATE messages SET unread = '0' WHERE id = " + ID;
		try (Connection conn = JDBCTest.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "boo");
		}
	}

	public void rentItem(int ID, String renter) {
		String sql = "UPDATE items SET renter = '" + renter + "' WHERE id = " + ID;
		try (Connection conn = JDBCTest.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public ArrayList<User> searchUsername(String search) {
		try {
			String sql = "SELECT * FROM lenders";

			Connection conn = JDBCTest.connect();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			ArrayList<User> users = new ArrayList<User>();

			while (rs.next()) {
				String username = rs.getString(1);
				String password = rs.getString(2);
				String image = rs.getString(3);
				String email = rs.getString(4);
				Lender user = new Lender(username, password, image, email);
				if (username.toLowerCase().contains((CharSequence) search.toLowerCase())
						|| search.equalsIgnoreCase("Lenders")) 
					users.add(user); 
			}

			sql = "SELECT * FROM renters";
			rs = st.executeQuery(sql);

			while (rs.next()) {
				String username = rs.getString(1);
				String password = rs.getString(2);
				String image = rs.getString(3);
				String email = rs.getString(4);
				Renter user = new Renter(username, password, image, email);
				if (username.toLowerCase().contains((CharSequence) search.toLowerCase())
						|| search.equalsIgnoreCase("Renters")) 
					users.add(user); 
			}

			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Message> getMessagesForUser(String username) {
		try {
			String sql = "SELECT * FROM messages WHERE receiver = '" + username + "'";

			Connection conn = JDBCTest.connect();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			ArrayList<Message> messages = new ArrayList<Message>();

			while (rs.next()) {
				int id = rs.getInt(1);
				String sender = rs.getString(2);
				String receiver = rs.getString(3);
				String title = rs.getString(4);
				String text = rs.getString(5);
				boolean read = !(rs.getBoolean(6));
				Date date = rs.getDate(7);

				Message newMessage = new Message(sender, receiver, title, text);
				newMessage.setDate(date);
				if (read) newMessage.markAsRead();
				newMessage.setID(id);
				messages.add(newMessage);
			}

			return messages;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public int countUnreadMessages(String username) {
		ArrayList<Message> messages = getMessagesForUser(username);
		int count = 0;
		if (messages != null) {
			for (Message message: messages) {
				if (!message.isRead()) count++;
			}
		}
		return count;
	}

	public ArrayList<Item> getItemsForLender(String username) {
		try {
			String sql = "SELECT * FROM items WHERE lender = '" + username + "'";

			Connection conn = JDBCTest.connect();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			ArrayList<Item> items = new ArrayList<Item>();

			while (rs.next()) {
				int id = rs.getInt(1);
				String lender = rs.getString(2);
				String renter = rs.getString(3);
				String title = rs.getString(4);
				String image = rs.getString(5);
				Date startDate = rs.getDate(6);
				Date endDate = rs.getDate(7);
				String description = rs.getString(8);
				Double price = rs.getDouble(9);
				Double xcoord = rs.getDouble(10);
				Double ycoord = rs.getDouble(11);

				Item item = new Item(lender, image, title, startDate, endDate, description, price, xcoord, ycoord); 
				item.setID(id);
				item.setRenter(renter);
				items.add(item);
			}

			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Item> getItemsForRenter(String username) {
		try {
			String sql = "SELECT * FROM items WHERE renter = '" + username + "'";

			Connection conn = JDBCTest.connect();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			ArrayList<Item> items = new ArrayList<Item>();

			while (rs.next()) {
				int id = rs.getInt(1);
				String lender = rs.getString(2);
				String renter = rs.getString(3);
				String title = rs.getString(4);
				String image = rs.getString(5);
				Date startDate = rs.getDate(6);
				Date endDate = rs.getDate(7);
				String description = rs.getString(8);
				Double price = rs.getDouble(9);
				Double xcoord = rs.getDouble(10);
				Double ycoord = rs.getDouble(11);

				Item item = new Item(lender, image, title, startDate, endDate, description, price, xcoord, ycoord); 
				item.setID(id);
				item.setRenter(renter);
				items.add(item);
			}

			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Item> getItemsForSearch(String search) {
		try {
			String sql = "SELECT * FROM items";

			Connection conn = JDBCTest.connect();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			ArrayList<Item> items = new ArrayList<Item>();

			while (rs.next()) {
				int id = rs.getInt(1);
				String lender = rs.getString(2);
				String renter = rs.getString(3);
				String title = rs.getString(4);
				String image = rs.getString(5);
				Date startDate = rs.getDate(6);
				Date endDate = rs.getDate(7);
				String description = rs.getString(8);
				Double price = rs.getDouble(9);
				Double xcoord = rs.getDouble(10);
				Double ycoord = rs.getDouble(11);

				if (description.toLowerCase().contains((CharSequence)search.toLowerCase()) 
						|| title.toLowerCase().contains((CharSequence)search.toLowerCase())) {
					Item item = new Item(lender, image, title, startDate, endDate, description, price, xcoord, ycoord); 
					item.setID(id);
					item.setRenter(renter);
					items.add(item);
				}
			}

			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Item> getItemsNearLocation(double x, double y) {
		try {
			int acceptableDistance = 1;
			String sql = "SELECT * FROM items";

			Connection conn = JDBCTest.connect();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			ArrayList<Item> items = new ArrayList<Item>();

			while (rs.next()) {
				int id = rs.getInt(1);
				String lender = rs.getString(2);
				String renter = rs.getString(3);
				String title = rs.getString(4);
				String image = rs.getString(5);
				Date startDate = rs.getDate(6);
				Date endDate = rs.getDate(7);
				String description = rs.getString(8);
				Double price = rs.getDouble(9);
				Double xcoord = rs.getDouble(10);
				Double ycoord = rs.getDouble(11);

				if (Math.sqrt((xcoord-x)*(xcoord-x) + (ycoord-y)*(ycoord-y)) < acceptableDistance) {
					Item item = new Item(lender, image, title, startDate, endDate, description, price, xcoord, ycoord);
					item.setID(id);
					item.setRenter(renter);
					items.add(item);
				}
			}

			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Item> getAllItems() {
		try {
			String sql = "SELECT * FROM items";

			Connection conn = JDBCTest.connect();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			ArrayList<Item> items = new ArrayList<Item>();

			while (rs.next()) {
				int id = rs.getInt(1);
				String lender = rs.getString(2);
				String renter = rs.getString(3);
				String title = rs.getString(4);
				String image = rs.getString(5);
				Date startDate = rs.getDate(6);
				Date endDate = rs.getDate(7);
				String description = rs.getString(8);
				Double price = rs.getDouble(9);
				Double xcoord = rs.getDouble(10);
				Double ycoord = rs.getDouble(11);

				Item item = new Item(lender, image, title, startDate, endDate, description, price, xcoord, ycoord);
				item.setID(id);
				item.setRenter(renter);
				items.add(item);

			}

			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Item> getItemsBySearchAndLocation(String search, double x, double y) {
		ArrayList<Item> searchList = getItemsForSearch(search);
		ArrayList<Item> locationList = getItemsNearLocation(x, y);
		ArrayList<Item> intersection = new ArrayList<Item>();

		for (Item item : searchList) {
			for (Item item2: locationList) {
				if (item.getID() == item2.getID()) {
					intersection.add(item);
					break;
				}
			}
		}
		return intersection;
	}

	public ArrayList<Item> getItemstoDisplayonMap(){

		try {
			String sql = "SELECT * FROM items";
			Connection conn = JDBCTest.connect();
			Statement st;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			ArrayList<Item> items = new ArrayList<Item>();

			while (rs.next()) {
				int id = rs.getInt(1);
				String lender = rs.getString(2);
				String renter = rs.getString(3);
				String title = rs.getString(4);
				String image = rs.getString(5);
				Date startDate = rs.getDate(6);
				Date endDate = rs.getDate(7);
				String description = rs.getString(8);
				Double price = rs.getDouble(9);
				Double xcoord = rs.getDouble(10);
				Double ycoord = rs.getDouble(11);
				Item item = new Item(lender, image, title, startDate, endDate, description, price, xcoord, ycoord); 

				item.setID(id);
				item.setRenter(renter);
				items.add(item);
			}
			return items;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String getLocationJson(String zip){
		String url = "https://maps.googleapis.com/maps/api/geocode/"
				+ "json?address="+zip+"&"
				+ "key=AIzaSyC-fSJYjRrDlEQTwVphjJc8wzBydKGzt88";
		//	System.out.println(url);
		URL googlemap;
		try {
			googlemap = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(googlemap.openStream()));
			String inputLine = readAll(in);

			return inputLine;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	public String setlng(String json){
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
	public String setlat(String json){
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



	public Item getItemByID(int ID) {
		try {
			String sql = "SELECT * FROM items WHERE id = " + ID;

			Connection conn = JDBCTest.connect();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt(1);
				String lender = rs.getString(2);
				String renter = rs.getString(3);
				String title = rs.getString(4);
				String image = rs.getString(5);
				Date startDate = rs.getDate(6);
				Date endDate = rs.getDate(7);
				String description = rs.getString(8);
				Double price = rs.getDouble(9);
				Double xcoord = rs.getDouble(10);
				Double ycoord = rs.getDouble(11);

				Item item = new Item(lender, image, title, startDate, endDate, description, price, xcoord, ycoord); 
				item.setRenter(renter);
				item.setID(id);
				return item;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Message getMessageByID(int ID) {
		try {
			String sql = "SELECT * FROM messages WHERE id = " + ID;

			Connection conn = JDBCTest.connect();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);


			while (rs.next()) {
				int id = rs.getInt(1);
				String sender = rs.getString(2);
				String receiver = rs.getString(3);
				String title = rs.getString(4);
				String text = rs.getString(5);
				boolean read = !(rs.getBoolean(6));
				Date date = rs.getDate(7);

				Message message = new Message(sender, receiver, title, text);
				message.setDate(date);
				if (read) message.markAsRead();
				message.setID(id);
				return message;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//4/20
	public String json_Geo(String itemName){
		try {
			ArrayList<Item> items = new ArrayList<Item>();
			items = this.getAllItemstoDisplayonMap(itemName);
			System.out.println(items.size());

			JSONArray ja_location = new JSONArray();
			for(int i=0;i<items.size();i++){
				JSONObject js_location = new JSONObject();
				js_location.put("lat", items.get(i).getX());
				js_location.put("lng", items.get(i).getY());
				ja_location.put(js_location);
			}
			JSONObject start_location = new JSONObject();
			start_location.put("locations", ja_location);
			//	System.out.println(start_location.toString());
			return start_location.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Item> getAllItemstoDisplayonMap(String itemName){

		try {
			System.out.println(itemName);
			String sql = "SELECT * FROM recurrent.items where title = '"+ itemName+"'";
			Connection conn = JDBCTest.connect();
			Statement st;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			ArrayList<Item> items = new ArrayList<Item>();

			while (rs.next()) {
				int id = rs.getInt(1);
				String lender = rs.getString(2);
				String renter = rs.getString(3);
				String title = rs.getString(4);
				String image = rs.getString(5);
				Date startDate = rs.getDate(6);
				Date endDate = rs.getDate(7);
				String description = rs.getString(8);
				Double price = rs.getDouble(9);
				Double xcoord = rs.getDouble(10);
				Double ycoord = rs.getDouble(11);
				Item item = new Item(lender, image, title, startDate, endDate, description, price, xcoord, ycoord); 

				item.setID(id);
				item.setRenter(renter);
				items.add(item);
			}
			return items;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	public String search_item_near_location_json_display(ArrayList<Item> item){


		String json = null;
		try {
			JSONArray ja = new JSONArray();
			for(Item i : item){
				JSONObject jo = new JSONObject();
				jo.put("lat", i.getX().toString());
				jo.put("lng", i.getY().toString());
				ja.put(jo);
			}
			JSONObject jo1 = new JSONObject();
			jo1.put("locations", ja);
			System.out.println(jo1.toString());
			return jo1.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
