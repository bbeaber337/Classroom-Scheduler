<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.scheduler.services.*" %>
<%@ page import="com.scheduler.valueObjects.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<% 
	String semester = session.getAttribute("semester").toString();
	int userlevel = Integer.parseInt(session.getAttribute("userlevel").toString());
	ServletContext context = session.getServletContext();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  
  <!-- Pulling Bootstrap from Content Delivery Network / Need to download and host myself -->
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<title>Upload</title>


</head>
<body>	
<%@ include file="/WEB-INF/AdminMenu.jspf" %>
<div id="formdiv" class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<h3 class="text-center">
				Confirm Header Fields
			</h3>
		</div>
	</div>
<br/><br/><br/>

<%
	Map<String, String> ournames = dbServices.getOurNames(semester);
	List<String> headers = dbServices.getHeaders(semester);
	Classlist classlist = dbServices.getClasses(semester, 1);
	if (!classlist.isEmpty()){
%>
<div class="col-md-6">
<form class="form-horizontal" id="data" action="<%= response.encodeURL(context.getContextPath()+"/Admin/Upload/Confirm") %>" method="post">
<input type="hidden" name="fileUpload" value="fileUpload">
<%
	for (String h : ournames.keySet()) {
%>
	<div class="form-group">
		<label for="<%= h %>" class="col-sm-4 control-label"><%= h %></label>
		<div class="col-md-8">
			<select class="form-control" name="<%= h %>" id="<%= h %>">
				<% for (String s : headers){ %>
				<option value="<%= s %>" <% if(s.equalsIgnoreCase(ournames.get(h))){%>selected<%}%>><%= s %></option>
				<% } %>
			</select>
		</div>
	</div>
<% } %>
	<div class="form-group">
		<div class="col-sm-offset-4">
			<input type="checkbox" name="keep" value="data" checked>
			<label for="checkbox">
				Keep previous Teacher and Classroom Data
			</label>
		</div>
	</div>
	<div class="col-sm-offset-4">				
	<button type="submit" class="btn btn-default" >
		</t>Submit
	</button>
	</div>				
</form>
</div>
<div class="container-fluid">
<h3 class="text-center">Headers for First Class from Upload</h3>
<dl>
<% 	Class1 class1 = classlist.get(0);
	for(String h : headers){ %>
		<dt class="col-md-2 text-right"><%= h %></dt>
		<dd class="col-md-4">:<%= class1.get(h) %></dd>
<% } %>
</dl>
<% } %>
</div>
</div>
<div id="loadingDiv" style="display:none;"></div>
<script>
$(document).ready(//Program a custom submit function for the form
	$("form#data").submit(function(event){
			 
		//disable the default form submission
		event.preventDefault();
		
		var postData = $(this).serializeArray();
	    var formURL = $(this).attr("action");
	    $.ajax(
	    {
	        url : formURL,
	        type: "POST",
	        data : postData,
	        success:function(data, textStatus, jqXHR) 
	        {
			    $("#loadingDiv").append("Done");
			    window.location.href("<%= response.encodeURL(menucontext.getContextPath()+"/Admin/Upload/") %>");
	        }
	    });
		$("#formdiv").hide();
		$("#loadingDiv").text("Loading...");
		$("#loadingDiv").show();
		 
		return false;
	})
);
</script>
</body>
</html>