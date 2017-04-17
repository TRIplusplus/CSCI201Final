package recurrent_CSCI201L_Project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//import org.json.*;

public class JDBCTest {

	private static Connection conn = null;
	private static ParseConfig pc;
	private static Statement st;
	private static PreparedStatement ps;
	private static ResultSet rs;

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
				
				if (description.contains((CharSequence)search) || title.contains((CharSequence)search)) {
					Item item = new Item(lender, image, title, startDate, endDate, description, price, xcoord, ycoord); 
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
			int acceptableDistance = 50;
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
					items.add(item);
				}
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
				if (item.getID() == item2.getID()) intersection.add(item);
			}
		}
		return intersection;
	}
	
	public Item getItemByID(int ID) {
		try {
			String sql = "SELECT * FROM items WHERE id = " + ID;
	
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
				return item;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}