package recurrent_CSCI201L_Project;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LendNewItemServlet
 */
@WebServlet("/LendNewItemServlet")
public class LendNewItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LendNewItemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JDBCTest jdb = (JDBCTest)request.getSession().getAttribute("jdb");
		String username = jdb.getLoggedUser();
		String title = request.getParameter("name");
		String image = request.getParameter("image");
		Date startdate = Date.valueOf(request.getParameter("start_date"));
		Date enddate = Date.valueOf(request.getParameter("end_date"));
		String description = request.getParameter("description");
		double price = Double.parseDouble(request.getParameter("price"));
		String address = request.getParameter("address");
		System.out.println("adding new item");
		Item item = new Item(username, image, title, startdate, enddate, description, price, 0, 0);
		jdb.addItem(item);
		
		response.sendRedirect("LenderHomePage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
