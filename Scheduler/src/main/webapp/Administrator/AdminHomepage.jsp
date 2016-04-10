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
	<%@ page import="java.io.*" %>
	
	
	<% HTMLServices hs = new HTMLServices(session, request, response, out); 
		excelServices es = new excelServices(session, request, response, out);
		adminServices as = new adminServices(session, request, response, out);
		as.logout();
		as.selectSemester();
		es.exportData();
	%>
</head>
<body>

<!-- Begin Validation -->
	<%System.out.print("Checking Login Status\n");
	//Always going to redirct unless current session key equals the adminKey
	//Even if this is set to the userKey the page will NOT be displayed
	if(as.invalidAdmin() ){
		System.out.print(" Invalid User\n");
		as.redirect("../User/LandingPage.jsp");
	}%>
	
	<%System.out.print("Ensuring a Semester was selected\n");
	//Always going to redirct unless current session key equals the adminKey
	//Even if this is set to the userKey the page will NOT be displayed
	if(as.validateSemester()){
		System.out.print(" Semester Choosen\n");
	} %>
	<%@ include file="/WEB-INF/AdminMenu.jspf" %>
	
	
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h3 class="text-center">
				Administrator Homepage
			</h3>
		</div>
	</div>
</div>
</br></br></br>

<div class="col-md-12">
			<div class="row">
			
			<form role="form" action='AdminHomepage.jsp' method='post'>
			<input type="hidden" name="semester" value="spring">
				<div class="col-md-4">
					<button type="submit" class="btn btn-default center-block" value="spring">
						Spring
					</button>
				</div>
				</form>
				
			<form role="form" action='AdminHomepage.jsp' method='post'>
			<input type="hidden" name="semester" value="fall">
				<div class="col-md-4">
					 
					<button type="submit" class="btn btn-default center-block" value="fall">
						Fall
					</button>
				</div>
			</form>
			
			<form role="form" action='AdminHomepage.jsp' method='post'>
			<input type="hidden" name="semester" value="summer">
				<div class="col-md-4">
					 
					<button type="submit" class="btn btn-default center-block" value="summer">
						Summer
					</button>
			</div>
			</form>
			
	</div>
</div><br><br><br>

<div>
<form role="form" action='AdminHomepage.jsp' method='post'>
			<input type="hidden" name="export" value="export">
				<div class="col-md-4">
					<button type="submit" class="btn btn-default center-block" value="export">
						Download Data
					</button>
				</div>
				</form>
</div>


</body>
</html>
