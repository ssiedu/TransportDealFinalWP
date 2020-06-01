<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Deals</title>
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
<div >
<div style="float:left;width:10%;">
<h3 align="center">Filters</h3>
<form action="applyfilter" method="post">
<table>
<tr><td><input type="checkbox" name="deal" value="price">Price</td></tr>
<tr><td><input type="checkbox" name="deal" value="dept_time">Departure Time</td></tr>
<tr><td><input type="checkbox" name="deal" value="arr_time">Arrival Time</td></tr>
<tr><td><input type="checkbox" name="deal" value="from_location">From Location</td></tr>
<tr><td><input type="checkbox" name="deal" value="to_location">To Location</td></tr>
<tr><td align="center"><input type="submit" value="APPLY"></td></tr>
</table>
</form>
</div>
<div style="float:left;width:90%;">
<h3 align="center">Deals Available</h2>
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
<th>BOOK DEAL</th>
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
	<td><a href="buydeal/?id=${item.deal_id}&tk=${tokens[item.deal_id] }">BOOK</a></td>

</tr>
</c:forEach>
</table>
</div>
</div>

<a href="/TransportPortal/customer/" class="topcorner">Home</a>
</body>
</html>