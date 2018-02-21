package full.aw.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import full.aw.dao.DaoImpl;

@SuppressWarnings("serial")
public class FindUser extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("txt/plain");
		PrintWriter pd = resp.getWriter();
		DaoImpl d = new DaoImpl();
		boolean b = d.checkUser(req.getParameter("search"));
		if (b == true) {
			pd.println("UserName already existed");
		}
	}// get close
}// class close