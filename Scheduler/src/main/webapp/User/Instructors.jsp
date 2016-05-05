<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.scheduler.services.*" %>
<%@ page import="com.scheduler.valueObjects.*" %>
<% 
	String semester = session.getAttribute("semester").toString();
	int userlevel = Integer.parseInt(session.getAttribute("userlevel").toString());
	ServletContext context = session.getServletContext();
	String pathinfo = request.getPathInfo();
	if (pathinfo == null){
		pathinfo = "";
	} else {
		pathinfo = pathinfo.substring(1);
	}
	if (request.getParameter("getInstructorTable") != null){
		response.setContentType("application/json"); %>
		<%= jsonServices.buildInstructors(semester, context, response, userlevel) %>
<%	} else if(pathinfo.equalsIgnoreCase("Select") && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
		response.setContentType("application/json"); %>
		<%= jsonServices.buildWeek(jsonServices.buildClasslistByInstructor(semester, Integer.parseInt(request.getParameter("instructorID"))), context, response) %>
<%	} else{ 
		Boolean updateFailed = false;
		if(userlevel == User.USER_ADMIN){
			if(pathinfo.equalsIgnoreCase("Delete") && request.getParameter("instructorID") != null){
				dbServices.deleteInstructor(semester, Integer.parseInt(request.getParameter("instructorID")));
			} else if (request.getParameter("update") != null){
				Instructor instructor = new Instructor();
				instructor.setID(Integer.parseInt(request.getParameter("update")));
				if (request.getParameter("firstName") != null){
					instructor.setNameFirst(request.getParameter("firstName"));
				}
				if (request.getParameter("lastName") != null){
					instructor.setNameLast(request.getParameter("lastName"));
				}
				if (request.getParameter("boardType") != null){
					instructor.setPrefBoard(request.getParameter("boardType"));
				}
				if (request.getParameter("chairType") != null){
					instructor.setPrefChair(request.getParameter("chairType"));
				}
				if (request.getParameter("roomType") != null){
					instructor.setPrefDesk(request.getParameter("roomType"));
				}
				if (request.getParameter("comment") != null){
					instructor.setComment(request.getParameter("comment"));
				}
				if (request.getParameter("applyall") != null && request.getParameterValues("applyall")[0].equalsIgnoreCase("applyall")){
					updateFailed = !dbServices.updateInstructors(instructor);
				} else {
					updateFailed = !dbServices.updateInstructors(semester, instructor);
				}
			}
		}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Instructors</title>
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

  <link rel="stylesheet" type="text/css" href="<%= response.encodeURL(context.getContextPath()+"/DataTables/datatables.min.css") %>"/>
  <script type="text/javascript" src="<%= response.encodeURL(context.getContextPath()+"/DataTables/datatables.min.js") %>"></script>
	<script>
	function ajaxCallback(json){
		for (var i = 0; i < json.data.length; i++){
			var class1 = json.data[i];
			var row = $('#WeekTable > tbody').find('tr:eq('+class1.Start.toString()+')');
			var classString = "<a href=" + class1.link + ">" + class1.Class_Number + " " + class1.Time + "</a><br>";
			for (var j = 0; j < class1.Duration; j++){
				if (class1.Sun !== 0){
					var cell = row.find('td:eq(0)');
					if (j == 0){
						cell.append(classString);
					}
					cell.css('background-color','PaleTurquoise');
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
					cell.css('background-color','PaleTurquoise');
				}
				row = row.next('tr');
			};
		};
		window.scrollTo(0,0);
	}
	
	function getSchedule(id, instructorName){
		$('#WeekTable > tbody > tr > td').empty();
		$('#WeekTable > tbody > tr > td').css('background-color', '');
		$('#WeekTableHeader').html(instructorName + "'s Schedule");
		$('#InstructorEditDiv').hide();
		$('#WeekTableDiv').show();
		$.ajax({
				dataType:"json",
				url:"<%= response.encodeURL(context.getContextPath()+"/Instructors/Select") %>",
				data: {instructorID : id},
				success: function (data) {ajaxCallback(data);},
				type: "POST"
				});
	}
	
	$(document).ready( function(){
		$("#InstructorTable").DataTable({
	        "lengthMenu": [[25, 50, -1], [25, 50, "All"]],
	        "ajax" : {url : "<%= response.encodeURL(context.getContextPath()+"/Instructors/") %>",
	        	"data" : {getInstructorTable: "True"}},
	        "columns": [
	            {"data" : "Select_Instructor"},
	            {"data" : "firstname"},
	            {"data" : "lastname"},
	            {"data" : "desk"},
	            {"data" : "board"},
	            {"data" : "chair"},
	            {"data" : "comment"},
				<% if (userlevel == User.USER_ADMIN) {%>
                   { "data": "Edit_Instructor" },
                   { "data": "Delete_Instructor" }
                   <% } %>
	                ],
	                "order": [[ 1, "asc" ]]
		});
<% if(userlevel == User.USER_ADMIN){ %>
	    $("#add > a").attr("href","<%= response.encodeURL(context.getContextPath()+"/Instructors/Add") %>");
	    $("#add > a").append(" Instructor");
	    $("#add").show();
<% } %>
	});
</script>
</head>
<body>
<%@ include file="/WEB-INF/AdminMenu.jspf" %>
<%@ include file="/WEB-INF/WeekTable.jspf" %>
<div id="InstructorEditDiv" class="container-fluid">
<% if(userlevel == User.USER_ADMIN){
		if(pathinfo.equalsIgnoreCase("Edit") || pathinfo.equalsIgnoreCase("Add")){
			Instructor instructor = new Instructor();
			if (request.getParameter("instructorID") != null){
				instructor = dbServices.getInstructorByID(semester, Integer.parseInt(request.getParameter("instructorID").toString()));
			} %>
		<h2 class="text-center"><% if(instructor.getID() > 0){ %>Edit <%= instructor.getNameFirst() %> <%= instructor.getNameLast() %><% } else {%>Add<% } %></h2>
		<Form class="form-horizontal col-md-6 col-md-offset-3" action="<%= response.encodeURL(context.getContextPath()+"/Instructors") %>">
		<input type="hidden" name="update" value="<% if(instructor.getID() > 0){%><%= instructor.getID()%><%}else{%>0<%}%>"> 
		<div class="form-group">
			<label for="firstName" class="control-label">First Name</label>
			<input class="form-control" name="firstName" id="firstName" value="<%= instructor.getNameFirst() %>" required>
		</div>
		<div class="form-group">
			<label for="lastName" class="control-label">Last Capacity</label>
			<input class="form-control" name="lastName" id="lastName" value="<%= instructor.getNameLast() %>" required>
		</div>
		<div class="form-group">
			<label for="boardType" class="control-label">Classroom Board Preference</label>
			<input class="form-control" name="boardType" id="boardType" value="<%= instructor.getPrefBoard() %>" >
		</div>
		<div class="form-group">
			<label for="chairType" class="control-label">Classroom Chair Preference</label>
			<input class="form-control" name="chairType" id="chairType" value="<%= instructor.getPrefChair() %>" >
		</div>
		<div class="form-group">
			<label for="deskType" class="control-label">Classroom Desk Preference</label>
			<input class="form-control" name="deskType" id="deskType" value="<%= instructor.getPrefDesk() %>" >
		</div>
		<div class="form-group">
			<label for="comment" class="control-label">Comments</label>
			<textarea rows="4" class="form-control" name="comment" id="comment"><%= instructor.getComment() %></textarea>
		</div>
		<div class="form-group">
			<input type="checkbox" name="applyall" value="applyall" checked>
			<label for="checkbox">
				Apply to All Semesters
			</label>
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-default">Apply</button>
		</div>
		<% if (updateFailed){ %>
		<p style="color:red"><i>Failed to apply changes</i></p>
		<% } %>
	</Form>
<%	}	} %>
</div>
<div class="container-fluid">
  <% if(semester.equals("summer")){%>
  <h2 class="text-center">Summer Instructors</h2>
  <%} else if (semester.equals("spring")){%>
  <h2 class="text-center">Spring Instructors</h2>
	
  <%} else {%>
  <h2 class="text-center">Fall Instructors</h2>
  <% } %>
  <br/><br/><br/><br/>
  <table id="InstructorTable" class="table table-striped table-bordered text-center">
    <thead>
      <tr>
        <th>Select Instructor</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Desk Preference</th>
        <th>Board Preference</th>
        <th>Chair Preference</th>
        <th>Comment</th>
        <% if (userlevel == User.USER_ADMIN) { %>
        <th>Edit Instructor</th>
        <th>Delete Instructor</th>
        <% } %>
      </tr>
    </thead>
    <tbody>
    </tbody>
  </table>
</div>
</body>
</html>
<% } %>