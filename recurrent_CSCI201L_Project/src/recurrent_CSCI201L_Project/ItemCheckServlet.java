package recurrent_CSCI201L_Project;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ItemCheckServlet
 */
@WebServlet("/ItemCheckServlet")
public class ItemCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JDBCTest jdb = (JDBCTest)request.getSession().getAttribute("jdb");
		String username = jdb.getLoggedUser();
		int num = Integer.valueOf(request.getParameter("count"));
		ArrayList<Item> items = jdb.getAllItems();
		int newCount = items.size();
		System.out.println(num + ", " + newCount);
		String jsonObject;
		if (newCount > num) {
			Item newest = items.get(items.size()-1);
			jsonObject = "{\"newchange\": \"" + true + "\", \"num\": \"" + newCount + "\", \"title\": \"" + newest.getTitle() + "\", \"image\": \"" + newest.getImage() + "\", \"description\": \"" + newest.getDescription() + "\", \"sender\": \"" + newest.getLender() + "\", \"id\": \"" + newest.getID() + "\"}";
		} else {
			jsonObject = "{\"newchange\": \"" + false + "\"}";
		}
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonObject);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
