<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Rate Transporter</title>
</head>
<body>
<h2 align="center">Rate Transporter</h2><hr>
<form action="submitrating" method="post">
<table align="center" cellpadding="16px">
<tr>
<td><input type="hidden" name="tid" value="${tid }"></td>
<td><input type="hidden" name=deal_id value="${deal_id }"></td>
<td><input type="hidden" name=tk value="${tk }"></td>
</tr>
<tr>
<td>Give Rating</td>
<td>
<select name="rating" >
		<c:forEach items="${ratings}" var="item">
					  <option>${item}</option>
			</c:forEach> 
</select>
</td>
</tr>
<tr><td colspan="2" align="center"><input type="submit" value="Submit"></tr>
</table>
</form>
</body>
</html>