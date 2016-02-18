<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
	as.directLogin();
	as.logout();
    %>
  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<!--  We do not want to restrict who can access this page -->

<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h3 class="text-center">
				UNO's Classroom Scheduler - Login
			</h3>
			</br></br></br>
			
			
			<%//Login Form %>
			<form role="form" action='LandingPage.jsp' method='post'>
			<input type="hidden" name="userLogin" value="userLogin">
				<div class="col-xs-3">				 
					<label for="InputUsername">
						Username
					</label>
					<input class="form-control" name="userName" id="InputUsername" />
				</div></br></br></br></br>
				<div class="col-xs-3">
					 
					<label for="InputPass">
						Password
					</label>
					<input type="password" class="form-control" name="userPassword" id="InputPass" />
				</div></br></br></br></br></br>
				
				<div class="row-md-5">				
				<button type="submit" class="btn btn-default">
					</t>Submit		
				</button>
				</div>				
			</form>
		</div>
	</div>
</div>
</br></br></br>
<!--   <a href="RequestAccount.jsp">    Click here to request a new account</a> -->

</body>
</html>