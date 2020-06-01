<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<style>
  ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

li {
  float: left;
}

li a {
  display: block;
  padding: 20px;
  background-color: white;
  text-decoration: none;
  
}
li a:visited{
	color:#453DF1;
}
li a:hover {
  background-color: #CDEAFB;
}
</style>
<meta charset="UTF-8">
<title>Admin</title>
</head>
<body>
${msg }
<h2 align="center">Welcome Admin</h2><hr>
<ul>
<li><a href="viewtransporter">View Transporters</a></li>
<li><a href="viewvehicle">View Vehicles</a></li>
<li><a href="viewdeal">View Deals</a></li>
<li><a href="viewratings">View Ratings</a></li>
<li style="float:right;margin-right:50px"><a href="logout">Logout</a></li>
</ul>
</body>
</html>
