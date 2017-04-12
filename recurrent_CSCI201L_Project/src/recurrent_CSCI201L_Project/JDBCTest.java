package recurrent_CSCI201L_Project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

	public void addRenter(String username, String password, String email) {
		System.out.println("got here");
		String sql = "INSERT INTO renters(username, password1, email) VALUES(?,?,?)";

		try (Connection conn = JDBCTest.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, email);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "boo");
		}
	}

	public void addLender(String username, String password, String email) {
		String sql = "INSERT INTO lenders(username, password1, email) VALUES(?,?,?)";

		try (Connection conn = JDBCTest.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, email);
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
}