<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Deal</title>
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
<h2 align="center">Update Deal</h2><hr>
<form:form modelAttribute="deal" action="updatedeal">
<c:set var="nameHasBindError">
		<form:errors path="arr_time"/>
	</c:set>
<table align="center" cellspacing="10px" cellpadding="0">
<tr>
<td><input type="hidden" name="id" value="${deal.deal_id }"/></td>
</tr>

<tr>
<td><input type="hidden" name="tk" value="${tk }"/></td>
</tr>
<tr>
<td><input type="hidden" name="transporter" value="${sessionScope.username }"/></td>
</tr>
<tr>
<td>Departure Time</td><td><input type="date" name="dept_time" required/></td>
<td><form:errors path="dept_time" cssClass="error"/></td>
</tr>
<tr>
<td>Arrival Time</td><td><input type="date" name="arr_time" required/></td>
<td><form:errors path="arr_time" cssClass="error"/></td>
<%-- <td>${nameHasBindError }</td> --%>
<c:if test="${not empty msg}"><td style="color: #ff0000;font-style: italic;">${msg }</td></c:if>
</tr>
<tr>
<td>From Location</td><td><form:input path="from_location"/></td>
<td><form:errors path="from_location" cssClass="error"/></td>
</tr>
<tr>
<td>To Location</td><td><form:input path="to_location"/></td>
<td><form:errors path="to_location" cssClass="error"/></td>
</tr>
<tr>
<td>Vehicle Name</td>
<td>
<select name="vname" required>
	<c:forEach items="${vnames}" var="item">
		<option value="${item.key}" ${item.key == vid ? 'selected="selected"' : ''}>${item.value}</option>
	</c:forEach>
</select>
</td>
</tr>
<tr>
<td>Price</td><td><form:input path="price"/></td>
<td><form:errors path="price" cssClass="error"/></td>
</tr>
<tr><td colspan="2" align="center"><input type="submit" value="SUBMIT"></td></tr>
</table>
</form:form>
</body>
</html>