package full.aw.service;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import full.aw.helper.MemcacheUtil;

public class ScheduleBook {
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	// code to create schedule
	public String bookSchedule(String userid, String custName, String custEmail, String date, Long time,
			String service) {
		ServicesDaoImplementation s = new ServicesDaoImplementation();
		int serviceTime = s.getTime(userid, service);
		Entity insertApp = new Entity("Appointment");
		insertApp.setProperty("userId", userid);
		insertApp.setProperty("Date", date);
		insertApp.setProperty("CustomerName", custName);
		insertApp.setProperty("CustomerEmail", custEmail);
		insertApp.setProperty("StartTime", time);
		insertApp.setProperty("EndTime", time + (serviceTime * 60 * 1000));
		insertApp.setProperty("ServiceName", service);
		ds.put(insertApp);
		return "Done!!";
	}// create method close

	// code to delete Schedule
	public boolean deleteSchedule(String userId, String userKey) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Filter userid = new FilterPredicate("userId", FilterOperator.EQUAL, userId);
		Query q = new Query("Appointment").setFilter(userid);
		PreparedQuery pq = ds.prepare(q);
		for (Entity result : pq.asIterable()) {
			if (result.getKey().toString().equals(userKey)) {
				ds.delete(result.getKey());
				return true;
			} // if close
		} // for close
		return false;
	}// delete method close

	// code to obtain the required entity

	@SuppressWarnings("unchecked")
	public Entity getEntity(String userId, String userKey) {
		Filter userid = new FilterPredicate("userId", FilterOperator.EQUAL, userId);
		Query q = new Query("Appointment").setFilter(userid);
		PreparedQuery pq = ds.prepare(q);
		Entity wantedEntity = null;
		for (Entity result : pq.asIterable()) {
			if (result.getKey().toString().equals(userKey)) {
				wantedEntity = result;
				MemcacheUtil.getCache().put(userKey, wantedEntity);
				return wantedEntity;
			} // if close
		} // for close
		return null;
	}// getEntity method close

	// code to update the Appointment
	public boolean updateSchedule(String userid, String custName, String custEmail, String date, Long time,
			String service, String userKey) {
		try {
			Entity insertApp = (Entity) MemcacheUtil.getCache().get(userKey);
			ServicesDaoImplementation s = new ServicesDaoImplementation();
			int serviceTime = s.getTime(userid, service);
			insertApp.setProperty("Date", date);
			insertApp.setProperty("CustomerName", custName);
			insertApp.setProperty("CustomerEmail", custEmail);
			insertApp.setProperty("StartTime", time);
			insertApp.setProperty("EndTime", time + (serviceTime * 60 * 1000));
			insertApp.setProperty("ServiceName", service);
			ds.put(insertApp);
			return true;
		} catch (Exception e) {
			Filter user = new FilterPredicate("userId", FilterOperator.EQUAL, userid);
			Query q = new Query("Appointment").setFilter(user);
			PreparedQuery pq = ds.prepare(q);
			for (Entity insertApp : pq.asIterable()) {
				if (insertApp.getKey().toString().equals(userKey)) {
					ServicesDaoImplementation s = new ServicesDaoImplementation();
					int serviceTime = s.getTime(userid, service);
					insertApp.setProperty("Date", date);
					insertApp.setProperty("CustomerName", custName);
					insertApp.setProperty("CustomerEmail", custEmail);
					insertApp.setProperty("StartTime", time);
					insertApp.setProperty("EndTime", time + (serviceTime * 60 * 1000));
					insertApp.setProperty("ServiceName", service);
					ds.put(insertApp);
					return true;
				}
			}
		}
		return false;
	}// update method close

	public boolean deleteAppointmentsByUser(String userId) {
		Filter userid = new FilterPredicate("userId", FilterOperator.EQUAL, userId);
		Query q = new Query("Appointment").setFilter(userid);
		PreparedQuery pq = ds.prepare(q);
		for (Entity result : pq.asIterable()) {
			ds.delete(result.getKey());
		}
		return true;
	}
}// class close
