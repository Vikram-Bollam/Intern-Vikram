package full.googlesignin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
				UserInfoBean user = info.getuserInfo(getToken.getAccess_token());
				user.getEmail();
				user.getName();
				user.getFamily_name();
			}
		} catch (Exception e) {
			System.out.println("Exception occured while getting a access token");
		}
	}
}
