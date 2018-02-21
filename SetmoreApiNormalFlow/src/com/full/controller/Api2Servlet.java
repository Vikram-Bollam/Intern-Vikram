package com.full.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.full.helper.HelperMethods;

@Controller
public class Api2Servlet {

	@RequestMapping("/check")
	public @ResponseBody String checkUserDetails(@RequestParam("fname") String fname,
			@RequestParam("lname") String lname, @RequestParam("email") String email) throws IOException {
		String status = HelperMethods.checkUser(fname, lname, email);
		return status;
	}

	@RequestMapping("/insert")
	public ModelAndView insertUserDetails(@RequestParam("fname") String fname, @RequestParam("lname") String lname,
			@RequestParam("email") String email, @RequestParam("key") String key) throws IOException {
		@SuppressWarnings("unused")
		boolean insertStatus = HelperMethods.insertUser(fname, lname, email, key);
		return null;
	}
}