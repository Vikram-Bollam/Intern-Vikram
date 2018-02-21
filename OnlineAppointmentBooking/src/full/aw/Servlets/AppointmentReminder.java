package full.aw.Servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import full.aw.helper.MailUtil;

@SuppressWarnings("serial")
public class AppointmentReminder extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Logger logger = Logger.getLogger(AppointmentReminder.class.getName());
		logger.warning(req.getParameter("email"));
		logger.warning(req.getParameter("message"));
		MailUtil m = new MailUtil();
		m.sendSimpleMail(req.getParameter("email"), req.getParameter("uname"), "Appointment Remainder",
				req.getParameter("message"));
	}
}