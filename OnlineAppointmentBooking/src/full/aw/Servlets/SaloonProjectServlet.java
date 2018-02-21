package full.aw.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import full.aw.dao.DaoImpl;

@SuppressWarnings("serial")
public class SaloonProjectServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		DaoImpl d = new DaoImpl();
		boolean b = d.checkUser(req.getParameter("userid"));
		try {
			if (b != true) {
				Entity businessInfo = new Entity("Users", req.getParameter("userid"));
				businessInfo.setProperty("UserName", req.getParameter("pname"));
				businessInfo.setProperty("UserEmail", req.getParameter("email"));
				businessInfo.setProperty("UserPhone", req.getParameter("phone"));
				businessInfo.setProperty("BusinessName", req.getParameter("bname"));
				businessInfo.setProperty("UserId", req.getParameter("userid"));
				businessInfo.setProperty("userPass", req.getParameter("pass"));
				businessInfo.setProperty("OpenTime", "08:00");
				businessInfo.setProperty("CloseTime", "20:00");
				dataStore.put(businessInfo);
				resp.sendRedirect("Login.html");
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("index.html");
				out.println("userName already exists");
				rd.include(req, resp);
			}
		} catch (Exception ee) {
		}
	}
}
