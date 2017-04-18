<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/HomePage.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<%@ page import="recurrent_CSCI201L_Project.Item" %>
		<title>Message</title>
	</head>
	<body>
		<%@include file="Navbar.jsp" %>
		<% Item item = jdb.getItemByID(Integer.parseInt(request.getParameter("id")));%>
		<div id="item-container">
			<img src="<%=item.getImage() %>" style="height: 400px; float: left; font-size: 120%">
			<div style="float: left; margin-left: 20px;">
				<h2><%=item.getTitle() %></h2>
				Lender: <a href="ProfilePage.jsp?user=<%=item.getLender() %>&userType=lender">
				<%=item.getLender() %></a></br>
				Status: <%if (item.getRenter() == null) {%>AVAILABLE
						<%} else {%>Rented by <a href="ProfilePage.jsp?user=<%=item.getRenter()%>&userType=renter">
						<%=item.getRenter()%></a><%}%>
						</br>
				Price: $<%=item.getPrice()%></br>
				Rent from: <%=item.getStartDate()%> to <%=item.getEndDate()%></br>
				Description: <%=item.getDescription()%></br></br>
				<%if (userType.equals("renter") && item.getRenter() == null) { %>
				<button id="lendnew-button" onclick="displayPopup()">Rent This Item</button>
				<%} %>
			</div>
		</div>
		<script type="text/javascript">
			function displayPopup() {
				document.getElementById('popup').style.display = 'block';
			}
			
			function closePopup() {
				document.getElementById('popup').style.display = 'none';
			}
		</script>
			
		<div id="popup" style="height: 130px">
			<span id="popup-close" onclick="closePopup()">X</span>
			<form name="lenderForm">
				<h3>Are you sure you wish to rent this item?</h3>
				<button onclick="closePopup()">No</button>
				<input type ="button" name="signup" value="Yes" onclick="location.href='${pageContext.request.contextPath}/RentItemServlet?id=<%=request.getParameter("id")%>'"/>
			</form>
		</div>
	</body>
</html>