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
<title>Transporter Rating</title>
</head>
<body>
<a href="/TransportPortal/customer/" class="topcorner">Home</a>
<h2 align="center">Transporter Rating</h2><hr>
<table align="center" cellspacing="0px" cellpadding="7px" border="1">
<tr>
<th>Name</th>
<th>Email</th>
<th>Address</th>
<th>Rating</th>
<th>Deactivate</th>
</tr>
<c:forEach items="${transporterList}" var="map">
    <c:forEach items="${map}" var="entry">
    <tr><td>${entry.key.companyName}</td>
    <td>${entry.key.email}</td>
    <td>${entry.key.address}</td>
    <td>${entry.value}</td>
    <td><a href="deactivatetransporter/?id=${entry.key.username }">Deactivate</a></td>
    </tr>
        <br>
        <br>
    </c:forEach>
</c:forEach>

</table>
</body>
</html>
