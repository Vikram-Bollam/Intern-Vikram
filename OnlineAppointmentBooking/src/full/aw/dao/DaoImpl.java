package full.aw.dao;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import full.aw.helper.Statistics;
import full.aw.service.ScheduleBook;
import full.aw.service.ServicesDaoImplementation;

public class DaoImpl implements UserCurdOperations {
	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    //cheking the already userName is found or not 
	@Override
	public boolean checkUser(String userName) {
		Key k = KeyFactory.createKey("Users", userName);
		try {
			@SuppressWarnings("unused")
			Entity e = ds.get(k);
			return true;
		} catch (EntityNotFoundException e) {
			return false;
		}//catch close
	}//checkUser Method close

	//Method to update the details of the user
	@Override
	public boolean updateUser(String userid, String userName, String userEmail, String userPhone, String businessName,
			String userPass, String openTime, String closeTime) {
		Key k = KeyFactory.createKey("Users", userid);
		try {
			Entity e = ds.get(k);
			e.setProperty("UserName", userName);
			e.setProperty("UserEmail", userEmail);
			e.setProperty("UserPhone", userPhone);
			e.setProperty("BusinessName", businessName);
			e.setProperty("userPass", userPass);
			e.setProperty("OpenTime", openTime);
			e.setProperty("CloseTime", closeTime);
			ds.put(e);
			return true;
		} catch (EntityNotFoundException e) {
			return false;
		}//catch close
	}//method close

	//delete User method close
	@Override
	public boolean deleteUser(String user) {
		Statistics s = new Statistics();
		ServicesDaoImplementation sd = new ServicesDaoImplementation();
		ScheduleBook sb = new ScheduleBook();
		Key k = KeyFactory.createKey("Users", user);
		sb.deleteAppointmentsByUser(user);
		sd.deleteServicesByUser(user);
		s.deleteStatistics(user);
		ds.delete(k);
		return true;
	}//method close

	//Getting the password for a specfic user
	public boolean getPassword(String userId, String password) {
		Key k = KeyFactory.createKey("Users", userId);
		try {
			Entity e = ds.get(k);
			if (password.equals((String) e.getProperty("userPass"))) {
				return true;
			} else {
				return false;
			}
		} catch (EntityNotFoundException e) {
			return false;
		}//catch close
	}//getPassword method close

	//checking the requried email is found with database or not
	public Entity checkEmail(String email) {
		Filter checkEmail = new FilterPredicate("UserEmail", FilterOperator.EQUAL, email);
		Query q = new Query("Users").setFilter(checkEmail);
		PreparedQuery pq = ds.prepare(q);
		for (Entity result : pq.asIterable()) {
			return result;
		} // for close
		return null;
	}//checkMail method close

	//method to change the password
	public boolean changePassword(String userId, String password) {
		Key k = KeyFactory.createKey("Users", userId);
		try {
			Entity found = ds.get(k);
			found.setProperty("userPass", password);
			ds.put(found);
			return true;
		} catch (EntityNotFoundException e) {
			return false;
		}
	}//method close
	
	//getting the business name of requried user
	public String getBusinessName(String userId) {
		Key k = KeyFactory.createKey("Users", userId);
		try {
			Entity found = ds.get(k);
			String businessName=(String)found.getProperty("BusinessName");
			return businessName;
		} catch (EntityNotFoundException e) {
			return null;
		}//catch close
	}//getBusiness method close
}//class close