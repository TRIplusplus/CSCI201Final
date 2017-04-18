<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/HomePage.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Search Results</title>
	</head>
	<body>
		<%@include file="Navbar.jsp" %>
		<%@ page import="java.util.ArrayList" %>
		<%@ page import="recurrent_CSCI201L_Project.Item" %>
		<%ArrayList<Item> results = new ArrayList<Item>();
		String criteria = request.getParameter("criteria");
		String search = request.getParameter("search");
		String location = request.getParameter("location");
		double x = 0;
		double y = 0;
		if(!search.isEmpty() && !location.isEmpty()) {
			results = jdb.getItemsBySearchAndLocation(search, x, y);
		} else {
			if (!search.isEmpty()) {
				results = jdb.getItemsForSearch(search);	
			} else if (!location.isEmpty()) {
				results = jdb.getItemsNearLocation(x, y);
			}
		}%>
		<div id="resultsTable" style="width: 50%; height: 500px;float:left; overflow-y: auto;">
			<%if (results.size() == 0) {%> <div style="position: relative; top: 10%; left: 45%">No search results.</div> 
			<%} else {%>
				<table style="width: 100%; border-collapse: collapse">
					<%for(int i=0; i<results.size(); i++) {
						if(results.get(i).getRenter() == null) {%>
					<tr>
						<td style="border: 1px solid black; padding: 10px; overflow: ellipsis;">
							<img src="<%=results.get(i).getImage()%>" style="height: 100px; float: left;">
							<div style="float: left; margin-left: 10px;">
								<strong><a href="ItemPage.jsp?id=<%=results.get(i).getID()%>"><%=results.get(i).getTitle() %></a></strong></br></br>
								Lender: <a href="ProfilePage.jsp?user=<%=results.get(i).getLender() %>&userType=lender">
								<%=results.get(i).getLender() %></a></br>
								Price: $<%=results.get(i).getPrice() %></br>
								Rent from: <%=results.get(i).getStartDate()%> to <%=results.get(i).getEndDate()%></br>
								Description: <%=results.get(i).getDescription()%>
							</div>
						</td>
					</tr>
			<%			}
					} %>
				</table>
			<%}%>
		</div>
		<div id="map" style="width:50%;height:550px;float:right;"></div>
		<script>
			function myMap() {
				var newcenter = new google.maps.LatLng(34.05349, -118.2453);
			  var myCenter = new google.maps.LatLng(51.508742,-0.120850);
			  
			  var mapCanvas = document.getElementById("map");
			  var mapOptions = {center: newcenter, zoom: 5};
			  var map = new google.maps.Map(mapCanvas, mapOptions);
			  var marker = new google.maps.Marker({position:myCenter});
			  var marker1 = new google.maps.Marker({position:newcenter});
			  marker.setMap(map);
			  marker1.setMap(map);	  
			}
		</script>
		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDp3SIzf3bZ3WAW-Pf5Qmdxs20pCfVIz-U&callback=myMap"></script>
		
	</body>
</html>