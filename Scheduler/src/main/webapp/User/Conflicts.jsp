<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.scheduler.services.*" %>
<%@ page import="com.scheduler.valueObjects.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
	String semester = session.getAttribute("semester").toString();
	ServletContext context = session.getServletContext();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>  
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  
  	<link rel="stylesheet" type="text/css" href="<%= response.encodeURL(context.getContextPath()+"/DataTables/datatables.min.css") %>"/>
  	<script type="text/javascript" src="<%= response.encodeURL(context.getContextPath()+"/DataTables/datatables.min.js") %>"></script>
  
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Conflicts</title>
</head>
<body>
<%@ include file="/WEB-INF/AdminMenu.jspf" %>	
<div class="container-fluid">
<%
	if(semester.equals("summer")){%>
<h2 class="text-center">Current Summer Conflicts</h2>

<%} else if (semester.equals("spring")){%>
<h2 class="text-center">Current Spring Conflicts</h2>

<%} else {%>
<h2 class="text-center">Current Fall Conflicts</h2>
<%} %>
<br/><br/><br/><br/>
<div class="col-md-6 col-sm-offset-3">
<table id ="conflictTable" class="table table-striped table-bordered">
<thead>
<tr>
	<th>Class1</th>
	<th>Class2</th>
	<th>Conflict Type</th>
</tr>
</thead>
<tbody>
<% 
	List<Conflict> conflicts = conflictServices.getConflicts(session.getAttribute("semester").toString()); 
	Map<String,String> ournames = dbServices.getOurNames(semester);
	if (conflicts != null){
	for (Conflict c : conflicts){ %>
<tr>
	<td><a href ="<%= response.encodeURL(context.getContextPath()+"/Classes/Edit?classID=" + c.getClass1()) %>"><%= dbServices.getClasses(semester, c.getClass1()).get(0).get(ournames.get("classname")) %></a></td>
	<td><% if(c.getClass2() > 0){ %><a href="<%= response.encodeURL(context.getContextPath()+"/Classes/Edit?classID=" + c.getClass2()) %>"><%= dbServices.getClasses(semester, c.getClass2()).get(0).get(ournames.get("classname")) %></a><% } %></td>
	<td><%= c.getConfType() %></td>
</tr>
<% }} %>

</tbody>
</table>
</div>
</div>

<script>
$(document).ready( function () {
    $('#conflictTable').DataTable({
        "lengthMenu": [[10, 25, -1], [10, 25, "All"]]
    });
});
</script>
</body>
</html>