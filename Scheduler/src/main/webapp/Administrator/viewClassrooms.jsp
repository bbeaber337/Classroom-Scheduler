<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
		adminServices as = new adminServices(session, request, response, out);
		boolean build = as.editClassroom();
	%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%System.out.print("Checking Login Status\n");
	//Always going to redirct unless current session key equals the adminKey
	//Even if this is set to the userKey the page will NOT be displayed
	if(as.invalidAdmin() ){
		System.out.print(" Invalid User\n");
		as.redirect("../User/LandingPage.jsp");
	}%>


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
                <a class="navbar-brand" style="float:none" href="AdminHomepage.jsp">Administrator Homepage</a>
            </div>
            
            <div id="navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">                                       

                    <li><a href="viewRequests.jsp">Create Account</a></li>
                    <li><a href="Upload.jsp">Upload Schedule</a></li>
             
                 <li>   
			<div class="btn-group">
                    <button class="btn btn-group">
					View
				</button> 
				<button data-toggle="dropdown" class="btn btn-default dropdown-toggle">
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li>
						<a href="viewClasses.jsp">Classes</a>
					</li>
					<li class="divider">
					</li>
					<li>
						<a href="viewClassrooms.jsp">Classrooms</a>
					</li>
					<li class="divider">
					</li>
					<li>
						<a href="viewUsers.jsp">Users</a>
					</li>
				</ul>
				</div>
				</li>
                    
                    <li><a href="../User/LandingPage.jsp?logout=true">Logout</a></li>
                </ul>
            </div>
            
        </div>
    </nav>
    </br></br></br></br>
	<!--  End Header -->
	

<!-- Check if a change was submitted -->
<% as.submitClassroomEdit();
   as.deleteClassroom();
%>
		
<%//} else {%>
<h2 class="text-center">Classrooms</h2>
</br></br></br></br>
<%hs.buildClassrooms(); 
//}%>


</body>
</html>