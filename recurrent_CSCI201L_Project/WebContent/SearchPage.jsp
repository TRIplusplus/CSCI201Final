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
		<%@include file="Alert.jsp" %>
		<%if (userType !=null) {%>
		<script type="text/javascript">	
			var mailcount = <%=jdb.countUnreadMessages(username)%>;
			var itemcount = <%=jdb.getAllItems().size()%>;
			
			function check() {
				checkMail(mailcount)
				checkNewItem(itemcount);
			}
			
			window.onload = function windowCheck() {		
					setInterval("check()", 5000);
			}
			
			
			
			function checkMail(n) {
				var req = new XMLHttpRequest();
				var url = 'MailCheckServlet?mail=' + encodeURI(n);
				req.open("GET", url, true);
				req.onreadystatechange = function () {
					if(req.readyState == 4 && req.status == 200) {
						var object = JSON.parse(req.responseText);
						console.log(object);
						if (object.newchange == 'true') {
							document.getElementById('alert').style.display = 'block';
							document.getElementById('alert-img').src = object.image;
							document.getElementById('alert-title').innerHTML = 'NEW MESSAGE--<a href="ItemPage.jsp?id=' + object.id + '">' + object.title + '</a>';
							document.getElementById('alert-sender').innerHTML = 'From: ' + object.sender;
							document.getElementById('alert-description').innerHTML = object.description;
							n++;
							mailcount++;
							document.getElementById('message-count').innerHTML = n;
							return true;
						}
					}
				}
				req.send(null);
				return false;
			};
			
			function checkNewItem(n) {
				var req = new XMLHttpRequest();
				var url = 'ItemCheckServlet?count=' + encodeURI(n);
				req.open("GET", url, true);
				req.onreadystatechange = function () {
					if(req.readyState == 4 && req.status == 200) {
						var object = JSON.parse(req.responseText);
						console.log(object);
						if (object.newchange == 'true') {
							document.getElementById('alert').style.display = 'block';
							document.getElementById('alert-img').src = object.image;
							document.getElementById('alert-title').innerHTML = 'NEW ITEM: <a href="ItemPage.jsp?id=' + object.id + '">' + object.title + '</a>';
							document.getElementById('alert-sender').innerHTML = 'From: <a href="ProfilePage.jsp?user=' + object.sender + '&userType=lender">' + object.sender + '</a>';
							document.getElementById('alert-description').innerHTML = object.description;
							n++;
							itemcount++;
							return n;
						}
					}
				}
				req.send(null);
				return n;
			};
			
			function closeAlert() {
				document.getElementById('alert').style.display = 'none';
			}
		</script>
		<%} %>
		<%ArrayList results = new ArrayList<>();
		String criteria = request.getParameter("criteria");
		String search = request.getParameter("search");
		String location = request.getParameter("location");
		String json = "";
		if (location != "" && location != null) 
			json = jdb.getLocationJson(location);
		String lat = jdb.setlat(json);
		String lng = jdb.setlng(json);
		System.out.println(criteria);
		if (criteria.equals("Item")) {
			if(!search.isEmpty() && !location.isEmpty()) {
				results = jdb.getItemsBySearchAndLocation(search, Double.parseDouble(lat), Double.parseDouble(lng));
			} else {
				if (!search.isEmpty()) {
					results = jdb.getItemsForSearch(search);	
				} else if (!location.isEmpty()) {
					results = jdb.getItemsNearLocation(Double.parseDouble(lat), Double.parseDouble(lng));
				}
			}
			for (int i=0; i<results.size(); i++) {
				if (((ArrayList<Item>)results).get(i).getRenter() != null) 
					results.remove(i);
			}
		} else if (criteria.equals("User")) {
			results = jdb.searchUsername(search);
		}%>
		<div id="resultsTable" style="width: 50%; height: 550px;float:left; overflow-y: auto;">
			<%if (results.size() == 0) {%> <div style="position: relative; top: 10%; left: 45%; width: 300px;">No search results.</div> 
			<%} else if (criteria.equals("Item")) {%>
				<table style="width: 100%; border-collapse: collapse">
				<thead><td style="border: 1px solid black;">Showing <strong><%=results.size() %></strong> result(s) for <strong><%=search %></strong>
				<%if (!location.isEmpty()) { %>in <strong><%= location %></strong><%}%></td></thead>
				<%for(int i=0; i<results.size(); i++) {
					Item result = (Item) results.get(i);%>
					<tr>
						<td id="<%=i %>" class="result" style="border: 1px solid black; padding: 10px; overflow: ellipsis;">
							<img src="<%=result.getImage()%>" style="height: 100px; float: left;">
							<div style="float: left; margin-left: 10px;">
								<strong><a href="ItemPage.jsp?id=<%=result.getID()%>"><%=result.getTitle() %></a></strong></br></br>
								Lender: <a href="ProfilePage.jsp?user=<%=result.getLender() %>&userType=lender">
								<%=result.getLender() %></a></br>
								Price: $<%=result.getPrice() %></br>
								Rent from: <%=result.getStartDate()%> to <%=result.getEndDate()%></br>
								Description: <%=result.getDescription()%>
							</div>
						</td>
					</tr>
			<%	} %>
				</table>
			 <%} else if (criteria.equals("User")) { %>
			  		<table style="width: 100%; border-collapse: collapse">
			  		<thead><td style="border: 1px solid black;">Showing <strong><%=results.size() %></strong> result(s) for <strong><%=search %></strong></td></thead>
					<%for(int i=0; i<results.size(); i++) {
						User result = (User) results.get(i);%>
						
						<tr>
							<td style="border: 1px solid black; padding: 10px; overflow: ellipsis;">
								<img src="<%=result.getImage()%>" style="height: 100px; float: left; border-radius: 50%">
								<div style="float: left; margin-left: 10px;">
									<strong><a href="ProfilePage.jsp?user=<%=result.getUsername() %>&userType=<%=result.getUserType()%>"><%=result.getUsername() %></a></strong></br></br>
									
									<%=result.getUserType() %></br>
									<%=result.getEmailAddress()%>
								</div>
							</td>
						</tr>
			<%	}%>  
			</table>
			<%}%>
		</div>
		<div id="map" style="width:50%;height:550px;float:right;"></div>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
		<script>
			
			
			function myMap() {
			  var newcenter = new google.maps.LatLng(34.05349, -118.2453);
			  var myCenter = new google.maps.LatLng(51.508742,-0.120850);
			  
			  var mapCanvas = document.getElementById("map");
			  var mapOptions = {center: newcenter, zoom: 5};
			  var map = new google.maps.Map(mapCanvas, mapOptions);
			  var marker = new google.maps.Marker({position:myCenter});
			  <%if (criteria.equals("Item")) {
			  		for (int i=0; i<results.size(); i++) {
			  			Item result = (Item)results.get(i);%>
			  var marker<%=i%> = new google.maps.Marker({position:{lat:<%=result.getX()%>, lng:<%=result.getY()%>}});
			  marker<%=i%>.addListener('click', function() {
				  $(".result").css("background-color", "white");
				  document.getElementById('<%=i%>').style.backgroundColor = "goldenrod";
				  $('#resultsTable').animate({
			          scrollTop:  <%=i%>*100
			     });
		      });
			  <%}
			  }%>
			  
			  <%if (criteria.equals("Item")) {
			  		for (int i=0; i<results.size(); i++) {
			  			Item result = (Item)results.get(i);%>
			  marker<%=i%>.setMap(map);	  
			  <%}
			  }%>
			}
		</script>
		<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDp3SIzf3bZ3WAW-Pf5Qmdxs20pCfVIz-U&callback=myMap"></script>
		
	</body>
</html>