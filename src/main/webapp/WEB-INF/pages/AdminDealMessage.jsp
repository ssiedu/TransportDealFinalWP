<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Deal Message</title>
</head>
<body>
<h2 align="center">ADD REASON</h2><hr>
<form action="removedeal" method="post">
<input type="hidden" name="did" value="${deal_id }">
<input type="hidden" name="tid" value="${tid }">
<table align="center" cellpadding="8px">
<tr>
<td>
REASON<br><textarea rows="4" cols="30" name="response"></textarea>
</td>
</tr>
<tr>
<td>
<input type="submit" value="SUBMIT">
</td>
</tr>
</table>
</form>
</body>
</html>
