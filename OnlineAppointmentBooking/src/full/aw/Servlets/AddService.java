package full.aw.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import full.aw.service.ServicesDaoImplementation;

@SuppressWarnings("serial")
public class AddService extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		String userid = (String) req.getSession(false).getAttribute("userSession");
		ServicesDaoImplementation addService = new ServicesDaoImplementation();
		boolean b = addService.addService(userid, req.getParameter("serviceName"), req.getParameter("serviceCost"),
				req.getParameter("serviceTime"));
		if (b == true) {
			RequestDispatcher rd = req.getRequestDispatcher("AddService.html");
			rd.include(req, resp);
			out.print("<script>alert('Service Added !! ');</script>");
		}
	}
}