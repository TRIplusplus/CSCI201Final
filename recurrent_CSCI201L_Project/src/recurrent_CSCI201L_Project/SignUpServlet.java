package recurrent_CSCI201L_Project;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String errorMessage = "";

		JDBCTest jdb = (JDBCTest)request.getSession().getAttribute("jdb"); 
		if (jdb == null) {
			jdb = new JDBCTest();
			request.getSession().setAttribute("jdb", jdb);
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String image = request.getParameter("image");
		String email = request.getParameter("email");
		String userType = request.getParameter("userType");
		System.out.println(userType);
		// check to make sure all the fields were filled out
		if (!email.contains("@")) {
			System.out.println("does not");
			errorMessage = "Invalid email address";
			System.out.println("invalid");
			response.setContentType("text/plain");
			response.getWriter().write(errorMessage);
		}
		if (userType.equals("renter")) {
			try {
				if (!jdb.validUsernameRenter(username)) {
					errorMessage = "Username has already been chosen. Please choose another";
					response.setContentType("text/plain");
					response.getWriter().write(errorMessage);
				} else {
					Renter renter = new Renter(username, password, image, email);
					jdb.addRenter(username, password, image, email);
					request.getSession().setAttribute("loggedUser", username);
					jdb.setLoggedUser(username);
					jdb.setLoggedUserType(userType);
				}
			} catch (

			SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				if (!jdb.validUsernameLender(username)) {
					errorMessage = "Username has already been chosen. Please choose another";
					response.setContentType("text/plain");
					response.getWriter().write(errorMessage);
				} else {
					Lender lender = new Lender(username, password, image, email);
					System.out.println("new Lender made");
					jdb.addLender(username, password, image, email);
					jdb.setLoggedUser(username);
					jdb.setLoggedUserType(userType);
					request.getSession().setAttribute("loggedUser", username);
				}
			} catch (

			SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}