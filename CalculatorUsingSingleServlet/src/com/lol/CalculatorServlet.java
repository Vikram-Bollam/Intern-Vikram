package com.lol;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.*;
@SuppressWarnings("serial")
public class CalculatorServlet extends HttpServlet {
	PrintWriter pw;
	int e = 0;
	String b;
	String a;
	String operation;
    int c=0;
    int d=0;
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/html");
		pw = resp.getWriter();
		a = req.getParameter("number1");
		b = req.getParameter("number2");
		operation = req.getParameter("opt");
		if (a == null && b == null) {
			cal();
		} else {
			if (a != null && b != null) {
				char ch = operation.charAt(0);
				 c = Integer.parseInt(a);
				 d = Integer.parseInt(b);
				switch (ch) {
				case 'A':
					e = c + d;
					cal();
					break;
				case 'S':
					e = c - d;
					cal();
					break;
				case 'M':
					e = c * d;
					cal();
					break;
				case 'D':
					e = c / d;
					cal();
					break;
				}//switch close

			} // inside if close
		} // else close
	}// get close

	public void cal() {
		pw.println("<html><body>");
		pw.println("<form><center>");
		pw.println("<h1><p>Welcome to the scientific calculator</p></h1>");
		pw.println("<table>");
		pw.println("<tr><td>Number 1:</td>");
		pw.println("<td><input type='text' name='number1' value='" + c + "'></td></tr>");
		pw.println("<tr><td>Number 2:</td>");
		pw.println("<td><input type='text' name='number2' value='" + d + "'></td></tr>");
		pw.println("<tr><td>Operations:</td>");
		pw.println(
				"<td><select name='opt'><option value='Addition'>Addition</option><option value='Subtraction'>Subtraction</option><option value='Multiplication'>Multiplication</option><option value='Division'>Division</option></select></td></tr>");
		pw.println(" <tr><td>Result:</td>");
		pw.println("<td><input type='text' name='output' value='" + e + "'></td></tr><br>");
		pw.println("<tr><td><input type='submit' value='submit'><input type='submit' value='cancel'></td></tr><br>");
		pw.println("</table>");
		pw.println("</center></form></body></html>");
	}// method close
}// class close
