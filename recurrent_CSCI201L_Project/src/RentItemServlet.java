

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import recurrent_CSCI201L_Project.Item;
import recurrent_CSCI201L_Project.JDBCTest;

/**
 * Servlet implementation class RentItemServlet
 */
@WebServlet("/RentItemServlet")
public class RentItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RentItemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JDBCTest jdb = (JDBCTest)request.getSession().getAttribute("jdb");
		String username = jdb.getLoggedUser();
		int id = Integer.valueOf(request.getParameter("id"));
		jdb.rentItem(id, username);
		
		response.sendRedirect("RenterHomePage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
