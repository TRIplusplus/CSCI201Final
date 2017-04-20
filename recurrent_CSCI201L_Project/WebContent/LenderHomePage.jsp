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
		<%@include file="Alert.jsp" %>
		<script type="text/javascript">
			window.onload = function() {
				var count = <%=jdb.countUnreadMessages(username)%>;
				setInterval(function() {
					count = checkMail(count);
				}, 5000);
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
							document.getElementById('message-count').innerHTML = n;
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
		<script type="text/javascript">
			function displayPopup() {
				document.getElementById('popup').style.display = 'block';
			}
		
			function closePopup() {
				document.getElementById('popup').style.display = 'none';
			}
			
			
		</script>
		
		<div id="lendnew-container">
			<button id="lendnew-button" onclick="displayPopup()">+ Lend New Item</button>
		</div>
		<div id="popup">
			<span id="popup-close" onclick="closePopup()">X</span>
			<form name="lenderForm" action="LendNewItemServlet">
				<strong>Lend New Item</strong><br><br>
				Item Name</br>
				<input type="input" name="name"><br/><br/>
				Photo</br>
				<input type="input" name="image"><br/><br/>
				Start Date</br>
				<input type="date" name="start_date"><br/><br/>
				End Date</br>
				<input type="date" name="end_date"><br/><br/>
				Description</br>
				<input type="input" name="description"><br/><br/>
				Price</br>
				$<input type="input" name="price"><br/><br/>
				ZIP Code</br>
				<input type="input" name="zip"><br/><br/>
				<input type="submit" value="Submit">
			</form>
		</div>
		<div id="lent-items" style="text-align: center;">
			<h3>Currently Lending Items:</h3>
			<div id="lent-items-table" style="text-align: center;">
			<%@ page import="java.util.ArrayList" %>
			<%@ page import="recurrent_CSCI201L_Project.Item" %>
			<%ArrayList<Item> items = jdb.getItemsForLender(username);
			//System.out.println(items.size());
			if (items == null) {
			%>
				You are not renting any items.<br>
				Click the button above to lend an item.
			<%} else {%>
				<table style="border: 0px; width: 60%; margin-left: 20%">
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