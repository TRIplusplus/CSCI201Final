<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="recurrent_CSCI201L_Project.User"%>
<html>
<%
User u = (User)session.getAttribute("loggedUser");

%>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/HomePage.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Your Profile</title>
	</head>
	<body>
		<%@include file="Navbar.jsp" %>
		<div id="profile_outter">
			<div id="profile_image">
				<img alt = "not found" src = "<%=u.getImage() %>">
				<h1><%= u.getUsername() %> </h1>
				<h3><%= u.getUserType() %> </h3>
			</div>
			<div id="profile_email">
				<div>Email: <%=u.getEmailAddress() %></div>   
			</div>
		</div>
		
		
	</body>
</html>