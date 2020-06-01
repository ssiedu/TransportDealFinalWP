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
<title>View Transporter</title>
</head>
<body>
<a href="/TransportPortal/customer/" class="topcorner">Home</a>
<h2 align="center">Transporter Details</h2><hr>
<table align="center" cellspacing="0" cellpadding="8px" border=1>
<tr>
<th>Username</th>
<th>Company Name</th>
<th>Email</th>
<th>Address</th>
<th>View GST File</th>
<th>View PAN File</th>
<th>Validate</th>
</tr>
<c:forEach items="${transporterList}" var="item">
<tr>
	<td>${item.username }</td>
	<td>${item.companyName }</td>
	<td>${item.email }</td>
	<td>${item.address }</td>
	<td><a href="viewtransporterfile/?fname=${item.gst_reg_filename }">View GST File</a></td>
	<td><a href="viewtransporterfile/?fname=${item.pan_filename }">View PAN File</a></td>
	<td><a href="validatetransporter/?id=${item.username }">Validate</a></td>
		

</tr>
</c:forEach>

</table>
</body>
</html>
