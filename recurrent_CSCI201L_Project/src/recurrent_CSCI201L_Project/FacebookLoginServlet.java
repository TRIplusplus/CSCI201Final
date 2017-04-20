package recurrent_CSCI201L_Project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/FacebookLoginServlet")
public class FacebookLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JDBCTest jdb = new JDBCTest();
		String username = request.getParameter("username");
		String image = request.getParameter("image");
		String type = request.getParameter("type");
		String password = request.getParameter("password");
		String email = "n/a";

		if (type.equals("lender")) {
			// Lender lender = new Lender(username, password, email);
			jdb.addLender(username, password, image, email);
			request.getSession().setAttribute("loggedUser", username);
		} else {
			// Renter renter = new Renter(username, password, email);
			jdb.addRenter(username, password, image, email);
			request.getSession().setAttribute("loggedUser", username);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}