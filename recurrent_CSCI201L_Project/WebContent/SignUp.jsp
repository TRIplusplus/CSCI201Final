<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Lender Login</title>
	<link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/HomePage.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  </head>
  <body>
    <div class="wrapper">
    	</br>
    	</br>
    	<h3>Team 14 CSCI201</h3>
    	</br></br>
		<h1>RecuRRent</h1>
		<image style="float:right" title = "View Feed" src="Logo.png"></image>
		<h4 style="margin-left: 295px;">Sign Up</h4>
		<div id = 'buttons'>
			<form id="loginForm" name="inputForm" method="GET" action="LoginServlet?user=lender">
				Username</br>
				<input type ="text" name="username"/></br>
				Password</br>
				<input type ="text" name="password"/></br>
				Email</br>
				<input type ="text" name="email"/></br>
				I will be a</br>
				<input type="radio" name="renter">Renter
				<input type="radio" name="lender">Lender
				<input type="radio" name="both">Both</br></br>
				<input type ="submit" name="login" value="SIGN UP" />
			</form>
		</div>
    </div>
  </body>
</html>