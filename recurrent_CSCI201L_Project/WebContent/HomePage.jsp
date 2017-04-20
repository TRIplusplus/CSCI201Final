<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>RecuRRent</title>
	<link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/HomePage.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  </head>
  <body id="login-page">
    <div class="wrapper">
    	</br>
    	</br>
    	<h3>Team 14 CS201</h3>
    	</br></br>
		<h1>RecuRRent</h1>
		<image style="float:right" title = "View Feed" src="Logo.png"></image>
		<h4>Rent, Lend, and Connect</h4>
		<div id = 'buttons'>
			<form name="inputForm" method="GET" action="${pageContext.request.contextPath}/DiscoverServlet">
				<input type ="button" name="login" value="LENDER LOGIN" onclick="location.href='${pageContext.request.contextPath}/LenderLogin.jsp'"/></br></br></br>
				<input type ="button" name="login" value="RENTER LOGIN" onclick="location.href='${pageContext.request.contextPath}/RenterLogin.jsp'"/></br></br></br>
				<input type ="button" name="signup" value="SIGN UP" onclick="location.href='${pageContext.request.contextPath}/SignUp.jsp'"/></br></br></br>
				<input type ="submit" name="discover" value="DISCOVER" onclick="location.href='${pageContext.request.contextPath}/DiscoverServlet"/>
			</form>
		</div>
    </div>
  </body>
</html>