<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

	<%@ page import="com.scheduler.services.*" %>
	<%@ page import="com.scheduler.jsp.*" %>

	<% HTMLServices hs = new HTMLServices(session, request, response, out); 
		adminServices as = new adminServices(session, request, response, out);
	%>




<title>Insert title here</title>
</head>
<body>

<!--  We do not want to restrict who can access this page -->

<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h3 class="text-center">
				Login Error
			</h3>
			<p class="text-center">
				The credentials you entered are invalid. Please return to the login page and try again.   <a href="LandingPage.jsp"> Click here </a>
			</p>

		</div>
	</div>
</div>


</body>
</html>