<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<link rel="stylesheet" type="text/css" href="/HomePage.css" />
<div id="navbar">
	<%@ page import="recurrent_CSCI201L_Project.JDBCTest" %>
	<%@ page import="recurrent_CSCI201L_Project.User" %>
	<%JDBCTest jdb = (JDBCTest)request.getSession().getAttribute("jdb");
	String userType = (String)request.getSession().getAttribute("userType");
	String username = (String)request.getSession().getAttribute("loggedUser");
	User user = jdb.getUser(username, userType);
	%>
	<%if (userType.equals("lender")) { %><a href="LenderHomePage.jsp">
	<%} else { %><a href="RenterHomePage.jsp"><% } %>
	<image id="navbar-logo" title = "View Feed" src="Logo.png">
	</a>
	<a href="ProfilePage.jsp?user=<%=user.getUsername()%>&userType=<%=user.getUserType()%>">
		<div style="height: 75px; border-left: 1px solid black; float: right;">
			<image id="profile-picture" class="navbar-item" title="Profile" src="<%=user.getImage() %>" style="margin-top: 10px">
		</div>
	</a>
	<a href="Messages.jsp">
		<div style="height: 75px; border-left: 1px solid black; float: right;">
			<image class="navbar-item" title="Inbox" src="mail.png" style="margin-top: 5px">
			<div style="margin-top: -15px;">(0)</div>
		</div>
	</a>
	
	<form name="searchForm" action="SearchServlet.jsp">
		<select class="navbar-item" id="navbar-dropdown" name="navbar-dropdown" style="margin-left: 20px;">
			<option value="Item">Search criteria</option>
			<option value="Item">Item</option>
			<option value="Username">User</option>
		</select>
		<input class="navbar-item" type="text" name="search" placeholder="Search Term">
		<input class="navbar-item" type="text" name="location" placeholder="Location">
		<input class="navbar-item" type="submit" style="width: 100px;"></input>
	</form>
</div>