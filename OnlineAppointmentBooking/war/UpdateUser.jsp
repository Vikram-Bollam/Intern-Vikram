<%@page import="java.text.DateFormat"%>
<%@page import="java.sql.Time"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="com.google.appengine.api.datastore.Key"%>
<%@page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@page
	import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
input[type=submit] {
    width: 40%;
    background-color: #4CAF50;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}
input[type=button] {
    width: 40%;
    background-color: #4CAF50;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    }
.s {
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
	<form action="UpdateUser" method="post">
		<%
			String currentuser = (String) session.getAttribute("userSession");
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			Key k = KeyFactory.createKey("Users", currentuser);
			Entity caught = ds.get(k);
			String uname = (String) caught.getProperty("UserName");
			String uemail = (String) caught.getProperty("UserEmail");
			String uphone = (String) caught.getProperty("UserPhone");
			String bname = (String) caught.getProperty("BusinessName");
			String upass = (String) caught.getProperty("userPass");
			String otime = (String) caught.getProperty("OpenTime");
			String ctime = (String) caught.getProperty("CloseTime");
		%>
		<table>
			<tr> 
				<td><input type="text" placeholder="Your Name" class="s"
					value="<%=uname%>" name="pname" class="vikram" pattern=".{4,}"
					title="Name Should be Six or more characters" required="required"></td>
			</tr>
			<tr>
				<td><input type="email" placeholder="Your EMail" name="email" class="s"
					value="<%=uemail%>" class="vikram" required="required"></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="Your Business Name" class="s"
					value="<%=bname%>" name="bname" class="vikram" pattern=".{4,}"
					title="Business Name Should be Six or more characters"
					required="required"></td>
			</tr>
			<tr>
				<td><input type="password" placeholder="Password" name="pass"
					value="<%=upass%>" class="s"
					pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"
					title="Password (UpperCase, LowerCase and Number)"
					required="required"></td>
			</tr>
			<tr>
				<td><input type="text" placeholder="Phone Number" name="phone"
					value="<%=uphone%>" class="s" pattern="[7|8|9][0-9]{9}"
					title="Should start with [7,8,9] and 10 numbers"></td>
			</tr>
			<tr>
				<td>Opening Time : <input type="time" name="openTime" class="s"
					value="<%=otime%>"></td>
			</tr>
			<tr>
				<td>Closing Time : <input type="time" name="closeTime" class="s"
					value="<%=ctime%>"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Update"> &nbsp &nbsp &nbsp
				<input type="button" value="Delete Account !!! " onclick="deleteUser()"></td>
			</tr>
		</table>

	</form>
</body>
</html>