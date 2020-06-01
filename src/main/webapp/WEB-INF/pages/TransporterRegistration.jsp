<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.error {
color: #ff0000;
font-style: italic;
}
</style>
</head>
<body>
<h2 align="center">Transporter Registration</h2><hr>
<form:form action="addtransporter" modelAttribute="transporter" method="post" enctype="multipart/form-data"> 
		<table align="center" cellspacing="0" cellpadding="5px">
			<tr>
				<td>Company Name</td>
				<td><form:input path="companyName"/></td>
				<td><form:errors path="companyName" cssClass="error"/></td>
			</tr>
			<tr>
				<td>Email</td>
				<td><form:input path="email" /></td>
				<td><form:errors path="email" cssClass="error"/></td>
			</tr>
			<tr>
				<td>Address</td>
				<td><textarea rows="3" cols="18" name="address"></textarea></td>
			</tr>
			<tr>
				<td>Username</td>
				<td><form:input path="username" /></td>
				<td><form:errors path="username" cssClass="error"/></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><form:password path="password" /></td>
				<td><form:errors path="password" cssClass="error"/></td>
			</tr>
			
			<tr>
				<td>GST Registration</td>
				<td><input type="file" name="gst_reg" accept="application/pdf" required/></td>
			</tr>
			<tr>
				<td>PAN</td>
				<td><input type="file" name="pan_no" accept="application/pdf" required/></td>
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