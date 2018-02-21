package full.aw.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import full.aw.service.ScheduleBook;

@SuppressWarnings("serial")
public class UpdateAppointment extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		String userid = (String) req.getSession(false).getAttribute("userSession");
		ScheduleBook sb = new ScheduleBook();
		Entity e = sb.getEntity(userid, req.getParameter("custKey"));
		JSONObject obj=new JSONObject(); 
		try {
			obj.put("CustomerName",(String) e.getProperty("CustomerName"));
			obj.put("CustomerEmail",(String)e.getProperty("CustomerEmail"));
			obj.put("ServiceName",(String) e.getProperty("ServiceName"));
			obj.put("Date", (String)e.getProperty("Date"));
			obj.put("StartTime", (Long)e.getProperty("StartTime"));
			obj.put("custKey", e.getKey().toString());
			pw.print(obj);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}