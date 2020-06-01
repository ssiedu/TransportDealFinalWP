<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Show Deals</title>
<style type="text/css">
.topcorner{
   position:absolute;
   top:28px;
   right:81px;
  }
  </style>
</head>
<body>
<a href="/TransportPortal/customer/" class="topcorner">Home</a>
<h2 align="center">View Deals</h2><hr>
<table align="center" cellspacing="0" cellpadding="6px" border="1px">
<tr>
<th>From Location</th>
<th>To Location</th>
<th>Departure Time</th>
<th>Arrival Time</th>
<th>Vehicle No</th>
<th>Vehicle Name</th>
<th>Vehicle Type</th>
<th>Price</th>
<th>Transporter Name</th>
<th>UPDATE DEAL</th>
</tr>
<c:forEach items="${dealList}" var="item" varStatus="status">
<tr>
	<td>${item.from_location }</td>
	<td>${item.to_location }</td>
	<td>${item.dept_time }</td>
	<td>${item.arr_time }</td>
	<td>${item.vehicle.reg_no }</td>
	<td>${item.vehicle.vehicleName }</td>
	<td>${item.vehicle.vehicleType.type }</td>
	<td>${item.price }</td>
	<td>${item.transporter.companyName }</td>
	<td><a href="updatedealform?id=${item.deal_id}&tk=${tokens[status.index]}">UPDATE</a></td>

</tr>
</c:forEach>
</table>
</body>
</html>