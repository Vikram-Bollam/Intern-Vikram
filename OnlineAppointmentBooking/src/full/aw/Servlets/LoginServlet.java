package full.aw.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.appengine.api.datastore.Entity;
import full.aw.helper.LoginChecking;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Entity loggedDetails = LoginChecking.checkDetails(req.getParameter("luser"), req.getParameter("lpass"));
		// if user found loggedDetails contains all the fields of logged user
		if (loggedDetails != null) {
			HttpSession ses = req.getSession(true);
			ses.setAttribute("userSession", req.getParameter("luser"));
			resp.sendRedirect("book.jsp");// redirecting to home page.
		}
		// redirecting to login page if userName and password is wrong
		else {
			PrintWriter out = resp.getWriter();
			RequestDispatcher rd1 = req.getRequestDispatcher("Login.html");
			rd1.include(req, resp);
			out.println("Invalid Details");
		} // else close
	}
}
