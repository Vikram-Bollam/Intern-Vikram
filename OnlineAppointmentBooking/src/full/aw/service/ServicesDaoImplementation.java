package full.aw.service;

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

public class ServicesDaoImplementation implements ServiceDao {
	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	@Override
	public boolean addService(String userId, String serviceName, String serviceCost, String serviceTime) {
		Entity addService = new Entity("Services");
		addService.setProperty("UserId", userId);
		addService.setProperty("ServiceName", serviceName);
		addService.setProperty("ServiceCost", serviceCost);
		ServicesDaoImplementation s=new ServicesDaoImplementation();
		addService.setProperty("ServiceTime", s.giveValidTime(serviceTime));
		ds.put(addService);
		return true;
	}

	@Override
	public ArrayList<String> getServices(String userId) {
		ArrayList<String> services = new ArrayList<>();
		Filter currentUser = new FilterPredicate("UserId", FilterOperator.EQUAL, userId);
		Query q = new Query("Services").setFilter(currentUser);
		PreparedQuery pq = ds.prepare(q);
		for (Entity result : pq.asIterable()) {
			String service = (String) result.getProperty("ServiceName");
			services.add(service);
		}
		return services;
	}

	@Override
	public int getTime(String userId, String serviceName) {
		int time = 0;
		Filter currentUser = new FilterPredicate("UserId", FilterOperator.EQUAL, userId);
		Filter requiredService = new FilterPredicate("ServiceName", FilterOperator.EQUAL, serviceName);
		CompositeFilter filter = CompositeFilterOperator.and(currentUser, requiredService);
		Query q = new Query("Services").setFilter(filter);
		PreparedQuery pq = ds.prepare(q);
		for (Entity result : pq.asIterable()) {
			time = Integer.parseInt((String) result.getProperty("ServiceTime"));
			return time;
		}
		return time;
	}

	@Override
	public boolean updateService(String userId, String serviceName, String serviceCost, String serviceTime) {
		Filter currentUser = new FilterPredicate("UserId", FilterOperator.EQUAL, userId);
		Filter requiredService = new FilterPredicate("ServiceName", FilterOperator.EQUAL, serviceName);
		CompositeFilter filter = CompositeFilterOperator.and(currentUser, requiredService);
		Query q = new Query("Services").setFilter(filter);
		PreparedQuery pq = ds.prepare(q);
		for (Entity result : pq.asIterable()) {
			result.setProperty("ServiceName", serviceName);
			result.setProperty("ServiceCost", serviceCost);
			ServicesDaoImplementation s=new ServicesDaoImplementation();
			result.setProperty("ServiceTime", s.giveValidTime(serviceTime));
			ds.put(result);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteSevice(String userId, String serviceName) {
		Filter currentUser = new FilterPredicate("UserId", FilterOperator.EQUAL, userId);
		Filter requiredService = new FilterPredicate("ServiceName", FilterOperator.EQUAL, serviceName);
		CompositeFilter filter = CompositeFilterOperator.and(currentUser, requiredService);
		Query q = new Query("Services").setFilter(filter);
		PreparedQuery pq = ds.prepare(q);
		for (Entity result : pq.asIterable()) {
			ds.delete(result.getKey());
			return true;
		}
		return false;
	}
	public String giveValidTime(String serviceTime) {
		int time = Integer.parseInt(serviceTime);
		if (time <= 7) {
			time = 5;
		} else if (time <= 20) {
			time = 15;
		}else if(time <= 30){
			time=30;
		}else{
			time=60;
		}
		return String.valueOf(time);
	}
	public int getCost(String userId, String serviceName) {
		int time = 0;
		Filter currentUser = new FilterPredicate("UserId", FilterOperator.EQUAL, userId);
		Filter requiredService = new FilterPredicate("ServiceName", FilterOperator.EQUAL, serviceName);
		CompositeFilter filter = CompositeFilterOperator.and(currentUser, requiredService);
		Query q = new Query("Services").setFilter(filter);
		PreparedQuery pq = ds.prepare(q);
		for (Entity result : pq.asIterable()) {
			time = Integer.parseInt((String) result.getProperty("ServiceCost"));
			return time;
		}
		return time;
	}
	
	public boolean deleteServicesByUser(String userId)
	{   DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Filter userid = new FilterPredicate("UserId", FilterOperator.EQUAL, userId);
		Query q = new Query("Services").setFilter(userid);
		PreparedQuery pq = ds.prepare(q);
		for (Entity result : pq.asIterable()) {
			ds.delete(result.getKey());
		}
		return true;	
	}
}
