package full.googlesignin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetUserInfo {
	public UserInfoBean getuserInfo(String accessToken) {
		try {
			URL obj = new URL("https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=" + accessToken);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("content-type", "application/json");
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
				UserInfoBean userDetails=JsonUtil.jsonToJava(jsonString, UserInfoBean.class);
			    return userDetails; 
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}
