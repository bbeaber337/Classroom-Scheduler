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
	if (request.getParameter("getClassroomTable") != null){
		response.setContentType("application/json"); %>
		<%= jsonServices.buildClassrooms(semester, context, response, userlevel) %>
<%	} else if(pathinfo.equalsIgnoreCase("Select") && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
		response.setContentType("application/json"); %>
		<%= jsonServices.buildWeek(jsonServices.buildClasslistByClassroom(semester, Integer.parseInt(request.getParameter("roomID"))), context, response) %>
<%	} else{ 
		Boolean updateFailed = false;
		if(userlevel == User.USER_ADMIN){
			if(pathinfo.equalsIgnoreCase("Delete") && request.getParameter("roomID") != null){
				dbServices.deleteClassroom(semester, Integer.parseInt(request.getParameter("roomID")));
			} else if (request.getParameter("update") != null){
				Classroom classroom = new Classroom();
				classroom.setRoomID(Integer.parseInt(request.getParameter("update")));
				if (request.getParameter("roomName")!=null){
					classroom.setRoomName(request.getParameter("roomName"));
				}
				if (request.getParameter("roomCapacity")!=null){
					classroom.setRoomCapacity(Integer.parseInt(request.getParameter("roomCapacity")));
				}
				if (request.getParameter("roomType")!=null){
					classroom.setRoomType(request.getParameter("roomType"));
				}
				if (request.getParameter("boardType")!=null){
					classroom.setRoomBoardType(request.getParameter("boardType"));
				}
				if (request.getParameter("chairType")!=null){
					classroom.setRoomChairType(request.getParameter("chairType"));
				}
				if (request.getParameter("deskType")!=null){
					classroom.setRoomDeskType(request.getParameter("deskType"));
				}
				if (request.getParameter("distLearning")!=null){
					classroom.setRoomDistLearning(request.getParameter("distLearning"));
				}
				if (request.getParameter("projectors")!=null){
					classroom.setRoomProjectors(Integer.parseInt(request.getParameter("projectors")));
				}
				if (request.getParameter("applyall") != null && request.getParameterValues("applyall")[0].equalsIgnoreCase("applyall")){
					updateFailed = !dbServices.updateClassrooms(classroom);
				} else {
					updateFailed = !dbServices.updateClassrooms(semester, classroom);
				}
			}
		}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Classrooms</title>
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
	
	function getSchedule(classroomID, roomName){
		$('#WeekTable > tbody > tr > td').empty();
		$('#WeekTable > tbody > tr > td').css('background-color', '');
		$('#WeekTableHeader').html(roomName + " Schedule");
		$('#ClassroomEditDiv').hide();
		$('#WeekTableDiv').show();
		$.ajax({
				dataType:"json",
				url:"<%= response.encodeURL(context.getContextPath()+"/Classrooms/Select") %>",
				data: {roomID : classroomID},
				success: function (data) {ajaxCallback(data);},
				type: "POST"
				});
	}
	
	$(document).ready( function(){
		$("#classroomTable").DataTable({
	        "lengthMenu": [[30, -1], [30, "All"]],
	        "ajax" : {url : "<%= response.encodeURL(context.getContextPath()+"/Classrooms/") %>",
	        	"data" : {getClassroomTable: "True"}},
	        "columns": [
	            {"data" : "Select_Room"},
	            {"data" : "name"},
	            {"data" : "capacity"},
	            {"data" : "roomType"},
	            {"data" : "desk"},
	            {"data" : "board"},
	            {"data" : "chair"},
	            {"data" : "distLearning"},
	            {"data" : "projectors"},
				<% if (userlevel == User.USER_ADMIN) {%>
                   { "data": "Edit_Room" },
                   { "data": "Delete_Room" }
                   <% } %>
	                ],
	                "order": [[ 1, "asc" ]]
		});
<% if(userlevel == User.USER_ADMIN){ %>
	    $("#add > a").attr("href","<%= response.encodeURL(context.getContextPath()+"/Classrooms/Add") %>");
	    $("#add > a").append(" Classroom");
	    $("#add").show();
<% } %>
	});
</script>
</head>
<body>
<%@ include file="/WEB-INF/AdminMenu.jspf" %>
<%@ include file="/WEB-INF/WeekTable.jspf" %>
<div id="ClassroomEditDiv" class="container-fluid">
<% if(userlevel == User.USER_ADMIN){
		if(pathinfo.equalsIgnoreCase("Edit") || pathinfo.equalsIgnoreCase("Add")){
			Classroom classroom = new Classroom();
			if (request.getParameter("roomID") != null){
				classroom = dbServices.getClassroomByID(semester, Integer.parseInt(request.getParameter("roomID").toString()));
			} %>
		<h2 class="text-center"><% if(classroom.getRoomID() > 0){ %>Edit <%= classroom.getRoomName() %><% } else {%>Add<% } %></h2>
		<Form class="form-horizontal col-md-6 col-md-offset-3" action="<%= response.encodeURL(context.getContextPath()+"/Classrooms") %>">
		<input type="hidden" name="update" value="<% if(classroom.getRoomID() > 0){%><%= classroom.getRoomID()%><%}else{%>0<%}%>"> 
		<div class="form-group">
			<label for="roomName" class="control-label">Classroom Name</label>
			<input class="form-control" name="roomName" id="roomName" value="<%= classroom.getRoomName() %>" required>
		</div>
		<div class="form-group">
			<label for="roomCapacity" class="control-label">Classroom Capacity</label>
			<input type="number" class="form-control" name="roomCapacity" id="roomCapacity" value="<%= classroom.getRoomCapacity() %>" required>
		</div>
		<div class="form-group">
			<label for="roomType" class="control-label">Classroom Type</label>
			<input class="form-control" name="roomType" id="roomType" value="<%= classroom.getRoomType() %>" >
		</div>
		<div class="form-group">
			<label for="boardType" class="control-label">Classroom Board Type</label>
			<input class="form-control" name="boardType" id="boardType" value="<%= classroom.getRoomBoardType() %>" >
		</div>
		<div class="form-group">
			<label for="chairType" class="control-label">Classroom Chair Type</label>
			<input class="form-control" name="chairType" id="chairType" value="<%= classroom.getRoomChairType() %>" >
		</div>
		<div class="form-group">
			<label for="deskType" class="control-label">Classroom Desk Type</label>
			<input class="form-control" name="deskType" id="deskType" value="<%= classroom.getRoomDeskType() %>" >
		</div>
		<div class="form-group">
			<label for="distLearning" class="control-label">Distance Learning</label>
			<input class="form-control" name="distLearning" id="distLearning" value="<%= classroom.getRoomDistLearning() %>" >
		</div>
		<div class="form-group">
			<label for="projectors" class="control-label">Number of Projectors</label>
			<input type="number" class="form-control" name="projectors" id="projectors" value="<%= classroom.getRoomProjectors() %>" >
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
  <h2 class="text-center">Summer Classrooms</h2>
  <%} else if (semester.equals("spring")){%>
  <h2 class="text-center">Spring Classrooms</h2>
	
  <%} else {%>
  <h2 class="text-center">Fall Classrooms</h2>
  <% } %>
  <br/><br/><br/><br/>
  <table id="classroomTable" class="table table-striped table-bordered text-center">
    <thead>
      <tr>
        <th>Select Room</th>
        <th>Classroom</th>
        <th>Capacity</th>
        <th>Room Type</th>
        <th>Desk Type</th>
        <th>Board Type</th>
        <th>Chair Type</th>
        <th>Distance Learning</th>
        <th>Projectors</th>
        <% if (userlevel == User.USER_ADMIN) { %>
        <th>Edit Room</th>
        <th>Delete Room</th>
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