<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>	<%@ page import="com.scheduler.services.*" %>
	<%@ page import="com.scheduler.valueObjects.*" %>
	<%@ page import="com.scheduler.jsp.*" %>
	<%@ page import="java.util.*" %>
	<%@ page import="java.sql.*" %>
	<% if (request.getParameter("classroomID") != null){
		 response.setContentType("application/json");
	   }
	    HTMLServices hs = new HTMLServices(session, request, response, out); 
		adminServices as = new adminServices(session, request, response, out);
		boolean build = as.editClassroom();
	%>
<%-- Begin Validation --%>
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
<%-- End Validation --%>

 <% if (request.getParameter("classroomID") != null){
	 hs.buildClasslistByClassroom(request.getParameter("classroomID")); 
     } else {%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  <!-- Pulling Bootstrap from Content Delivery Network / Need to download and host myself -->
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

  <link rel="stylesheet" type="text/css" href="../DataTables/datatables.min.css"/>
  <script type="text/javascript" src="../DataTables/datatables.min.js"></script>


<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script>
	function ajaxCallback(json){
		function generateDiv(c){
			return c;
		}
		for (var i = 0; i < json.data.length; i++){
			var class1 = json.data[i];
			var row = $('#WeekTable > tbody').find('tr:eq('+class1.Start.toString()+')');
			var classString = "<a href=\"viewClasses.jsp?editClass="+ class1.Class_Id + "\">" + class1.Class_Number + " " + class1.Time + "</a><br>";
			console.log(class1.Class_Id);
			for (var j = 0; j < class1.Duration; j++){
				if (class1.Sun !== 0){
					var cell = row.find('td:eq(0)');
					if (j == 0){
						cell.append(classString);
					}
					cell.css('background-color','PaleGreen');
				}
				if (class1.Mon == 1){
					var cell = row.find('td:eq(1)');
					if (j == 0){
						cell.append(classString);
					}
					cell.css('background-color','PaleGreen');
				}
				if (class1.Tues == 1){
					var cell = row.find('td:eq(2)');
					if (j == 0){
						cell.append(classString);
					}
					cell.css('background-color','PaleTurquoise');
				}
				if (class1.Wed == 1){
					var cell = row.find('td:eq(3)');
					if (j == 0){
						cell.append(classString);
					}
					cell.css('background-color','PaleGreen');
				}
				if (class1.Thur == 1){
					var cell = row.find('td:eq(4)');
					if (j == 0){
						cell.append(classString);
					}
					cell.css('background-color','PaleTurquoise');
				}
				if (class1.Fri == 1){
					var cell = row.find('td:eq(5)');
					if (j == 0){
						cell.append(classString);
					}
					cell.css('background-color','PaleGreen');
				}
				if (class1.Sat == 1){
					var cell = row.find('td:eq(6)');
					if (j == 0){
						cell.append(classString);
					}
					cell.css('background-color','PaleGreen');
				}
				row = row.next('tr');
			};
		};
		window.scrollTo(0,0);
	}
	
	function getSchedule(roomID, roomName){
		$('#WeekTable > tbody > tr > td').empty();
		$('#WeekTable > tbody > tr > td').css('background-color', '');
		$('#WeekTable').show();
		$('#WeekTableHeader').html(roomName + " Schedule");
		$.ajax({
				dataType:"json",
				url:"viewClassrooms.jsp",
				data: {classroomID : roomID},
				success: function (data) {ajaxCallback(data);},
				type: "POST"
				});
	}
	
	$(document).ready( function(){
		$("#classroomTable").DataTable({"lengthMenu": [[30, -1], [30, "All"]],"order": [[ 1, "asc" ]]});
	    $("#add > a").attr("href","viewClassrooms.jsp?new=true");
	    $("#add > a").append(" Classroom");
	    $("#add").show();
	});
</script>
</head>
<body>




	<!--  Start Header -->
	<%@ include file="/WEB-INF/AdminMenu.jspf" %>
	<!--  End Header -->
	
	<%@ include file="/WEB-INF/WeekTable.jspf" %>
	

<!-- Check if a change was submitted -->
<% as.submitClassroomEdit();
   as.deleteClassroom();
%>
		
<%String semester = session.getAttribute("semester").toString();
if(semester.equals("summer")){%>
<h2 class="text-center">Summer Classrooms</h2>

<%} else if (semester.equals("spring")){%>
<h2 class="text-center">Spring Classrooms</h2>

<%} else {%>
<h2 class="text-center">Fall Classrooms</h2>
<%} %>
</br></br></br></br>
<%hs.buildClassrooms();
//}%>


</body>
</html>
<%}%>