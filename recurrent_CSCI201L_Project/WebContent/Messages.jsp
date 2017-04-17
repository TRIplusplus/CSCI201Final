<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/HomePage.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Inbox</title>
	</head>
	<body>
		<%@include file="Navbar.jsp" %>
		<h1 style="text-align: center; margin-top: 10px;">Your Messages</h1>
		<table id="message-table">
			<thead id="message-table-header">
				<td>From</td>
				<td>Subject</td>	
				<td>Date</td>
			</thead>
			<%@ page import="java.util.ArrayList" %>
			<%@ page import="recurrent_CSCI201L_Project.Message" %>
			<% ArrayList<Message> messages = jdb.getMessagesForUser(jdb.getLoggedUser()); 
			if (messages == null) {%>
			<tr>
				<td colspan="3">You have no messages.</td>
			</tr>
			<% } else {
					for (int i=0; i<messages.size(); i++) {%>
					<% if(!messages.get(i).isRead()) {%> <strong> <% } %>
					<tr>
						<td><%=messages.get(i).getSender() %></td>
						<td><%=messages.get(i).getTitle() %></td>
						<td><%=messages.get(i).getDate().toString() %></td>
					</tr>
					<% if(!messages.get(i).isRead()) {%> </strong> <% } %>
			<%		} 
				}%>
				
		</table>
</body>
</html>