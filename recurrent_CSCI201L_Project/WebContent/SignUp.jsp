<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="recurrent_CSCI201L_Project.StringConstants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Lender Login</title>
	<link href="https://fonts.googleapis.com/css?family=PT+Sans" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/HomePage.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
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
		<h4 style="margin-left: 295px;">Sign Up</h4>
		<div id = 'buttons'>
			<form name="inputForm" method="GET" onsubmit="return sendErrorMessage()">
				Username</br>
				<input type ="text" name="username"/></br>
				Password</br>
				<input type ="password" name="password"/></br>
				Email</br>
				<input type ="text" name="email"/></br>
				<input type="radio" id='renter' name='usertype'/ value='renter'>Renter</input>
				<input type="radio" id='lender' name='usertype' value='lender'/>Lender</input></br></br>
				<input type ="submit" name="newUser" value="Sign Up" />
			</form>
			</br><div id ="error"></div>
		</div>
    </div>
    <script>
		function sendErrorMessage() {

			var userType = "";
			userType = $('input:radio[name=usertype]:checked').val();
			
			var username = ""; 
			var username = document.inputForm.username.value;
			
			var password = "";
 			var password = document.inputForm.password.value;
 			
 			var email = "";
			var email = document.inputForm.email.value;
			
			
			var errorMessage = ""; 

			if (username==""|| password =="" || email==""){
				errorMessage = "There is a missing field. Please input information into all required fields"; 
			} 
  			else {
 				$.ajax({
 	 				type: 'POST', 
 	 				url: "${pageContext.request.contextPath}/SignUpServlet", 
 	 				xhrField: {
 	 					withCredential: true
 	 				},
 	 				data: {
 	 					'username': username, 'password': password, 'email': email, 'userType': userType
 	 				}, 
 	 				success: function(msg){
 	 					if (msg.match("There is a missing field. Please input information into all required fields") || msg.match("Invalid email address") || msg.match("Username has already been chosen. Please choose another")){
 	 	 					$('#error').text(msg);
 	 	 					console.log(msg)
 	 					} else {
 	 						var successUrl = "LenderLogin.jsp";
 	 					    window.location.href = successUrl;
 	 					}
 	 				},
 				}); 
 			}
			
			document.getElementById("error").innerHTML = errorMessage;
 			return false;  
		}
	</script>
  </body>
</html>
