package full.aw.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

import full.aw.dao.DaoImpl;
import full.aw.helper.MailUtil;

@SuppressWarnings("serial")
public class PasswordReset extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		DaoImpl a = new DaoImpl();
		Entity foundEntity = a.checkEmail(req.getParameter("email"));
		if (foundEntity != null) {
			String name = (String) foundEntity.getProperty("UserName");
			String email = (String) foundEntity.getProperty("UserEmail");
			String userId = (String) foundEntity.getProperty("UserId");
			String subject = "Reset your Password";
			String message = "http://1-dot-appointmentbokking.appspot.com/ResetPassword.jsp?userId=" + userId;
			MailUtil m = new MailUtil();
			m.sendSimpleMail(email, name, subject, message);
			RequestDispatcher rd = req.getRequestDispatcher("Login.html");
			rd.include(req, resp);
			out.print("<script>alert('Check Your Email'); window.close(); </script>");
		} else {
			RequestDispatcher rd = req.getRequestDispatcher("ForgetPassword.jsp");
			rd.include(req, resp);
			out.print("<script>alert('EMAIL NOT FOUND !!');</script>");
		}
	}
}