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
		conflictServices cs = new conflictServices();
		//Check if account was deleted
		as.delAccount();
	%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
	}%>
<!-- End Validation -->


	<!--  Start Header -->
	<%@ include file="/WEB-INF/AdminMenu.jspf" %>
	<!--  End Header -->
	

<%String semester = session.getAttribute("semester").toString();
if(semester.equals("summer")){%>
<h2 class="text-center">Current Summer Conflicts</h2>

<%} else if (semester.equals("spring")){%>
<h2 class="text-center">Current Spring Conflicts</h2>

<%} else {%>
<h2 class="text-center">Current Fall Conflicts</h2>
<%} %>
</br></br></br></br>
<table>
<tbody>
<thead>
<tr>
	<th>Class1</th>
	<th>Class2</th>
	<th>ConfType</th>
	<th>Value1</th>
	<th>Value2</th>
</tr>
</thead>
<% 
	List<Conflict> conflicts = cs.getConflicts(session.getAttribute("semester").toString()); 
	if (conflicts != null){
	for (Conflict c : conflicts){ %>
<tr>
	<td><%= c.getClass1() %></td>
	<td><%= c.getClass2() %></td>
	<td><%= c.getConfType() %></td>
	<td><%= c.getValue1() %></td>
	<td><%= c.getValue2() %></td>
</tr>
<% }} %>

</tbody>
</table>

</body>
</html>