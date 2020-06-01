<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Vehicle</title>
<style>
.error {
color: #ff0000;
font-style: italic;
}
.topcorner{
   position:absolute;
   top:28px;
   right:81px;
  }
</style>
</head>
<body>
	<a href="/TransportPortal/customer/" class="topcorner">Home</a>
<h2 align="center">Update Vehicle</h2><hr>
<form action="updateinsurance" method="post" enctype="multipart/form-data" >
<table align="center" cellscaping="0" cellpadding="9px">
<tr><td><input type="hidden" name="reg_no" value="${vehicle.reg_no }"></td></tr>
<tr><td><input type="hidden" name="tk" value="${tk }"></td></tr>
<tr><td>Registration No</td><td>${vehicle.reg_no }</td></tr>
<tr><td>Vehicle Name</td><td>${vehicle.vehicleName }</td></tr>
<tr><td>Brand</td><td>${vehicle.brand }</td></tr>
<tr><td>Type</td><td>${vehicle.vehicleType.type }</td></tr>
<tr><td>Price</td><td>${vehicle.price }</td></tr>
<tr><td>View RC</td><td><a href="viewvehiclefile/?fname=${vehicle.rc_filename }&tk=${rc_tk}">Click Here</a></td></tr>
<tr><td>View InsuranceFile</td><td><a href="viewvehiclefile/?fname=${vehicle.insurance_filename }&tk=${ins_tk}">Click Here</a></td></tr>
<tr><td>Update Insurance File</td><td><input type="file" name="insfile" accept="application/pdf" required/></td></tr>
<tr><td colspan="2" align="2"><input type="submit" value="SUBMIT"></td></tr>
</table>
</form>

</body>
</html>