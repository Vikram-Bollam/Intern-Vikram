package full.aw.Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import full.aw.dao.DaoImpl;

@SuppressWarnings("serial")
public class UpdateUser extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		String userid = (String) req.getSession(false).getAttribute("userSession");
			DaoImpl d = new DaoImpl();
			System.out.println(req.getParameter("closeTime"));
			d.updateUser(userid, req.getParameter("pname"), req.getParameter("email"), req.getParameter("phone"),
					req.getParameter("bname"), req.getParameter("pass"), req.getParameter("openTime"),
					req.getParameter("closeTime"));
			;
			RequestDispatcher rd=req.getRequestDispatcher("UpdateUser.jsp");
			rd.include(req, resp);
		}
	}
