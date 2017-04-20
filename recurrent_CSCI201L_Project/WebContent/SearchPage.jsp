<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/HomePage.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
</head>
<body>
	<%@include file="Navbar.jsp"%>
	<%@ page import="java.util.ArrayList"%>
	<%@ page import="recurrent_CSCI201L_Project.Item"%>
	<%@include file="Alert.jsp"%>

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
	<%
		ArrayList results = new ArrayList<>();
		String criteria = request.getParameter("criteria");
		String search = request.getParameter("search");
		String location = request.getParameter("location_zip");
		if (location != "" && location != null) {
			String location_json = jdb.json_Geo(location);
			System.out.println(location_json);
		}
		//get search area zip code
		//get zipcode coordinate
		String search_geo_json = jdb.getLocationJson(location);
		String search_lat = jdb.setlat(search_geo_json);
		String search_lng = jdb.setlng(search_geo_json);
		double x = Double.parseDouble(search_lat);
		double y = Double.parseDouble(search_lng);
		System.out.println(criteria);
		if (criteria.equals("Item")) {
			if (!search.isEmpty() && !location.isEmpty()) {
				results = jdb.getItemsBySearchAndLocation(search, x, y);
			} else {
				if (!search.isEmpty()) {
					results = jdb.getItemsForSearch(search);
				} else if (!location.isEmpty()) {
					results = jdb.getItemsNearLocation(x, y);
				}
			}
			for (int i = 0; i < results.size(); i++) {
				if (((ArrayList<Item>) results).get(i).getRenter() != null)
					results.remove(i);
			}
		} else if (criteria.equals("User")) {
			results = jdb.searchUsername(search);
		}
	%>
	<div id="resultsTable"
		style="width: 50%; height: 550px; float: left; overflow-y: auto;">
		<%
			if (results.size() == 0) {
		%>
		<div style="position: relative; top: 10%; left: 45%; width: 300px;">No
			search results.</div>
		<%
			} else if (criteria.equals("Item")) {
		%>
		<table style="width: 100%; border-collapse: collapse">
			<thead>
				<td style="border: 1px solid black;">Showing <strong><%=results.size()%></strong>
					result(s) for <strong><%=search%></strong> <%
 	if (!location.isEmpty()) {
 %>in
					<strong><%=location%></strong>
					<%
						}
					%></td>
			</thead>
			<%
				for (int i = 0; i < results.size(); i++) {
						Item result = (Item) results.get(i);
			%>
			<tr>
				<td
					style="border: 1px solid black; padding: 10px; overflow: ellipsis;">
					<img src="<%=result.getImage()%>"
					style="height: 100px; float: left; border-radius: 50%">
					<div style="float: left; margin-left: 10px;">
						<strong><a href="ItemPage.jsp?id=<%=result.getID()%>"><%=result.getTitle()%></a></strong></br>
						</br> Lender: <a
							href="ProfilePage.jsp?user=<%=result.getLender()%>&userType=lender">
							<%=result.getLender()%></a></br> Price: $<%=result.getPrice()%></br> Rent
						from:
						<%=result.getStartDate()%>
						to
						<%=result.getEndDate()%></br> Description:
						<%=result.getDescription()%>
					</div>
				</td>
			</tr>
			<%
				}
			%>
		</table>
		<%
			} else if (criteria.equals("User")) {
		%>
		<table style="width: 100%; border-collapse: collapse">
			<thead>
				<td style="border: 1px solid black;">Showing <strong><%=results.size()%></strong>
					result(s) for <strong><%=search%></strong></td>
			</thead>
			<%
				for (int i = 0; i < results.size(); i++) {
						User result = (User) results.get(i);
			%>

			<tr>
				<td
					style="border: 1px solid black; padding: 10px; overflow: ellipsis;">
					<img src="<%=result.getImage()%>"
					style="height: 100px; float: left;">
					<div style="float: left; margin-left: 10px;">
						<strong><a
							href="ProfilePage.jsp?user=<%=result.getUsername()%>&userType=<%=result.getUserType()%>"><%=result.getUsername()%></a></strong></br>
						</br>

						<%=result.getUserType()%></br>
						<%=result.getEmailAddress()%>
					</div>
				</td>
			</tr>
			<%
				}
			%>
		</table>
		<%
			}
		%>
	</div>
	<div id="map" style="width: 50%; height: 700px; float: right;"></div>
	<%
		String search_item_near_location_json = jdb.search_item_near_location_json_display(results);
	%>
	<script>
		 function initMap() {
			      	
			 		 var json_ = '<%=search_item_near_location_json%>
		';
			var j_son = JSON.parse(json_);
			
			var location = new google.maps.LatLng(j_son.locations[0].lat,
					j_son.locations[0].lng);
			var map = new google.maps.Map(document.getElementById('map'), {

				mapTypeId : 'terrain',
				zoom : 8,
				center : location
			});

			for (i = 0; i < j_son.locations.length; i++) {
				var latlng = new google.maps.LatLng(j_son.locations[i].lat,
						j_son.locations[i].lng);
				var marker = new google.maps.Marker({
					position : latlng,
					map : map
				});
				google.maps.event.addListener(marker, 'click', function() {
					map.setZoom(10);
					map.setCenter(marker.getPosition());
				});
			}

			// Zoom to 9 when clicking on marker
			google.maps.event.addListener(marker, 'click', function() {
				map.setZoom(10);
				map.setCenter(marker.getPosition());
			});
		}
	</script>
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDp3SIzf3bZ3WAW-Pf5Qmdxs20pCfVIz-U
&callback=initMap"></script>
</body>
</html>