package full.googlesignin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import full.aw.dao.DaoImpl;

@SuppressWarnings("serial")
public class GetTokens extends HttpServlet {
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String generatedCode = req.getParameter("code");
	try {
		String clientId = "582703059964-dp9nvbdmsgqfu1klg1nj7snb4vcb102c.apps.googleusercontent.com";
		String clientSecret = "t5VqTbV6qafUUzcWCEiZwiAM";
		String params = "&code=" + generatedCode + "&client_id=" + clientId + "&client_secret=" + clientSecret
				+ "&redirect_uri=http://localhost:8888/UserAuth&grant_type=authorization_code";
		URL obj = new URL("https://www.googleapis.com/oauth2/v4/token");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=utf-8");
		con.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
		writer.write(params);
		writer.flush();
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			String jsonString = response.toString();
			TokenInfoBean getToken = JsonUtil.jsonToJava(jsonString, TokenInfoBean.class);
			GetUserInfo info = new GetUserInfo();
			UserInfoBean user=info.getuserInfo(getToken.getAccess_token());
			DaoImpl checkUser=new DaoImpl();
			boolean find=checkUser.checkUser(user.getEmail());
			if(find==true){
				HttpSession ses = req.getSession(true);
				ses.setAttribute("userSession", user.getEmail());
				resp.sendRedirect("book.jsp");
			}else{
				DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
				Entity businessInfo = new Entity("Users", user.getEmail());
				businessInfo.setProperty("UserName", user.getName());
				businessInfo.setProperty("UserEmail", user.getEmail());
				businessInfo.setProperty("UserPhone", "");
				businessInfo.setProperty("BusinessName", "");
				businessInfo.setProperty("UserId", user.getEmail());
				businessInfo.setProperty("userPass", user.getEmail());
				businessInfo.setProperty("OpenTime", "08:00");
				businessInfo.setProperty("CloseTime", "20:00");
				dataStore.put(businessInfo);
				HttpSession ses = req.getSession(true);
				ses.setAttribute("userSession", user.getEmail());
				resp.sendRedirect("book.jsp");
			}
		}
	} catch (Exception e) {
		System.out.println("Exception occured while getting a access token");
	}
}
}
