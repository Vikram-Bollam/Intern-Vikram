<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="Validation.js"></script>
<style>
header, footer {
	padding: 1em;
	color: white;
	background-color: #4b42f4;
	clear: left;
	text-align: center;
	font-stretch: 12;
	font-size-adjust:
}

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

input[type=password] {
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
</head>
<body>
	<header>
	<h1>Reset Your Password</h1>
	</header>
	<%
	System.out.print("Coming here");
	String uid=request.getParameter("userId");
	%>
	<form action="ResetPass" onsubmit="return validatePassword()" method="post">
		<br> <br> <input type="hidden" name="name" value="<%= uid%>"/>
		<table>
			<tr>
				<td>New Password</td>
			</tr>
			<tr>
				<td><input type="password" name="pass" id="pass1" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"
                    title="Password (UpperCase, LowerCase and Number)" requried></td>
			<tr>
			<tr>
				<td>Confirm Password</td>
			</tr>
			<tr>
				<td><input type="password" id="pass2" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"
                    title="Password (UpperCase, LowerCase and Number)" requried></td>
			<tr>
			<tr>
				<td><input type="submit" value="Update !!"></td>
			<tr>
			</tr>
		</table>
	</form>
</body>
</html>