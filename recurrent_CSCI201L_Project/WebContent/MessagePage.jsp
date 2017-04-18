<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/HomePage.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%@ page import="recurrent_CSCI201L_Project.Message" %>
		<title>Message</title>
	</head>
	<body>
		<%@include file="Navbar.jsp" %>
		<a href="Messages.jsp">Back</a>
		<% Message message = jdb.getMessageByID(Integer.parseInt(request.getParameter("id")));
			System.out.println(message);
			jdb.readMessage(Integer.parseInt(request.getParameter("id")));%>
		<div id="message-container">
			<h1><%=message.getTitle()%></h1>
			From: <%=message.getSender()%></br>
			Date: <%=message.getDate().toString() %>
			<p><%=message.getMessage() %></p>
		</div>
	</body>
</html>