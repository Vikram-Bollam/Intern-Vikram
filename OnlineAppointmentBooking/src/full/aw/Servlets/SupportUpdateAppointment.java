package full.aw.Servlets;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import full.aw.service.ScheduleBook;

@SuppressWarnings("serial")
public class SupportUpdateAppointment extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ScheduleBook sb=new ScheduleBook();
		String userid = (String) req.getSession(false).getAttribute("userSession");
		sb.deleteSchedule(userid, req.getParameter("custKey"));
	}//method close
}//class close