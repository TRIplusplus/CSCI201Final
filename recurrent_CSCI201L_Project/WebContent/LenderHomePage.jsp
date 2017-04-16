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
		<script type="text/javascript">
			function displayPopup() {
				document.getElementById('popup').style.display = 'block';
			}
			
			function closePopup() {
				document.getElementById('popup').style.display = 'none';
			}
		</script>
		<%@include file="Navbar.jsp" %>
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
		<div id="lent-items" style="text-align: center">
			<h3>Currentlty Lending Items:</h3>
			<div id="lent-items-table">
				You are not renting any items.<br>
				Click the button above to lend an item.
			</div>
		</div>
	</body>
</html>