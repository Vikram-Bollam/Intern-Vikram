package full.aw.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import full.aw.helper.Conversion;
import full.aw.service.ServicesDaoImplementation;

@SuppressWarnings("serial")
public class FindAvalability extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServicesDaoImplementation ss=new ServicesDaoImplementation();
		PrintWriter out = resp.getWriter();
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		String userid = (String) req.getSession(false).getAttribute("userSession");
		int serviceTime=ss.getTime(userid,req.getParameter("currentService"));
		String date = req.getParameter("date");
		Key k = KeyFactory.createKey("Users", userid);
		try {
			Entity caught = ds.get(k);
			String opent = (String) caught.getProperty("OpenTime");
			String closet = (String) caught.getProperty("CloseTime");
			Long openMilli = Conversion.dateToMilli(date.concat(" " + opent), "yyyy-MM-dd HH:mm");
			Long closeMilli = Conversion.dateToMilli(date.concat(" " + closet), "yyyy-MM-dd HH:mm");
			// In order to store all the Schedule Times in between Working Hours
			TreeSet<Long> availableTime = new TreeSet<>();
			// In order to avoid Concurrent Modification Exception
			TreeSet<Long> toRemove = new TreeSet<>();
			// Adding all the schedule times to the available Times 
			while (openMilli < closeMilli) {
				availableTime.add(openMilli);
				openMilli = openMilli + (serviceTime * 60 * 1000);
			}
			Long tempNumber=availableTime.last();
			if((tempNumber+(serviceTime * 60 * 1000))>closeMilli){
			availableTime.remove(tempNumber);
			}
			// Applying filters and getting requried data
			Filter userId = new FilterPredicate("userId", FilterOperator.EQUAL, userid);
			Filter userDate = new FilterPredicate("Date", FilterOperator.EQUAL, date);
			CompositeFilter requriredEntity = CompositeFilterOperator.and(userId, userDate);
			Query q = new Query("Appointment").setFilter(requriredEntity);
			PreparedQuery pq = ds.prepare(q);		
			// obtaining the query data and deleting the already used time slots
			for (Entity result : pq.asIterable()) {
				Long startTime = (Long) result.getProperty("StartTime");
				Long endTime = (Long) result.getProperty("EndTime");
				for (Long check : availableTime) {
					if (startTime <= check && check < endTime) {
						toRemove.add(check);
					}
				}
				availableTime.removeAll(toRemove);				
			} // as Iterable close
		// displaying the available times			
			out.println("<select name='time'>");
			for (Long v : availableTime) {
				String t = Conversion.milliToDate(v, "HH:mm");
				out.println("<option value='" + t + "'>" + t + "</option>");
			} // for close
		}//try close 
		catch (EntityNotFoundException e) {
			e.printStackTrace();
		}//catch close
	}// post close
}// class close
