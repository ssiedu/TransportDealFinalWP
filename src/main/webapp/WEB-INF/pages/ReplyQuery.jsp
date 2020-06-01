<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ReplyQuery</title>
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
<h2 align="center">Reply Query</h2><hr>
<form action="replyquery" method="post">
<table align="center" cellspacing="0" cellpadding="15px">
<tr>
<td>Select Query</td>
<td>
<select name="qid">
		<c:forEach items="${customerQueries}" var="item">
			 <option value="${item.key}" ${item.key == qid ? 'selected="selected"' : ''}>${item.value}</option>
		</c:forEach>
</select>
</td>
<!-- <td><input type="hidden" name="query" value/></td> -->
</tr>
<tr><td colspan="2" align="center"><input type="submit" value="SUBMIT"></td></tr>
</table>
</form>

<c:if test="${not empty qid }">
<hr>
<form action="submitreply" method="post">
<table align="center" cellspacing="0" cellpadding="15px">
<tr>
<td><input type="hidden" name="qid" value="${qid }"/></td>
<td >Enter Reply</td>
<td ><textarea rows="4" cols="20" name="reply"></textarea></td>
</tr>
<tr>
<td colspan="3" align="center"><input type="submit" value="REPLY"></td>
</tr>
</table>
</form>
</c:if>
</form>
</body>
</html>