<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.scheduler.services.*" %>
<%@ page import="com.scheduler.valueObjects.*" %>
<%@ page import="com.scheduler.jsp.*" %>
<%@ page import="java.io.*,java.util.*" %>
<% 
	HTMLServices hs = new HTMLServices(session, request, response, out); 
	adminServices as = new adminServices(session, request, response, out);
	excelServices es = new excelServices(session, request, response, out);
	wordServices ws = new wordServices(session, request, response, out);
%>
<%-- Begin Validation --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  
  <!-- Pulling Bootstrap from Content Delivery Network / Need to download and host myself -->
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<title>Insert title here</title>

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
	if (request.getParameter("export") != null){
		es.exportData();
	} else if (request.getParameter("exportWord") != null){
		ws.exportData();
	} else{
	%>
<%-- End Validation --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  
  <!-- Pulling Bootstrap from Content Delivery Network / Need to download and host myself -->
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<title>Insert title here</title>

	
</head>
<body>



	
	<%--  Start Header --%>
	<%@ include file="/WEB-INF/AdminMenu.jspf" %>
	<%--  End Header --%>
	
	
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h3 class="text-center">
				Download Document
			</h3>
		</div>
	</div>
</div>
</br></br></br>

<div>
<div>
<form role="form" action='Download.jsp' method='post'>
			<input type="hidden" name="export" value="export">
				<div class="col-md-4">
					<button type="submit" class="btn btn-default center-block" value="export">
						Excel Download
					</button>
				</div>
				</form>
</div>
<div>



       



<form role="form" action='Download.jsp' method='post'>
			<input type="hidden" name="exportWord" value="exportWord">
				<div class="col-md-4">		
					<button type="submit" class="btn btn-default center-block" value="exportWord">
						Word Download
					</button>
					<!-- 
					<div class="checkbox">
 					 <label><input type="checkbox" value="Val1">Department 1</label>
 					 <label><input type="checkbox" value="Val2">Department 2</label>
  					<label><input type="checkbox" value="Val3">Department 3</label>
					</div>  -->
				</div>
				</form>
</div>
</div>


</body>
</html>
<% } %>