<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>RegisterCustomer</title>
<style>
.error {
color: #ff0000;
font-style: italic;
}
</style>
</head>
<body>
<h2 align="center">Customer Registration</h2><hr>
<form:form action="addcustomer" modelAttribute="customer" method="post"> 
		<table align="center" cellspacing="0" cellpadding="5px">
			<tr>
				<td>Username</td>
				<td><form:input path="username" /></td>
				<td><form:errors path="username" cssClass="error"/></td>
			</tr>
			<tr>
				<td>Name</td>
				<td><form:input path="customerName" /></td>
				<td><form:errors path="customerName" cssClass="error"/></td>
			</tr>
			<tr>
				<td>Email</td>
				<td><form:input path="email" /></td>
				<td><form:errors path="email" cssClass="error"/></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><form:password path="password" /></td>
				<td><form:errors path="password" cssClass="error"/></td>
			</tr>
			<tr>
				<td>Phone</td>
				<td><form:input path="phone" /></td>
				<td><form:errors path="phone" cssClass="error"/></td>
			</tr>
			<tr > 
				<td align="center" colspan="2"><input type="submit" value="submit"></td>
			</tr>
		</table>
	</form:form>
	<hr>
	<a href="/TransportPortal/">Login</a>
</body>
</html>