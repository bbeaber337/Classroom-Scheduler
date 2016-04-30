<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.scheduler.services.*" %>
<%@ page import="com.scheduler.valueObjects.*" %>
<% 
	ServletContext context = session.getServletContext();
	boolean loginAttempt = false;
	if (request.getParameter("logout") != null){
		session.invalidate();
	} else if (session.getAttribute("loggedin") != null && session.getAttribute("loggedin").toString().equalsIgnoreCase("true")){
		response.sendRedirect(response.encodeURL(context.getContextPath()+"/Home"));
	} else if(request.getParameter("userLogin") != null){
		loginAttempt = true;
		User user = adminServices.validLogin(request.getParameter("userName"), request.getParameter("userPassword"));
		if (user.getUserLevel() > 0){ 
			session.setAttribute("username", user.getUserName());
			session.setAttribute("userlevel", user.getUserLevel());
			session.setAttribute("loggedin", "true");
			response.sendRedirect(response.encodeURL(context.getContextPath()+"/Home"));
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>  
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login</title>
</head>
<body>
<div class="container-fluid">
	<h3 class="text-center">UNO's Classroom Scheduler - Login</h3>
	<br/><br/><br/>
	<form class="form-horizontal col-md-4 col-md-offset-4" action='Login' method='post'>
	<input type="hidden" name="userLogin" value="userLogin">
		<div class="form-group">				 
			<label for="InputUsername" class="control-label">Username</label>
			<input class="form-control" name="userName" id="InputUsername" autofocus required>
		</div>
		<div class="form-group">
			<label for="InputPass" class="control-label">Password</label>
			<input type="password" class="form-control" name="userPassword" id="InputPass" required>
		</div>
		<div class="block-center">
		<button type="submit" class="btn btn-default">Submit</button>
		</div>
	<% if (loginAttempt){ %>
		<p style="color:red"><i>Incorrect username or password</i></p>
	<% } %>
	</form>
</div>
</body>
</html>