<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
input[type=submit] {
	width: 50%;
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

input[type=submit]:hover {
	background-color: #45a049;
}

table {
	width: auto;
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
	<form action="DeleteAccount" method="post">
	<br>
	<br>
	<br>
	<table>
		<tr>
			<td>Enter Password</td>
		</tr>
		<tr>
			<td><input type="text" name="password" requried></td>
		<tr>
		<tr>
			<td><input type="submit" value="Delete"></td>
	</table>
	</form>
</body>
</html>