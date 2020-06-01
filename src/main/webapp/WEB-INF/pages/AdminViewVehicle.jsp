<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.topcorner{
   position:absolute;
   top:28px;
   right:81px;
  }
  </style>
<meta charset="UTF-8">
<title>View Vehicle</title>
</head>
<body>
<a href="/TransportPortal/customer/" class="topcorner">Home</a>
<h2 align="center">Vehicle Details</h2><hr>
<table align="center" cellspacing="0" cellpadding="8px" border=1>
<tr>
<th>Registration No</th>
<th>Vehicle Name</th>
<th>Brand</th>
<th>Price</th>
<th>Type</th>
<th>Transporter Name</th>
<th>View RC File</th>
<th>View Insurance File</th>

<th>Validate</th>
</tr>
<c:forEach items="${vehicleList}" var="item">
<tr>
	<td>${item.reg_no }</td>
	<td>${item.vehicleName }</td>
	<td>${item.brand }</td>
	<td>${item.price }</td>
	<td>${item.vehicleType.type }</td>
	<td>${item.transporter.companyName }</td>
	<td><a href="viewvehiclefile/?fname=${item.rc_filename }">View RC File</a></td>
	<td><a href="viewvehiclefile/?fname=${item.insurance_filename }">View Insurance File</a></td>
	<td><a href="validatevehicle/?id=${item.reg_no }">Validate</a></td>

</tr>
</c:forEach>

</table>
</body>
</html>