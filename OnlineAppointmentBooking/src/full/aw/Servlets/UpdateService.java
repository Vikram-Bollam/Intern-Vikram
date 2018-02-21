package full.aw.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import full.aw.service.ServicesDaoImplementation;

@SuppressWarnings("serial")
public class UpdateService extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userid = (String) req.getSession(false).getAttribute("userSession");
		ServicesDaoImplementation updateImpl = new ServicesDaoImplementation();
		updateImpl.updateService(userid, req.getParameter("serviceName"), req.getParameter("serviceCost"),
				req.getParameter("serviceTime"));
	}
}
