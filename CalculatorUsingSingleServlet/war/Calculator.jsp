<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>	
		<h1>Welcome to the scientific calculator</h1>
		<table>
		<tr><td>Number 1:</td>
		<td><input type='text' name='number1'></td></tr>
		<tr><td>Number 2:</td>
		<td><input type='text' name='number2' ></td></tr>
		<tr><td>Operations:</td>
        <td><select name='opt'><option value='Addition'>Addition</option><option value='Subtraction'>Subtraction</option><option value='Multiplication'>Multiplication</option><option value='Division'>Division</option></select></td></tr>
		<tr><td>Result:</td>
		<td><input type='text' name='output' value=></td></tr>
		<tr><td><input type='submit' value='submit'><input type='submit' value='cancel'></td></tr>
		</table>
</body>
</html>