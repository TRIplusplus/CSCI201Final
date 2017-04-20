package recurrent_CSCI201L_Project;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DiscoverServlet
 */
@WebServlet("/DiscoverServlet")
public class DiscoverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DiscoverServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JDBCTest jdb = (JDBCTest)request.getSession().getAttribute("jdb"); 
		if (jdb == null) {
			jdb = new JDBCTest();
			request.getSession().setAttribute("jdb", jdb);
		}
		jdb.setLoggedUser(null);
		jdb.setLoggedUserType(null);
		response.sendRedirect("SearchPage.jsp?criteria=Item&search=All&location=");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
