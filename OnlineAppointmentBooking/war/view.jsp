<%@page import="full.aw.helper.Conversion"%>
<%@page import="com.google.appengine.api.datastore.Query.Filter"%>
<%@page import=" com.google.appengine.api.datastore.DatastoreService"%>
<%@page
	import=" com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import=" com.google.appengine.api.datastore.PreparedQuery"%>
<%@page import="com.google.appengine.api.datastore.Query"%>
<%@page
	import="com.google.appengine.api.datastore.Query.CompositeFilter"%>
<%@page
	import="com.google.appengine.api.datastore.Query.CompositeFilterOperator"%>
<%@page import="com.google.appengine.api.datastore.Query.Filter"%>
<%@page import="com.google.appengine.api.datastore.Query.FilterOperator"%>
<%@page
	import="com.google.appengine.api.datastore.Query.FilterPredicate"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="SearchUser.js"></script>
<script type="text/javascript" src="Validation.js"></script>
<script>
	$(function() {
		$("#includedContent").load("Home.jsp");
	});
</script>
</head>
<style>
input[type=text] {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

input[type=button] {
	width: 100%;
	background-color: #4CAF50;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

input[type=button]:hover {
	background-color: #45a049;
}

table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

td, th {
	border: 1px solid #dddddd;
	text-align: left;
	padding: 8px;
}
tr:nth-child(even) {
	background-color: #dddddd;
}
</style>
<body>
<div id="includedContent"></div>
	<center>
	<div id="changeDate"><br>
	<input type="date" id="date" onchange="testFunction()">
	 </div>
		<%
			String currentuser = (String) session.getAttribute("userSession");
		    String date=request.getParameter("date");
 		%>
		<br><table>
			<tr>
			    <th>Appointement ID</th>
				<th>Customer Name</th>
				<th>Customer Email</th>
				<th>Start Time</th>
				<th>End Time</th>
				<th>Service</th>
				<th>Update</th>
				<th>Delete</th>
			</tr>
			<%
			    int count=0;
				DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
				Filter userId = new FilterPredicate("userId", FilterOperator.EQUAL, currentuser);
				Filter userDate = new FilterPredicate("Date", FilterOperator.EQUAL, date);
				CompositeFilter requriredEntity = CompositeFilterOperator.and(userId, userDate);
				Query q = new Query("Appointment").setFilter(requriredEntity);
				PreparedQuery pq = ds.prepare(q);
				if(pq==null){
					response.sendRedirect("SaloonProject/war/book.jsp");
				}
				for (Entity result : pq.asIterable()) {
				    count=count+1;
					String date1 = (String) result.getProperty("Date");
					String custName = (String) result.getProperty("CustomerName");
					String custEmail = (String) result.getProperty("CustomerEmail");
					String selectedService = (String) result.getProperty("ServiceName");
					Long startTime = (Long) result.getProperty("StartTime");
					Long endTime = (Long) result.getProperty("EndTime");
					String start = Conversion.milliToDate(startTime, "hh:mm a");
					String end = Conversion.milliToDate(endTime, "hh:mm a");
					String idName="custName"+count;
					String idEmail="custEmail"+count;
					String idService="custService"+count;
					String idIdentifier="custKey"+count;
					
			%>
			<tr> 
			    <td><input type="text" value="<%=result.getKey()%>" id="<%=idIdentifier%>" readonly="readonly"></td>
			    <td><%= custName %></td>
				<td><%= custEmail%></td>
				<td ><%= start%></td>
				<td><%= end%></td>
				<td><%= selectedService %></td>
				<td><input type="button" value="Update" id="<%= count%>" onclick="updateDetails(this.id)"/></td>
				<td><input type="button" value="Delete" id="<%= count%>" onclick="deleteDetails(this.id)"/></td>
			</tr>
			<%
				}
			%>
		</table>
	</center>
</body>
</html>