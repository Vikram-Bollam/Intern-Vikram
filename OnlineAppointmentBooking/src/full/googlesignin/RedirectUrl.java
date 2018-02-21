package full.googlesignin;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class RedirectUrl extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String redirect_uri = "http://localhost:8888/UserAuth";
		String client_id = "582703059964-dp9nvbdmsgqfu1klg1nj7snb4vcb102c.apps.googleusercontent.com";
		String finalUrl = "https://accounts.google.com/o/oauth2/auth?redirect_uri=" + redirect_uri
				+ "&response_type=code&client_id=" + client_id
				+ "&scope=https://www.googleapis.com/auth/analytics.readonly+https://www.googleapis.com/auth/userinfo.email&"
				+ "approval_prompt=force&access_type=offline";
		resp.sendRedirect(finalUrl);
	}
}