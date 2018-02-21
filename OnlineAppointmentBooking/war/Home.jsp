
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="SearchUser.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<style>
header {
	padding: 1em;
	color: white;
	background-color: #4b42f4;
	clear: left;
	text-align: center;
	font-stretch: 12;
}

a {
	text-decoration: none;
	color: white;
}
</style>
<body>
	<%
		response.setHeader("Cache-Control", "no-chace, no-store, must-revalidate");
	%>
	<header> <%
 	String currentuser = (String) session.getAttribute("userSession");
 	if (currentuser == null) {
 		response.sendRedirect("logout.jsp");
 	} else {
 %>
	<h3>
		<a href="book.jsp">Book Appointment</a>&nbsp;&nbsp;&nbsp;&nbsp;
		 <a	href="ViewAppointment.jsp">View Appointment</a> &nbsp;&nbsp;&nbsp;&nbsp;
		 <a	href="UpdateUser.jsp">Update Details</a>&nbsp;&nbsp;&nbsp;&nbsp; <a
			href="AddService.html">Add Service </a> &nbsp;&nbsp;&nbsp;&nbsp;<a
			href="UpdateServices.jsp">Update Service</a>&nbsp;&nbsp;&nbsp;&nbsp; 
			<a href="Statistics.jsp">Statistics</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="logout.jsp">Logout(<%=currentuser%>)
		</a>
	</h3>

	<%
		}
	%> </header>
	
</body>
</html>