<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/HomePage.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Homepage</title>
	</head>
	<body>
		<%@include file="Navbar.jsp" %>
		<div id="lent-items" style="text-align: center">
			<h2>Search to begin your experience.</h2>
			<form name="searchForm" method="GET" action="SearchPage.jsp">
				<input style="width: 300px; height: 30px; font-size: 110%; margin: 5px;" type="text" name="search" placeholder="Search Term">
				<input style="width: 300px; height: 30px; font-size: 110%; margin: 5px;" type="text" name="location" placeholder="Location">
				<input style="height: 40px; width: 100px; font-size: 105%" type="submit" style="width: 100px;"></input>
			</form>
			<h3>Currently Renting Items:</h3>
			<div id="lent-items-table" style="text-align: center;">
			<%@ page import="java.util.ArrayList" %>
			<%@ page import="recurrent_CSCI201L_Project.Item" %>
			<%ArrayList<Item> items = jdb.getItemsForRenter(username);
			if (items == null || items.size() == 0) {
			%>
				You are not renting any items.<br>
				Use the search above to search for items.
			<%} else {%>
				<table style="border: 0px; width: 60%; margin-left: 17%">
					<% for (int i=0; i<items.size(); i+=3) {%>
						<tr>
						<%for (int j=i; j<i+3; j++) {
							if (j<items.size()) {%>
							<td>
								<a href="ItemPage.jsp?id=<%=items.get(j).getID()%>">
								<img src="<%=items.get(j).getImage()%>" style="max-width: 100px; max-height: 100px;"></br>
								<%=items.get(j).getTitle()%>
								</a>
							</td>
			<% 				}
						}%>
						</tr>
			<%		}%>
				</table>
			<%} %>
			</div>
		</div>
	</body>
</html>