<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.scheduler.valueObjects.*" %>
<% 
	if (Semester.SEMESTERS.contains(request.getParameter("semester"))){
		session.setAttribute("semester", request.getParameter("semester"));
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html>
<head>  
  <%-- Pulling Bootstrap from Content Delivery Network / Need to download and host myself --%>
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Home</title>
</head>
<body>
	<%@ include file="/WEB-INF/AdminMenu.jspf" %>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<h3 class="text-center">
					Choose a Semester
				</h3>
			</div>
		</div>
	</div>
<br/><br/><br/>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="row">
			
			<div class="col-md-4 col-sm-6">
			<form role="form" action='<%= response.encodeURL(menucontext.getContextPath()+"/Home") %>'>
			<input type="hidden" name="semester" value="spring">
				
					<button type="submit" class="btn btn-default center-block" value="spring">
						Spring
					</button>
				
				</form>
				</div>
				
			<div class="col-md-4 col-sm-6">
			<form role="form" action='<%= response.encodeURL(menucontext.getContextPath()+"/Home") %>'>
			<input type="hidden" name="semester" value="fall">
					 
					<button type="submit" class="btn btn-default center-block" value="fall">
						Fall
					</button>
			</form>
			</div>
			
			<div class="col-md-4 col-sm-6">
			<form role="form" action='<%= response.encodeURL(menucontext.getContextPath()+"/Home") %>'>
			<input type="hidden" name="semester" value="summer">
					 
					<button type="submit" class="btn btn-default center-block" value="summer">
						Summer
					</button>
			</form>
			</div>
			</div>
		</div>
	</div>
</div>
</div><br><br><br>




</body>
</html>
