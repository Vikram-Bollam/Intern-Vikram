<%@page import="java.util.logging.Logger"%>
<%@page import="full.aw.helper.MemcacheUtil"%>
<%@page import="full.aw.helper.Statistics"%>
<%@page import="full.aw.helper.Conversion"%>
<%@page import="full.aw.service.ServicesDaoImplementation"%>
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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
table, td, th {
	border: 1px solid black;
	width: 400px;
	margin: 0 auto;
}
</style>
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
	<br>
	<br>
	<%
		String currentDate = Conversion.getCurrentDate("yyyy-MM-dd");
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		String currentuser = (String) session.getAttribute("userSession");
		try {
			Entity found = (Entity) MemcacheUtil.getCache().get(currentuser.concat(currentDate));
			found.getKind();
	%>
	<table>
		<tr>
			<th>Date</th>
			<th><%=(String) found.getProperty("Date")%></th>
		</tr>
		<tr>
			<th>Total Number of services offered</th>
			<th><%=(Integer) found.getProperty("TotalServices")%></th>
		</tr>
		<tr>
			<th>Total Number of Appointment to be Dealed</th>
			<th><%=(Integer) found.getProperty("TodayAppointment")%></th>
		</tr>
		<tr>
			<th>Total time to be Spend</th>
			<th><%=(Integer) found.getProperty("TotalTimeSpent")%></th>
		</tr>
		<tr>
			<th>Income to be Earned</th>
			<th><%=(Integer) found.getProperty("Income")%></th>
		</tr>
	</table>
	<%
		} catch (Exception e) {
			Filter requriedEntity = new FilterPredicate("StatisticsId", FilterOperator.EQUAL,
					currentuser.concat(currentDate));
			Query q = new Query("statistics").setFilter(requriedEntity);
			PreparedQuery pq = ds.prepare(q);
			for (Entity result : pq.asIterable()) {
				System.out.print("Entering TO DB");
				String date = (String) result.getProperty("Date");
				Long TotalServices = (Long) result.getProperty("TotalServices");
				Long TodayAppointment = (Long) result.getProperty("TodayAppointment");
				Long TotalTimeSpent = (Long) result.getProperty("TotalTimeSpent");
				Long Income = (Long) result.getProperty("Income");
	%>
 	<table>
		<tr>
			<th>Date</th>
			<th><%=date%></th>
		</tr>
		<tr>
			<th>Total Number of services offered</th>
			<th><%=TotalServices%></th>
		</tr>
		<tr>
			<th>Total Number of Appointment to be Dealed</th>
			<th><%=TodayAppointment%></th>
		</tr>
		<tr>
			<th>Total time to be Spend</th>
			<th><%=TotalTimeSpent%></th>
		</tr>
		<tr>
			<th>Income to be Earned</th>
			<th><%=Income%></th>
		</tr>
	</table> 
	<%
		}
		}
	%>
</body>
</html>