<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>    
<%@ page import="com.scheduler.services.*" %>
<%@ page import="com.scheduler.valueObjects.*" %>
<%
	int userlevel = Integer.parseInt(session.getAttribute("userlevel").toString());
	ServletContext context = session.getServletContext();
	String pathinfo = request.getPathInfo();
	if (pathinfo == null){
		pathinfo = "";
	} else {
		pathinfo = pathinfo.substring(1);
	}
	Boolean updateFailed = false;
	if(userlevel == User.USER_ADMIN){
		if(pathinfo.equalsIgnoreCase("Delete")){
			dbServices.deleteUser(Integer.parseInt(request.getParameter("userID")));
		} else if (request.getParameter("Update") != null){
			User user = new User();
			user.setUserName(request.getParameter("userName"));
			user.setUserFirst(request.getParameter("userFirst"));
			user.setUserLast(request.getParameter("userLast"));
			user.setUserEmail(request.getParameter("userEmail"));
			user.setUserLevel(Integer.parseInt(request.getParameter("userLevel")));
			if (request.getParameter("userPassword") != null){
				user.setUserPassword(request.getParameter("userPassword"));
			}
			updateFailed = !dbServices.updateUser(user);
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%-- Pulling Bootstrap from Content Delivery Network / Need to download and host myself --%>
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Users</title>
</head>
<body>
	<%@ include file="/WEB-INF/AdminMenu.jspf" %>
	<% if(pathinfo.equalsIgnoreCase("Edit") || pathinfo.equalsIgnoreCase("Add") || updateFailed){ 
		User user = null;
		if(pathinfo.equalsIgnoreCase("Edit")){
			user = dbServices.getUserByID(Integer.parseInt(request.getParameter("userID")));
		} %>
	<div class="container-fluid">
	<h2 class="text-center">Edit User</h2>
	<Form class="form-horizontal col-md-6 col-md-offset-3" action="<%= response.encodeURL(context.getContextPath()+"/Admin/Users") %>">
		<input type="hidden" name="Update" value="<% if(user!=null){%><%= user.getUserID()%><%}else{%>0<%}%>"> 
		<div class="form-group">
			<label for="userName" class="control-label">Username</label>
			<input class="form-control" name="userName" id="userName"<% if(user != null){ %> value="<%= user.getUserName() %>" <% } %> required>
		</div>
		<% if(user!=null && user.getUserName().equalsIgnoreCase(session.getAttribute("username").toString())
				|| user == null){ %>
		<div class="form-group">
			<label for="userPassword" class="control-label">Password</label>
			<input class="form-control" name="userPassword" id="userPassword"<% if(user != null){ %> value="<%= user.getUserPassword() %>" <% } %> required>
		</div>
		<% } %>
		<div class="form-group">
			<label for="userFirst" class="control-label">First Name</label>
			<input class="form-control" name="userFirst" id="userFirst" <% if(user != null){ %> value="<%= user.getUserFirst() %>" <% } %>>
		</div>
		<div class="form-group">
			<label for="userLast" class="control-label">Last Name</label>
			<input class="form-control" name="userLast" id="userLast"<% if(user != null){ %> value="<%= user.getUserLast() %>" <% } %>>
		</div>
		<div class="form-group">
			<label for="userEmail" class="control-label">Email</label>
			<input type="email" class="form-control" name="userEmail" id="userEmail"<% if(user != null){ if (user.getUserEmail()!=null){%> value="<%= user.getUserEmail() %>" <% }} %>>
		</div>
		<div class="form-group">
			<div class="col-md-4">
			<label for="userLevel" class="control-label">User Level</label>
			<select class="form-control" name="userLevel" id="userLevel">
				<option value="1" <% if(user!=null && user.getUserLevel() == 1){%>selected<%}%>>user</option>
				<option value="2" <% if(user!=null && user.getUserLevel() == 2){%>selected<%}%>>admin</option>
			</select>
			</div>
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-default">Apply</button>
		</div>
		<% if (updateFailed){ %>
		<p style="color:red"><i>Failed to apply changes. Username and Email (if provided) must be unique.</i></p>
		<% } %>
	</Form>
	</div>
	<% } %>
	<div class="container-fluid">
	<h2 class="text-center">Current Users</h2>
	<table class="table">
	<thead>
		<tr>
			<th>Username</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>E-Mail</th>
			<th>User Level</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
	</thead>
	<tbody>
	<% 
		List<User> users = dbServices.getUsers();
		for (User u : users){ %>
			<tr>
				<td><%= u.getUserName() %></td>
				<td><%= u.getUserFirst() %></td>
				<td><%= u.getUserLast() %></td>
				<td><%if (u.getUserEmail() == null){%><%= u.getUserEmail() %><%}%></td>
				<% if (u.getUserLevel() == 1){ %>
					<td>User</td>
				<% } else if (u.getUserLevel() == 2) {%>
					<td>Admin</td>
				<% } %>
				<td>
					<form action="<%= response.encodeURL(context.getContextPath()+"/Admin/Users/Edit") %>" method="post">
						<input type="hidden" name="userID" value="<%= u.getUserID() %>">
						<button class="button">Edit</button>
					</form>
				</td>
				<td>
					<form action="<%= response.encodeURL(context.getContextPath()+"/Admin/Users/Delete") %>" method="post">
						<input type="hidden" name="userID" value="<%= u.getUserID() %>">
						<button class="button">Delete</button>
					</form>
				</td>
			</tr>			
	<% } %>
	</tbody>
	</table>
	</div>
	<script>
	$(document).ready( function(){
		$("#add > a").attr("href","<%= response.encodeURL(context.getContextPath()+"/Admin/Users/Add") %>");
	    $("#add > a").append(" User");
	    $("#add").show();
	});
	</script>
</body>
</html>