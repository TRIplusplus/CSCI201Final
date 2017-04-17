<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="recurrent_CSCI201L_Project.User"%>
<html>

	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/HomePage.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Your Profile</title>
	</head>
	<body>
		<%@include file="Navbar.jsp" %>
		<%
		User u = jdb.getUser(request.getParameter("user"), request.getParameter("userType"));
	
		%>
		<div id="profile_outter" style="text-align:center">
			<div id="profile_image">
				<img alt = "not found" src = "<%=u.getImage() %>" style="border-radius: 50%; max-height: 300px; margin-top: 20px;">
				<h1><%= u.getUsername() %> </h1>
				<h3><%= u.getUserType() %> </h3>
			</div>
			<div id="profile_email">
				<div>Email: <%=u.getEmailAddress() %></div>   
			</div>
		</div>
		<%if (u != user) { %>
		<button id="lendnew-button" onclick="displayPopup()">Send Message</button>
		<%} %>
		<div id="popup">
			<span id="popup-close" onclick="closePopup()">X</span>
			<form name="lenderForm" action="SendMessageServlet">
				<strong>Send A Message To <%=u.getUsername() %></strong><br><br>
				Title</br>
				<input type="input" name="title"><br/><br/>
				Message</br>
				<input type="input" name="image" style = "height: 300px;"><br/><br/>
				<input type="submit" value="Send Message">
			</form>
		</div>
	</body>
</html>