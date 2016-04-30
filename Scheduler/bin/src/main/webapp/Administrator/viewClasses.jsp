<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  <!-- Pulling Bootstrap from Content Delivery Network / Need to download and host myself -->
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  
  <link rel="stylesheet" type="text/css" href="../DataTables/datatables.min.css"/>
  <script type="text/javascript" src="../DataTables/datatables.min.js"></script>
  
  <!-- For sortable table -->
  <!-- <script src="sortable.min.js"></script>-->
  <!-- <link rel="stylesheet" href="sortable-theme-bootstrap.css" />-->

<title>Insert title here</title>

	<%@ page import="com.scheduler.services.*" %>
	<%@ page import="com.scheduler.valueObjects.*" %>
	<%@ page import="com.scheduler.jsp.*" %>
	<%@ page import="java.util.*" %>
	<%@ page import="java.sql.*" %>
	<% HTMLServices hs = new HTMLServices(session, request, response, out); 
		adminServices as = new adminServices(session, request, response, out);
		boolean build = as.editClass();
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
	
<%//if(build){%>
<!-- Check if a change was submitted -->
<% as.submitClassEdit();
   as.deleteClass();
   if(as.selectClass()){
		   
   } else {%>
		
<%String semester = session.getAttribute("semester").toString();
if(semester.equals("summer")){%>
<h2 class="text-center">Summer Classes</h2>

<%} else if (semester.equals("spring")){%>
<h2 class="text-center">Spring Classes</h2>

<%} else {%>
<h2 class="text-center">Fall Classes</h2>
<%} %>

</br></br></br></br>
<%hs.buildClasses(); }
//}%>
<script>
$(document).ready( function () {
    $('#classTable').DataTable({
        "lengthMenu": [[25, 50, 100, -1], [25, 50, 100, "All"]],
        "ajax" : "../API/Classlist.jsp",
        "columns": [
                    { "data": "Change_Room" },
                    { "data": "Class_Number" },
                    { "data": "Name" },
                    { "data": "Room" },
                    { "data": "Subject" },
                    { "data": "First_Name" },
                    { "data": "Last_Name" },
                    { "data": "Days" },
                    { "data": "Start_Time" },
                    { "data": "End_Time" },
                    { "data": "Start_Date" },
                    { "data": "End_Date" },
                    { "data": "Capacity" },
                    { "data": "Enrolled" },
                    { "data": "Catalog" },
                    { "data": "Section" },
                    { "data": "Description" },
                    { "data": "Campus" },
                    { "data": "Academic_Group" },
                    { "data": "Mode" },
                    { "data": "Combined" },
                    { "data": "Edit_Class" },
                    { "data": "Delete_Class" }
                ],
                "scrollX": true
    });
} );
</script>


</body>
</html>