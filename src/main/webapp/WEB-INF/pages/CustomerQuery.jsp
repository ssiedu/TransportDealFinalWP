<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CustomerQuery</title>
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
<h2 align="center">Ask Query</h2><hr>
<form action="querybycustomer" method="post">
<table align="center" cellspacing="0" cellpadding="15px">
<tr>
<td>Select Transporter</td>
<td>
<select name="tid">
		<c:forEach items="${transporters}" var="item">
			 <option value="${item.key}"  ${item.key == tid ? 'selected="selected"' : ''}>${item.value}</option>
		</c:forEach>
</select>
</td>
</tr>
<tr><td colspan="2" align="center"><input type="submit" value="SUBMIT"></td></tr>
</table>
</form>
<c:if test="${not empty tid }">
<hr>
<form action="submitquery" method="post">
<table align="center" cellspacing="0" cellpadding="15px">
<tr>
<td><input type="hidden" name="tid" value="${tid }"/></td>
<td>Enter Query</td>
<td><textarea rows="4" cols="20" name="query"></textarea></td>
<td><input type="submit" value="SUBMIT"></td>
</tr>
</table>
</form>
</c:if>
</body>
</html>