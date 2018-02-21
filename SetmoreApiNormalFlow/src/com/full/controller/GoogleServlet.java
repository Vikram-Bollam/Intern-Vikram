package com.full.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import full.googlesignin.GetUserInfo;
import full.googlesignin.JsonUtil;
import full.googlesignin.TokenInfoBean;
import full.googlesignin.UserInfoBean;

@Controller
public class GoogleServlet {
	@RequestMapping("/")
	public ModelAndView homePageRedirect() throws IOException {
		String redirect_uri = "http://localhost:8888/UserAuth";
		String client_id = "582703059964-dp9nvbdmsgqfu1klg1nj7snb4vcb102c.apps.googleusercontent.com";
		String finalUrl = "https://accounts.google.com/o/oauth2/auth?redirect_uri=" + redirect_uri
				+ "&response_type=code&client_id=" + client_id
				+ "&scope=https://www.googleapis.com/auth/analytics.readonly+https://www.googleapis.com/auth/userinfo.email&"
				+ "approval_prompt=force&access_type=offline";
		ModelAndView model = new ModelAndView("redirect:" + finalUrl);
		return model;
	}

	@RequestMapping("/UserAuth")
	public void postRequest(HttpServletRequest req, HttpServletResponse resp) {
		String generatedCode = req.getParameter("code");
		System.out.println(generatedCode);
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
				HttpSession ses = req.getSession(true);
				ses.setAttribute("name", user);
				resp.sendRedirect("MainPage.jsp");
			}
		} catch (Exception e) {
			System.out.println("Exception occured while getting a access token");
		}
	}
}