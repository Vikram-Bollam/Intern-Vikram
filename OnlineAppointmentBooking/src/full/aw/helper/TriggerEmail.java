package full.aw.helper;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import full.aw.dao.DaoImpl;

public class TriggerEmail {
	public void triggerEmail() {
		DaoImpl d = new DaoImpl();
		Queue queue = QueueFactory.getQueue("sendEmail");
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Long currentMilliInGMT = System.currentTimeMillis();
		Long currentMilli = currentMilliInGMT + (330 * 60 * 1000);
		Long openRange = currentMilli + (30 * 60 * 1000);
		Long closeRange = openRange + ((30 * 60 * 1000) - 1);
		Filter startTime = new FilterPredicate("StartTime", FilterOperator.LESS_THAN, closeRange);
		Query q1 = new Query("Appointment").setFilter(startTime);
		PreparedQuery pq1 = ds.prepare(q1);
		for (Entity a : pq1.asIterable()) {
			if ((Long) a.getProperty("StartTime") > openRange) {
				String userMail = (String) a.getProperty("CustomerEmail");
				String userService = (String) a.getProperty("ServiceName");
				String userId = (String) a.getProperty("userId");
				String businessName = d.getBusinessName(userId);
				String userName = (String) a.getProperty("CustomerName");
				Long userTime = (Long) a.getProperty("StartTime");
				String time = Conversion.milliToDate(userTime, "hh:mm a");
				String message = "MR/MS " + "" + userName + " Your Appointment is Booked at " + time + " at "
						+ businessName + " for " + userService + " service";
				queue.add(TaskOptions.Builder.withUrl("/AppointmentReminder").param("email", userMail).param("message",
						message).param("uname",userName ));
			} // if close
		} // for close
	}// method close
}// class close