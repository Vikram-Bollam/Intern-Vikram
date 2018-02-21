package full.aw.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import full.aw.dao.DaoImpl;

@SuppressWarnings("serial")
public class DeleteAccount extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out=resp.getWriter();
		String userid = (String) req.getSession(false).getAttribute("userSession");
		String s = req.getParameter("password");
		DaoImpl d=new DaoImpl();
		boolean b=d.getPassword(userid, s);
        if(b==true){
        	d.deleteUser(userid);
    		RequestDispatcher rd = req.getRequestDispatcher("index.html");
    		rd.include(req, resp);
        	out.print("<script>alert('Account Deleted !! THANK YOU');</script>");
        }
        else{
        	RequestDispatcher rd1 = req.getRequestDispatcher("DeleteUser.jsp");
			rd1.include(req, resp);
			out.println(" Wrong Password !! ");
        }
	}
}
