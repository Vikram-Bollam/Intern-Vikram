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

input[type=text] {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

input[type=button]:hover {
	background-color: #45a049;
}

table {
	width: auto;
	margin: 0 auto;
}
</style>
<script type="text/javascript" src="SearchUser.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="Validation.js"></script>
<script>
	$(function() {
		$("#includedContent").load("Home.jsp");
	});
</script>
</head>
<body>
	<div id="includedContent"></div>
	<table>
		<tr>
			<th>Service Name</th>
			<th>Service Cost</th>
			<th>Service Time</th>
		</tr>
		<%
			int count = 0;
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			String currentuser = (String) session.getAttribute("userSession");
			Filter userId = new FilterPredicate("UserId", FilterOperator.EQUAL, currentuser);
			Query q = new Query("Services").setFilter(userId);
			PreparedQuery pq = ds.prepare(q);
			for (Entity result : pq.asIterable()) {
				count = count + 1;
				String serviceName = (String) result.getProperty("ServiceName");
				int serviceCost = Integer.parseInt((String) result.getProperty("ServiceCost"));
				int serviceTime = Integer.parseInt((String) result.getProperty("ServiceTime"));
				String generateName = "serviceName" + count;
				String generateCost = "serviceCost" + count;
				String generateTime = "serviceTime" + count;
		%>
		<tr>
			<th><input type="text" value="<%=serviceName%>"
				id="<%=generateName%>" readonly="readonly"></th>
			<th><input type="text" value="<%=serviceCost%>"
				id="<%=generateCost%>" onkeypress="return onlyNos(event,this)"></th>
			<th><input type="text" value="<%=serviceTime%>"
				id="<%=generateTime%>" onkeypress="return onlyNos(event,this)"></th>
			<th><input type="button" value="Update" id="<%=count%>"
				onclick="updateService(this.id)" /></th>
			<th><input type="button" value="Delete" id="<%=count%>"
				onclick="deleteService(this.id)" /></th>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>