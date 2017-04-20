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
		<% String lat_item = item.getX().toString() ;
		String lng_item = item.getY().toString() ;
	%>
		<div id="item-container" style="width: 50%; height: 550px;float:left; overflow-y: auto;">
	
		<img src="<%=item.getImage() %>"
			style="width:50%; height: 250px; float: left; font-size: 120%">
		<table style="float: left; margin-left: 3px; width:30%; height:250px; border: 1px solid black;">
			<tr>
				<th>Item:</th>
				<td><%=item.getTitle() %></td>
			</tr>
			<tr>
				<th>Lender:</th>
				<td><a
					href="ProfilePage.jsp?user=<%=item.getLender() %>&userType=lender"><%=item.getLender() %></a>
				</td>
			<tr>
				<th>Status:</th>
				<td>
					<%if (item.getRenter() == null) {%>AVAILABLE <%} else {%>Rented by <a
					href="ProfilePage.jsp?user=<%=item.getRenter()%>&userType=renter">
						<%=item.getRenter()%></a>
					<%}%>
				</td>
			<tr>
				<th>Price: $</th>
				<td><%=item.getPrice()%></td>
			</tr>
			<tr>
				<th>Rent from:</th>
				<td><%=item.getStartDate()%> to <%=item.getEndDate()%></td>
			</tr>
			<tr>
				<th>Description:</th>
				<td><%=item.getDescription()%></td>
			</tr>
			<tr>
				<%if (userType.equals("renter") && item.getRenter() == null) { %>
				<th>
					<button id="lendnew-button" onclick="displayPopup()">Rent
						This Item</button>
				</th>
				<%} %>
			</tr>


		</table>
		
	</div>
	<div id="map" style="width: 50%; height: 700px; float: right;"></div>
		<script type="text/javascript">
			function displayPopup() {
				document.getElementById('popup').style.display = 'block';
			}
			
			function closePopup() {
				document.getElementById('popup').style.display = 'none';
			}
			   function initMap() {
				      	var icon_ = '<%= item.getImage() %>';
				 		 var lat_ = '<%= lat_item %>';
				 	   var lng_ = '<%= lng_item %>';
				 	   var location = new google.maps.LatLng(lat_,lng_);

				         var map = new google.maps.Map(document.getElementById('map'), {
				           zoom: 9,
				           center: new google.maps.LatLng(lat_,lng_),
				 			mapTypeId: 'terrain'

				         });

				 	var marker = new google.maps.Marker({
				 	    map: map,
				 	    draggable: true,
				 	    animation: google.maps.Animation.DROP,
				 	    position: location
				 	  });
				 	marker.setMap(map);
				 	
				 	
				 	  google.maps.event.addListener(marker,'click',function() {
				 		    map.setZoom(12);
				 		    map.setCenter(marker.getPosition());
				 		  });
				 	  google.maps.event.addListener(marker,'click',function() {
				 		    var infowindow = new google.maps.InfoWindow({
				 		      content:'<%=item.getTitle() %>'
				 		    });
				 		  infowindow.open(map,marker);
				 		  });
				 	}

		</script>
			<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDp3SIzf3bZ3WAW-Pf5Qmdxs20pCfVIz-U
&callback=initMap"></script>
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