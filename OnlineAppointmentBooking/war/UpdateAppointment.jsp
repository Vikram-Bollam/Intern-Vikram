
<%@page import="full.aw.helper.Conversion"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="full.aw.service.ServicesDaoImplementation"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
input[type=submit] {
	width: 100%;
	background-color: #4CAF50;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

.style {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

input[type=submit]:hover {
	background-color: #45a049;
}

table {
	width: 400px;
	margin: 0 auto;
}
</style>
<script type="text/javascript" src="SearchUser.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
	$(function() {
		$("#includedContent").load("Home.jsp");
	});
</script>
</head>
<body>
	<div id="includedContent"></div>
	<form action="UpdateApp" method="post">
		<%
			
		    String custName=request.getParameter("custName");
			String custEmail=request.getParameter("custEmail");
			String custDate=request.getParameter("custDate");
			Long s=Long.valueOf(request.getParameter("custTime"));
			System.out.print(s);
			String time=Conversion.milliToDate(s, "HH:mm");
			String custService=request.getParameter("custSevice");
            String key=request.getParameter("key");
			String currentuser = (String) session.getAttribute("userSession");
			ServicesDaoImplementation i = new ServicesDaoImplementation();
			ArrayList<String> service = i.getServices(currentuser);
		%>
		<table>
			<br>
			<br>
			<tr>
				<td>Customer Name</td>
				<td><input type="text" name="custName" value="<%= custName%>" class="style" required="required"></td>
			</tr>
			<tr>
				<td>Service</td>
				<td><select name="service" id="currentService" value="<%= custService%>" class="style" required="required">
						<%
							for (String services : service) {
						%>
						<option value='<%=services%>'><%=services%></option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td>Date</td>
				<td><input type="date" name="date" id="date" value="<%= custDate%>"
					onblur="checkTime()" class="style"required="required" ></td>
			</tr>
			<tr>
				<td>Customer Email</td>
				<td><input type="email" name="custEmail" value="<%= custEmail%>" class="style"></td>
				<td><input type="hidden" name="key" value="<%= key%>" required="required" ></td>
			</tr>
			<tr>
				<td>Time</td>
				<td><div id="time" class="style"></div></td>
			</tr>
			<tr>
				<td><input type="submit" value=" Update !! "></td>
			</tr>
		</table>
	</form>
</body>
</html>