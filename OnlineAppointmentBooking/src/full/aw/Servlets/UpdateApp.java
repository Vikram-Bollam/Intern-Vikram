package full.aw.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import full.aw.helper.Conversion;
import full.aw.service.ScheduleBook;

@SuppressWarnings("serial")
public class UpdateApp extends HttpServlet {
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	ScheduleBook sc = new ScheduleBook();
	Long time = Conversion.dateToMilli(req.getParameter("date").concat(" " + req.getParameter("time")), "yyyy-MM-dd HH:mm");
	System.out.println(time);
	String userid = (String) req.getSession(false).getAttribute("userSession");
	boolean status = sc.updateSchedule(userid, req.getParameter("custName"), req.getParameter("custEmail"),
			req.getParameter("date"), time ,req.getParameter("service"),req.getParameter("key"));
	if(status==true){
		PrintWriter out=resp.getWriter();
		out.print("<script>alert('Record Updated !! ');</script>");
		RequestDispatcher rd = req.getRequestDispatcher("ViewAppointment.jsp");
		rd.include(req, resp);
	}
}
}