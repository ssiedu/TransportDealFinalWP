<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Dashboard</title>
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

<h2 align="center">Customer DashBoard</h2><hr>
<ul>
<li><a href="updateprofile">Update Profile</a></li>
<li><a href="querybycustomer">Query Transporter</a></li>
<li><a href="viewdeal">View Deals</a></li>
<li><a href="viewreply">View Replies</a></li>
<li><a href="dealhistory">Give Ratings</a></li>
<li style="float:right;margin-right:50px"><a href="logout">Logout</a></li>
</ul>
<h4 class="topcorner">Welcome ${sessionScope.username }</h4>
</body>
</html>