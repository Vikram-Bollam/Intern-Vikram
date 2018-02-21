package com.full.helper;

import java.util.logging.Logger;

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

public class HelperMethods {
	static Logger log = Logger.getLogger(HelperMethods.class.getName());
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	public static boolean insertUser(String fname, String lname, String email, String key) {
		String status = checkUser(fname, lname, email);
		if (status.equals("notFound")) {
			try {
				Entity user = new Entity("Users");
				user.setProperty("FirstName", fname);
				user.setProperty("LastName", lname);
				user.setProperty("Email", email);
				user.setProperty("Key", key);
				ds.put(user);
				return true;
			} catch (Exception e) {
				log.warning("Exception occured while creating a user details");
				return false;
			} // catch closef
		} else {
			return false;
		}
	}// insertUser method close

	public static String checkUser(String fname, String lname, String email) {
		Filter fnameUser = new FilterPredicate("FirstName", FilterOperator.EQUAL, fname);
		Filter lnameUser = new FilterPredicate("LastName", FilterOperator.EQUAL, lname);
		Filter emailUser = new FilterPredicate("Email", FilterOperator.EQUAL, email);
		CompositeFilter filter = CompositeFilterOperator.and(fnameUser, lnameUser, emailUser);
		Query q = new Query("Users").setFilter(filter);
		PreparedQuery pq = ds.prepare(q);
		for (Entity result : pq.asIterable()) {
			return (String) result.getProperty("Key");
		}
		return "notFound";
	}// checkUser method close

}// class close
