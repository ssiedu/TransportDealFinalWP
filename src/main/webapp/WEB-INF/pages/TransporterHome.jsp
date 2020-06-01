<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transporter Dashboard</title>
<style>
.topcorner{
   position:absolute;
   top:10px;
   right:81px;
  }
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
</head>
<body>
<h2 align="center">Transporter Dashboard</h2><hr>
<ul>
<li><a href="replyquery">Reply Queries</a></li>
<li><a href="newvehicle">Add Vehicle</a></li>
<li><a href="updatedeletevehicle">View Vehicles</a></li>
<li><a href="newdeal">Add Deal</a></li>
<li><a href="showdeals">View Deals</a></li>
<li style="float:right;margin-right:50px"><a href="logout">Logout</a></li>
</ul>
<h4 class="topcorner">Welcome ${sessionScope.username }</h4>
</body>
</html>