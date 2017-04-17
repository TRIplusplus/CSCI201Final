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
@WebServlet("/RenterLoginServlet")
public class RenterLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String errorMessage = "";
		System.out.println("got here");
		JDBCTest jdb = (JDBCTest)request.getSession().getAttribute("jdb"); 
		if (jdb == null) {
			jdb = new JDBCTest();
			request.getSession().setAttribute("jdb", jdb);
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			if (!jdb.validUsernameRenter(username)) {
				if (!jdb.correctPasswordRenter(username, password)) {
					jdb.setLoggedUser(username);
					jdb.setLoggedUserType("renter");
					request.getSession().setAttribute("userType", "renter");
				} else {
					errorMessage = "Username and password do not match";
					response.setContentType("text/plain");
					response.getWriter().write(errorMessage);
				}

			} else {
				errorMessage = "Username does not exist";
				response.setContentType("text/plain");
				response.getWriter().write(errorMessage);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
