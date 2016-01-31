<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  
  <!-- Pulling Bootstrap from Content Delivery Network / Need to download and host myself -->
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<title>Insert title here</title>

	<%@ page import="com.scheduler.services.*" %>
	<%@ page import="com.scheduler.valueObjects.*" %>
	<%@ page import="com.scheduler.jsp.*" %>
	<%@ page import="java.util.*" %>
	<%@ page import="java.sql.*" %>
	
	
	<% HTMLServices hs = new HTMLServices(session, request, response, out); 
	adminServices as = new adminServices(session, request, response, out);%>
</head>
<body>
	
	<%System.out.print("Checking Login Status\n");
	//If this Admin tries to access user page we will allow it, otherwise check userKey status
	if(as.invalidAdmin()){
		//Always going to redirct unless current session key equals the userKey
		if(as.invalidUser() ){
			as.redirect("LandingPage.jsp");
		}
	}   %>

	
	<!--  Start Header -->
	<nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" style="float:none" href="UserHomepage.jsp">Homepage</a>
            </div>
            
            <div id="navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">                                       

                    <li><a href="LandingPage.jsp">Classes</a></li>
                    <li><a href="LandingPage.jsp">Classrooms</a></li>
                    <li><a href="../User/LandingPage.jsp?logout=true">Logout</a></li>
                </ul>
            </div>
            
        </div>
    </nav>
    </br></br></br></br>
	<!--  End Header -->
	
	
	
	
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h3 class="text-center">
				Welcome to UNO's Classroom Scheduler
			</h3>
		</div>
	</div>
</div>
</br></br></br>



</body>
</html>