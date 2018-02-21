package full.aw.helper;

import java.util.Collections;
import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

public class MemcacheUtil {
	public static Cache getCache() {
		Cache cache = null;
		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
		} catch (CacheException e) {
			return null;
		}
		return cache;
	}//method close
}//class closeD