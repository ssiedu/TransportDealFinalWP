<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Reply</title>
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
<h2 align="center">Queries Reply</h2><hr>
<table align="center" cellpadding="10px" cellspacing="0" border="1px">
<tr>
<th>Traders</th>
<th>Query</th>
<th>Reply</th>
</tr>
<c:forEach items="${replies}" var="item">
<tr>
<td>${item.transporter.companyName }</td>
<td>${item.query }</td>
<td>${item.reply }</td>
</tr>
</c:forEach>
</table>
</body>
</html>