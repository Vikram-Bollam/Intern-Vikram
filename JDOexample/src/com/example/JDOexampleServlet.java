package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.http.*;
@SuppressWarnings("serial")
public class JDOexampleServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		UserBean u=new UserBean();
		u.setFirstName("vikram");
		u.setLastName("bollam");
		u.setUserName("VIkram bollam");
		u.setPassword("vikram");
		Marks m=new Marks();
		m.setM1(123);
		m.setM2(34);
		m.setM3(123);
		List<Marks> l=new ArrayList<>();
		l.add(m);
		u.setMarks(l);
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
		PersistenceManager manager=pmf.getPersistenceManager();
		manager.makePersistent(u);
	}
}
