<%@page import="com.full.controller.GoogleServlet"%>
<%@page import="full.googlesignin.UserInfoBean"%>
<%@page
	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="CallUrls.js"></script>
<style type="text/css">
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

#myService {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

#myTime {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

#myStaff {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
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

input[type=email] {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

input[type=date] {
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
</head>
<body onLoad="hitcall()">
	<%
		HttpSession ses = request.getSession(false);
		UserInfoBean info = (UserInfoBean) ses.getAttribute("name");
		String name = info.getName();
		String lname = info.getFamily_name();
		String email = info.getEmail();
	%>
	<br>
	<br>
	<br>
	<div id="main">
		<div id="main1">
			<script type="text/javascript">
				hitcall();
			</script>
			<table>
				<tr>
					<td>First Name</td>
					<td><input type="text" id="fname" value="<%=name%>" required></td>
				</tr>
				<tr>
					<td>Last Name</td>
					<td><input type="text" id="lname" value="<%=lname%>" required></td>
				</tr>
				<tr>
					<td>Your Email</td>
					<td><input type="email" value="<%=email%>" id="email" required></td>
				</tr>
				<tr>
					<td>Services</td>
					<td><div id="services"></div></td>
				</tr>
				<tr>
					<td><div id="staffDisplay"></div></td>
					<td><div id="staff"></div></td>
				</tr>
				<tr>
					<td><div id="dateDisplay"></div></td>
					<td><div id="date"></div></td>
				</tr>
				<tr>
					<td><div id="dateSlot"></div></td>
					<td><div id="time"></div></td>
				</tr>
				<tr>
					<td><input type="button" value="Book" onclick="finalBook()"></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>