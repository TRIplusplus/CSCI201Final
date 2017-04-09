<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>testing</title>
</head>

<body>
<div id="map" style="width:60%;height:500px"></div>

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
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDp3SIzf3bZ3WAW-Pf5Qmdxs20pCfVIz-U
&callback=myMap"></script>

</body>

</body>
</html>