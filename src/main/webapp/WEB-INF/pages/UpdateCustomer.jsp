<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
 .topcorner{
   position:absolute;
   top:28px;
   right:81px;
  }

</style>
</head>
<body>
<a href="/TransportPortal/customer/" class="topcorner">Home</a>
<h2 align="center">Update Customer</h2><hr>
<form:form modelAttribute="customer" action="updatecustomer">
<table align="center" cellspacing="10px" cellpadding="0">
<tr>
<td><input type="hidden" name="username" value="${sessionScope.username }"></td>
</tr>
<tr>
<td>Name</td><td><form:input path="customerName"/></td>
<td><form:errors path="customerName" cssClass="error"/></td>
</tr>
<tr>
<td>Email</td><td><form:input path="email"/></td>
<td><form:errors path="email" cssClass="error"/></td>
</tr>
<tr>
<td>Password</td><td><form:password path="password"/></td>
<td><form:errors path="password" cssClass="error"/></td>
</tr>
<tr>
<td>Confirm Password</td>
<td><input type="password" name="confpassword"></td>
<td class="error">${msg }</td>
</tr>
<tr>
<td>Phone</td><td><form:input path="phone"/></td>
<td><form:errors path="phone" cssClass="error"/></td>
</tr>

<tr><td colspan="2" align="center"><input type="submit" value="SUBMIT"></td></tr>
</table>
</form:form>
</body>
</html>