<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

	<%@ page import="com.scheduler.services.*" %>
	<%@ page import="com.scheduler.valueObjects.*" %>
	<%@ page import="com.scheduler.jsp.*" %>
	<%@ page import="java.util.*" %>
	<%@ page import="java.sql.*" %>


  <!-- Pulling Bootstrap from Content Delivery Network / Need to download and host myself -->
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  
   	<% HTMLServices hs = new HTMLServices(session, request, response, out);
   	adminServices as = new adminServices(session, request, response, out);
   	as.addAccRequest();
    %>

<title>Request Account</title>
</head>
<body>

<!--  We do not want to restrict who can access this page -->

<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h1 class="text-center">
				Account Request
			</h1></br></br></br></br>
			<p >
				<h4 class="text-center">In order to ensure that only qualified individuals can access UNO's classroom scheduler all requests for an accounts must first be approved.</h4>
			</p></br></br></br></br>
			
			
			<form role="form" action='RequestAccount.jsp' method='post'>
			<input type="hidden" name="accountRequest" value="accountRequest">	
				<div class="col-xs-3">	 
					<label for="firstName">
						First Name
					</label>
					<input type="text" class="form-control" name="userFirst" id="firstName" size="50" />
				</div></br></br></br></br>
				
				<div class="col-xs-3">	 
					<label for="lastName">
						Last Name
					</label>
					<input type="text" class="form-control" name="userLast" id="lastName" size="50" />
				</div></br></br></br></br>
				
				<div class="col-xs-3"> 
					<label for="email">
						Email
					</label>
					<input type="email" class="form-control" name="userEmail" id="email" />
				</div></br></br></br></br>
				
				<div class="col-xs-3">
					<label for="desiredUsername">
						Desired Username
					</label>
					<input type="text" class="form-control" name="userName" id="desiredUsername" />
				</div></br></br></br></br>
				
				<div class="col-xs-3">
					<label for="pass">
						Password
					</label>
					<input type="password" name="userPassword" class="form-control" id="pass" />
				</div></br></br></br></br>
				
				<div class="row-xs-3">
					<label for="reasoning">
						Reason for requesting account
					</label>
					<input type="text" class="form-control" name="reasoning" id="reasoning"/>
				</div></br>
				
				<button type="submit" class="btn btn-default">
					Submit
				</button>
			</form>
			</br></br></br>
			<a href="LandingPage.jsp"> Back to Login Page </a>
			
		</div>
	</div>
</div>
</body>
</html>