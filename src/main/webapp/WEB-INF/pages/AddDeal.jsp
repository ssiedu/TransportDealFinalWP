<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Deal</title>
<style type="text/css">
.topcorner{
   position:absolute;
   top:28px;
   right:81px;
  }
.error {
color: #ff0000;
font-style: italic;
}
  </style>
</head>
<body>
<a href="/TransportPortal/customer/" class="topcorner">Home</a>
<h2 align="center">Add Deal</h2><hr>
<form:form action="adddeal" modelAttribute="deal" method="post" enctype="multipart/form-data"> 
		<table align="center" cellspacing="0" cellpadding="5px">
			<tr>
				<td>From Date</td>
				<td><input type="date" name="dept_time" required/></td>
				<td><form:errors path="dept_time" cssClass="error"/></td>
			</tr>
			<tr>
				<td>To Date</td>
				<td><input type="date" name="arr_time" required/></td>
				<td><form:errors path="dept_time" cssClass="error"/></td>
				<c:if test="${not empty msg}"><td style="color: #ff0000;font-style: italic;">${msg }</td></c:if>
			</tr>
			<tr>
				<td>From Location</td>
				<td><form:input path="from_location"/></td>
				<td><form:errors path="from_location" cssClass="error"/></td>
			</tr>
			<tr>
				<td>To Location</td>
				<td><form:input path="to_location"/></td>
				<td><form:errors path="to_location" cssClass="error"/></td>
			</tr>
			<tr>
				<td>Registered Vehicles</td>
				<td>
				<select name="vname" >
				<c:forEach items="${vnames}" var="item">
					  <option value="${item.key}">${item.value}</option>
				</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td>Price</td>
				<td><form:input path="price"/></td>
				<td><form:errors path="price" cssClass="error"/></td>
			</tr>
			<tr > 
				<td align="center" colspan="2"><input type="submit" value="submit"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>