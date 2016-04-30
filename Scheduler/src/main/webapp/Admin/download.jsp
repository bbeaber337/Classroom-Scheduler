<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.scheduler.services.*" %>
<%@ page import="com.scheduler.valueObjects.*" %>
<%
	ServletContext context = session.getServletContext();
	String semester = session.getAttribute("semester").toString();
	int userlevel = Integer.parseInt(session.getAttribute("userlevel").toString());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  
  <!-- Pulling Bootstrap from Content Delivery Network / Need to download and host myself -->
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<title>Download</title>

	
</head>
<body>
<%@ include file="/WEB-INF/AdminMenu.jspf" %>
	
	
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h3 class="text-center">
				Download Document
			</h3>
		</div>
	</div>
</div>
<br/><br/><br/>

<div>
<div>
<form role="form" action='<%= response.encodeURL(context.getContextPath()+"/Admin/Download/Excel") %>' method='post'>
			<input type="hidden" name="export" value="export">
				<div class="col-md-4">
					<button type="submit" class="btn btn-default center-block" value="export">
						Excel Download
					</button>
				</div>
				</form>
</div>
<div>
<form role="form" action='<%= response.encodeURL(context.getContextPath()+"/Admin/Download/Word") %>' method='post'>
			<input type="hidden" name="exportWord" value="exportWord">
				<div class="col-md-4">		
					<button type="submit" class="btn btn-default center-block" value="exportWord">
						Word Download
					</button>
					<% for (String subject : dbServices.getSubjects(semester)){ %>
					<div class="row col-md-4 col-md-offset-2">
						<input type="checkbox" name="subjects" value="<%= subject %>" >
						<label for="checkbox">
							<%= subject %>
						</label>
					</div>
					<% } %>
				</div>
				</form>
</div>
</div>


</body>
</html>