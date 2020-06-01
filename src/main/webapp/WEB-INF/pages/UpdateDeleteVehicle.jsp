<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UpdateDeleteVehicle</title>
<style>
.topcorner{
   position:absolute;
   top:28px;
   right:81px;
  }
  </style>
</head>
<body>
<a href="/TransportPortal/customer/" class="topcorner">Home</a>
<p>${msg }</p>
<h2 align="center">Vehicle Details</h2><hr>
<table align="center" cellpadding="9px" cellspacing="0" border="1px">
<tr>
<th>RegNo</th><th>Vehicle Name</th><th>Brand</th><th>Type</th>
<th>Price</th><th>RegNoFile</th><th>InsuranceFile</th>
<th>Update</th><th>Delete</th>
</tr>
<c:forEach items="${vehicleList}" var="item" varStatus="status">
<tr>
	<td>${item.reg_no }</td>
	<td>${item.vehicleName }</td>
	<td>${item.brand }</td>
	<td>${item.vehicleType.type }</td>
	<td>${item.price }</td>
	<td>${item.rc_filename }</td>
	<td>${item.insurance_filename }</td>
	<td><a href="updatevehicle/?id=${item.reg_no }&tk=${tokens[status.index] }">UPDATE</a></td>
	<td><a href="deletevehicle/?id=${item.reg_no }&tk=${tokens[status.index] }">DELETE</a></td>

</tr>
</c:forEach>
</table>
<hr>
<p>${msg }</p>
</body>
</html>