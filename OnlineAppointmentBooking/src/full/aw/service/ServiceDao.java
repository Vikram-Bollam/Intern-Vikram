package full.aw.service;

import java.util.ArrayList;

public interface ServiceDao {
public boolean addService(String userId, String serviceName, String serviceCost , String serviceTime);
public boolean deleteSevice(String userId,String serviceName);
public boolean updateService(String userId,String serviceName,String serviceCost,String serviceTime);
public ArrayList<String> getServices(String userId) ;
public int getTime(String userId, String serviceName);
}