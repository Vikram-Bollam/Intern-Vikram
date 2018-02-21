package full.aw.Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import full.aw.dao.DaoImpl;

@SuppressWarnings("serial")
public class ResetPass extends HttpServlet {
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	DaoImpl d=new DaoImpl();
	boolean b=d.changePassword(req.getParameter("name"), req.getParameter("pass"));
	if(b==true){
		resp.sendRedirect("Login.html");
	}
}
}
