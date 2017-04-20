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
 * Servlet implementation class MailCheckServlet
 */
@WebServlet("/MailCheckServlet")
public class MailCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JDBCTest jdb = (JDBCTest)request.getSession().getAttribute("jdb");
		String username = jdb.getLoggedUser();
		int num = Integer.valueOf(request.getParameter("mail"));
		int newCount = jdb.countUnreadMessages(username);
		String jsonObject;
		if (newCount > num) {
			ArrayList<Message> messages = jdb.getMessagesForUser(username);
			Message newest = messages.get(messages.size()-1);
			jsonObject = "{\"newchange\": \"" + true + "\", \"num\": \"" + newCount + "\", \"title\": \"" + newest.getTitle() + "\", \"image\": \"mail.png\", \"description\": \"" + newest.getMessage() + "\", \"sender\": \"" + newest.getSender() + "\", \"id\": \"" + newest.getID() + "\"}";
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
