package full.aw.dao;
public interface UserCurdOperations {
  public boolean checkUser(String user);
  public boolean updateUser(String userid, String userName, String userEmail, String userPhone , String businessName , String userPass , String openTime , String closeTime);
  public boolean deleteUser(String user);
}
