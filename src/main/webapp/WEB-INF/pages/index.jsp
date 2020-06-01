<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<style>
.error {
color: #ff0000;
font-style: italic;
}
</style>
</head>
<body>
	<h2 align="center">Transportation Deal Portal</h2>
	<hr>
	<!-- <a href="email">Email</a>
	<a href="upl">Upload</a> -->
	<form:form action="login" modelAttribute="login"> 
		<table align="center" cellspacing="0" cellpadding="5px">
			<tr>
				<td>username</td>
				<td><form:input path="username" /></td>
				<td><form:errors path="username" cssClass="error"/></td>
			</tr>
			<tr>
				<td>password</td>
				<td><form:password path="password" /></td>
				<td><form:errors path="password" cssClass="error"/></td>
			</tr>
			<tr>
				<td align="center">Transporter</td>
				<td><form:radiobutton path="userType" value="transporter" /></td>
				<td align="center">Customer</td>
				<td><form:radiobutton path="userType" value="customer"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="SUBMIT" /></td>
			</tr>

		</table>
	</form:form>
	<hr>
	<a href="customer/newcustomer">New Customer</a><br>
	<a href="transporter/newtransporter">New Transporter</a>
	
</body>
</html>
