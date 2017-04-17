<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Lender Login</title>
	<link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/HomePage.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  </head>
  <body>
    <div class="wrapper">
    	</br>
    	</br>
    	<h3>Team 14 CSCI201</h3>
    	</br></br>
		<h1>RecuRRent</h1>
		<image style="float:right" title = "View Feed" src="Logo.png"></image>
		<h4 style="margin-left: 285px;">Renter Login</h4>
		<div id = 'buttons'>
			<form name="inputForm" method="GET" onsubmit="return sendErrorMessage()">
				Username</br>
				<input type ="text" name="username"/></br>
				Password</br>
				<input type ="text" name="password"/></br></br>
				<input type ="submit" name="login" value="LOG IN" /></br></br>
			</form>
			</br><div id ="error"></div>
		</div>
    </div>
  </body>
      <script>
		function sendErrorMessage() {

			var username = ""; 
			var username = document.inputForm.username.value;
			
			var password = "";
 			var password = document.inputForm.password.value;
	
			
			var errorMessage = ""; 

			if (username==""|| password ==""){
				errorMessage = "There is a missing field. Please input information into all required fields"; 
			} 
  			else {
 				$.ajax({
 	 				type: 'POST', 
 	 				url: "${pageContext.request.contextPath}/RenterLoginServlet", 
 	 				xhrField: {
 	 					withCredential: true
 	 				},
 	 				data: {
 	 					'username': username, 'password': password
 	 				}, 
 	 				success: function(msg){
 	 					if (msg.match("There is a missing field. Please input information into all required fields") || msg.match("Username does not exist") || msg.match("Username and password do not match")){
 	 	 					$('#error').text(msg);
 	 					} else {
 	 						var successUrl = "RenterHomePage.jsp";
 	 					    window.location.href = successUrl;
 	 					}
 	 				},
 				}); 
 			}
			
			document.getElementById("error").innerHTML = errorMessage;
 			return false;  
		}
	</script>
</html>
