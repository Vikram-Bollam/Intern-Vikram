package full.aw.Servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import full.aw.helper.Statistics;

@SuppressWarnings("serial")
public class CornDemo extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Logger logger = Logger.getLogger(CornDemo.class.getName());
		Statistics s=new Statistics();
		s.getStatistics();
		logger.warning("Corn Job executed!!!");
	}
}