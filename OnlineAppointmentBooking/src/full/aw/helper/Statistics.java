package full.aw.helper;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import full.aw.service.ServicesDaoImplementation;

public class Statistics {

	@SuppressWarnings("unchecked")
	public  void getStatistics() {
		int appNumber = 0, totalTime = 0, totalIncome = 0;
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		ServicesDaoImplementation s = new ServicesDaoImplementation();
		String currentDate = Conversion.getCurrentDate("yyyy-MM-dd");
		Query q1 = new Query("Users");
		PreparedQuery pq1 = ds.prepare(q1);
		for (Entity a : pq1.asIterable()) {
			String userId = (String) a.getProperty("UserId");
			ArrayList<String> servicesArray = s.getServices(userId);
			Filter userid = new FilterPredicate("userId", FilterOperator.EQUAL, userId);
			Filter nowDate = new FilterPredicate("Date", FilterOperator.EQUAL, currentDate);
			CompositeFilter filter = CompositeFilterOperator.and(userid, nowDate);
			Query q = new Query("Appointment").setFilter(filter);
			PreparedQuery pq = ds.prepare(q);
			for (Entity result : pq.asIterable()) {
				appNumber = appNumber + 1;
				String servieName = (String) result.getProperty("ServiceName");
				int serviceTime = s.getTime(userId, servieName);
				totalTime = totalTime + serviceTime;
				int serviceCost = s.getCost(userId, servieName);
				totalIncome = totalIncome + serviceCost;
			}
			Entity cornEntry = new Entity("statistics",userId.concat(currentDate));
			cornEntry.setProperty("StatisticsId", userId.concat(currentDate));
			cornEntry.setProperty("UserId", userId);
			cornEntry.setProperty("Date", currentDate);
			cornEntry.setProperty("TotalServices", servicesArray.size());
			cornEntry.setProperty("TodayAppointment", appNumber);
			cornEntry.setProperty("TotalTimeSpent", totalTime);
			cornEntry.setProperty("Income", totalIncome);
			ds.put(cornEntry);
			MemcacheUtil.getCache().put(userId.concat(currentDate), cornEntry);	
		} // for close
	}// method close
	
	public boolean deleteStatistics(String userId)
	{   DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Filter userid = new FilterPredicate("UserId", FilterOperator.EQUAL, userId);
		Query q = new Query("statistics").setFilter(userid);
		PreparedQuery pq = ds.prepare(q);
		for (Entity result : pq.asIterable()) {
			ds.delete(result.getKey());
		}
		return true;	
	}
}// class close
