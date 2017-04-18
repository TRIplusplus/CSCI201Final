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
		<style>
			td a {color: grey; text-decoration: none;}
		</style>
		<h1 style="text-align: center; margin-top: 10px;">Your Messages</h1>
		<table id="message-table">
			<thead id="message-table-header">
				<td style="border: 1px solid black;" width="20%">From</td>
				<td style="border: 1px solid black;" width="50%">Subject</td>	
				<td style="border: 1px solid black;" width="20%">Date</td>
			</thead>
			<%@ page import="java.util.ArrayList" %>
			<%@ page import="recurrent_CSCI201L_Project.Message" %>
			<% ArrayList<Message> messages = jdb.getMessagesForUser(jdb.getLoggedUser()); 
			if (messages == null) {%>
			<tr>
				<td colspan="3">You have no messages.</td>
			</tr>
			<% } else {
					for (int i=messages.size()-1; i>=0; i--) {%>
					<tr style="color: black; text-decoration: none;">
					
						<td style="border: 0px"><a href="MessagePage.jsp?id=<%=messages.get(i).getID()%>"><% if(!messages.get(i).isRead()) {%> <b> <% } %><%=messages.get(i).getSender() %><% if(!messages.get(i).isRead()) {%> </b> <% } %></a></td>
						<td style="border: 0px"><a href="MessagePage.jsp?id=<%=messages.get(i).getID()%>"><% if(!messages.get(i).isRead()) {%> <b> <% } %><%=messages.get(i).getTitle() %><% if(!messages.get(i).isRead()) {%> </b> <% } %></a></td>
						<td style="border: 0px"><a href="MessagePage.jsp?id=<%=messages.get(i).getID()%>"><% if(!messages.get(i).isRead()) {%> <b> <% } %><%=messages.get(i).getDate().toString() %><% if(!messages.get(i).isRead()) {%> </b> <% } %></a></td>
					
					</tr>
			<%		} 
				}%>
				
		</table>
</body>
</html>