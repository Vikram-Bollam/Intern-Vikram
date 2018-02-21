package full.aw.helper;
import java.util.logging.Logger;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class LoginChecking {
	static public Entity checkDetails(String userName, String password) {
		Logger logger = Logger.getLogger(LoginChecking.class.getName());
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Key k = KeyFactory.createKey("Users", userName);
		Entity loggedDetails = null;
		try {
			loggedDetails = ds.get(k);
			if (password.equals(loggedDetails.getProperty("userPass"))) {
				return loggedDetails;
			} else {
				return null;
			}
		} catch (EntityNotFoundException e) {
			logger.warning("Exception occured in Finding user Entity");
		} // catch close
		return null;
	}// method close
}// class close