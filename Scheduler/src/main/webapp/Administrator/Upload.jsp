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
	<%@ page import="java.io.*,java.util.*" %>
	
	
	<% 
		HTMLServices hs = new HTMLServices(session, request, response, out); 
		adminServices as = new adminServices(session, request, response, out);
		excelServices es = new excelServices(session, request, response, out);
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
	if(!as.validateSemester()){
		System.out.print(" Need to select a semester \n");
		as.redirect("AdminHomepage.jsp");
	}
	es.addData();
	%>
<!-- End Validation -->

	
	<!--  Start Header -->
	<%@ include file="AdminMenu.jspf" %>
	<!--  End Header -->
	
	
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h3 class="text-center">
				Upload an Excel Document
			</h3>
		</div>
	</div>
</div>
</br></br></br>

<!--  File Uploader -->

<!-- Need to ensure the form  includes " enctype="multipart/form-data" " for the file to be uploaded correctly (see below Example) -->
<!-- <form role="form" action="Upload.jsp" method="post" enctype="multipart/form-data"> -->

<form role="form" action="Upload.jsp" method="post" enctype="multipart/form-data">
<input type="hidden" name="fileUpload" value="fileUpload">
	<div class="form-group">	 
		<label for="file">
			File input
		</label>
		<input type="file" name="file" required/>
	</div>

	<div class="row-md-5">				
	<button type="submit" class="btn btn-default" onclick="return confirm('Are you sure you want to Upload this file?  This will replace all Existing classes and classrooms.')">
		</t>Submit		
	</button>
	</div>				
</form>



</body>
</html>