<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Vehicle</title>
<style>
.msg {
color: #ff0000;
font-style: italic;
}
.topcorner{
   position:absolute;
   top:28px;
   right:81px;
  }
.error {
color: #ff0000;
font-style: italic;
}
</style>
</head>
<body>
<h2 align="center">Add Vehicle</h2><hr>
<form:form action="addvehicle" modelAttribute="vehicle" method="post" enctype="multipart/form-data"> 
		<table align="center" cellspacing="0" cellpadding="5px">
			<tr>
				<td>Name</td>
				<td><form:input path="vehicleName"/></td>
				<td><form:errors path="vehicleName" cssClass="error"/></td>
				
			</tr>
			
			<tr>
				<td>Registration No</td>
				<td><form:input path="reg_no"/></td>
				<td><form:errors path="reg_no" cssClass="error"/></td>
			</tr>
			<tr><td colspan="2" align="center"><label class="msg">if registration no is MP 09 SL 0000 then use MP09SL0000</label></td></tr>
			<tr>
				<td>brand</td>
				<td><form:input path="brand" /></td>
				<td><form:errors path="brand" cssClass="error"/></td>
			</tr>
			<tr>
				<td>Type</td>
				<td>
				<select name="type">
				<c:forEach items="${vtype}" var="item">
					  <option value="${item.key}">${item.value}</option>
				</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td>price</td>
				<td><form:input path="price"/></td>
				<td><form:errors path="price" cssClass="error"/></td>
			</tr>
			<tr>
				<td>RC File</td>
				<td><input type="file" accept="application/pdf" name="rc_file" required/></td>
			</tr>
			<tr>
				<td>Insurance File</td>
				<td><input type="file" name="insurance_file" accept="application/pdf" required/></td>
			</tr>
			
			<tr > 
				<td align="center" colspan="2"><input type="submit" value="submit"></td>
			</tr>
		</table>
	</form:form>
	<a href="/TransportPortal/customer/" class="topcorner">Home</a>
</body>
</html>