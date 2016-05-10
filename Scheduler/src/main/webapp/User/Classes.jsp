<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.scheduler.services.*" %>
<%@ page import="com.scheduler.valueObjects.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<% 
	ServletContext context = session.getServletContext();
	String semester = session.getAttribute("semester").toString();
	int userlevel = Integer.parseInt(session.getAttribute("userlevel").toString());
	String pathinfo = request.getPathInfo();
	if (pathinfo == null){
		pathinfo = "";
	} else {
		pathinfo = pathinfo.substring(1);
	}
	if (request.getParameter("getClassTable") != null){
		response.setContentType("application/json"); %>
		<%= jsonServices.buildClasses(semester, context, response, userlevel) %>
<%	} else if (pathinfo.equalsIgnoreCase("check")){
		response.setContentType("application/json");
		Class1 c = new Class1();
		List<String> headers = dbServices.getHeaders(semester);
		Map<String, String> ournames = dbServices.getOurNames(semester);
		for (String s : ournames.keySet()) {
			c.set(ournames.get(s), request.getParameter(s));
		}
		for (String s : headers){
			if (!ournames.containsValue(s)){
				c.set(s,request.getParameter(s));
			}
		}
		if (request.getParameter("classID") != null){
			c.setClassID(Integer.parseInt(request.getParameter("classID")));
		}
		String groupnum = request.getParameter("groupnum");
		if (groupnum != null && !groupnum.equalsIgnoreCase("")){
			c.setGroupNumber(Integer.parseInt(groupnum));
		} else {
			c.setGroupNumber(0);
		} %>
		<%= jsonServices.buildClassChangeConflicts(semester, c) %>
<%	} else {
		Boolean updateFailed = false;
		if(userlevel == User.USER_ADMIN){
			if(pathinfo.equalsIgnoreCase("Delete")){
				dbServices.deleteClasses(semester, Integer.parseInt(request.getParameter("classID")));
			} else if (request.getParameter("Update") != null){
				Class1 c = new Class1();
				List<String> headers = dbServices.getHeaders(semester);
				Map<String, String> ournames = dbServices.getOurNames(semester);
				for (String s : ournames.keySet()) {
					c.set(ournames.get(s), request.getParameter(s));
				}
				for (String s : headers){
					if (!ournames.containsValue(s)){
						c.set(s,request.getParameter(s));
					}
				}
				if (request.getParameter("classID") != null){
					c.setClassID(Integer.parseInt(request.getParameter("classID")));
				}
				String groupnum = request.getParameter("groupnum");
				if (groupnum != null && !groupnum.equalsIgnoreCase("")){
					c.setGroupNumber(Integer.parseInt(groupnum));
				} else {
					c.setGroupNumber(0);
				}
				
				if (request.getParameter("applyall") != null && request.getParameterValues("applyall")[0].equalsIgnoreCase("applyall")){
					updateFailed = !dbServices.updateGroup(semester, c);
				} else {
					updateFailed = !dbServices.updateClasses(semester, c);
				}
			} else if (request.getParameter("RoomUpdate") != null){
				Map<String, String> ournames = dbServices.getOurNames(semester);
				String classID = request.getParameter("classID");
				String classroom = request.getParameter("classroom");
				if (classID != null &&  classroom != null){
					Classlist classlist = dbServices.getClasses(semester, Integer.parseInt(classID));
					for (Class1 c : classlist){
						c.set(ournames.get("classroom"), classroom);
					}
					dbServices.updateClasses(semester, classlist);
				}
			}
		}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Classes</title>
  <%-- Pulling Bootstrap from Content Delivery Network / Need to download and host myself --%>
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  
  <link rel="stylesheet" type="text/css" href="<%= response.encodeURL(context.getContextPath()+"/DataTables/datatables.min.css") %>"/>
  <script type="text/javascript" src="<%= response.encodeURL(context.getContextPath()+"/DataTables/datatables.min.js") %>"></script>
</head>
<body>
<%@ include file="/WEB-INF/AdminMenu.jspf" %>
<% if (userlevel == User.USER_ADMIN) {%>
<% if(pathinfo.equalsIgnoreCase("Edit") || pathinfo.equalsIgnoreCase("Add") || updateFailed){
	Classlist classlist = null;
	if(pathinfo.equalsIgnoreCase("Edit")){
		classlist = dbServices.getClasses(semester, Integer.parseInt(request.getParameter("classID")));
	} %>
	<div class="container-fluid">
	<h2 class="text-center">Edit Class</h2>
	<Form class="form-horizontal col-md-8" method="post" id="editForm" action="<%= response.encodeURL(context.getContextPath()+"/Classes") %>">
		<%
			Class1 c = null;
			if (classlist != null && classlist.size() > 0){
				c = classlist.get(0);
			}
			Map<String, String> ournames = dbServices.getOurNames(semester);
		%>
		<div class="form-group">
			<input type="hidden" name="Update" value="Update">
		<% if (c != null){ %>
			<input type="hidden" name="classID" value="<%= c.getClassID() %>">
		<% } %>
			<p class="text-center conflictDisplay" style="color:red;display:none;"><i>Creates a Conflict</i></p>
			<div class="col-md-4">
				<label for="groupnum" class="control-label">Class Group Number</label>
				<input type="number" class="form-control" name="groupnum" id="groupnum" <% if(c != null && c.getGroupNumber() != 0){ %>value="<%= c.getGroupNumber() %>"<% } %>>
			</div>
			<div class="col-md-offset-2 col-md-3">
				<label for="combo" class="control-label">Combo</label>
				<input class="form-control" name="combo" id="combo" placeholder="C" <% if(c != null && c.get(ournames.get("combo")) != null){ %>value="<%= c.get(ournames.get("combo")) %>"<% } %>>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-4">
				<label for="classroom" class="control-label">Classroom</label>
				<select class="form-control conflictCheck" name="classroom" id="classroom">
					<% for (Classroom room : dbServices.getClassrooms(semester)){%>
					<option value="<%= room.getRoomName() %>" <% if(c != null && c.get(ournames.get("classroom")) != null && c.get(ournames.get("classroom")).equalsIgnoreCase(room.getRoomName())){%>selected<%}%>><%= room.getRoomName() %></option>
					<% } %>
				</select>
			</div>
		</div>
		<div class="form-group<% if (c != null && classlist != null && c.get(ournames.get("capacity")) != null && (classlist.getTotEnrolled() > Integer.parseInt(c.get(ournames.get("capacity"))) || classlist.getTotEnrolled() > classlist.getCapEnrolled())){ %> has-error<% } %>">
			<div class="col-md-3">
				<label for="grouptotenrolled" class="control-label">Group Total Enrolled</label>
				<input class="form-control" name="grouptotenrolled" id="grouptotenrolled" <% if(classlist != null){ %>value="<%= classlist.getTotEnrolled() %>"<% } %> readonly>
			</div>
			<div class="col-md-offset-1 col-md-3">
				<label for="capenrolled" class="control-label">Group Class Capacity</label>
				<input class="form-control" name="groupcapenrolled" id="groupcapenrolled" <% if(classlist != null){ %>value="<%= classlist.getCapEnrolled() %>"<% } %> readonly>
			</div>
			<div class="col-md-offset-1 col-md-3">
				<label for="groupcapacity" class="control-label">Classroom Capacity</label>
				<input class="form-control" name="groupcapacity" id="groupcapacity" <% if(c != null && c.get(ournames.get("capacity")) != null){ %>value="<%= c.get(ournames.get("capacity")) %>"<% } %> readonly>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-5">
				<div class="col-md-5">
					<label for="starttime" class="control-label">Start Time</label>
					<input class="form-control conflictCheck" name="starttime" id="starttime" placeholder="HH:MM:SS am/pm" <% if(c != null && c.get(ournames.get("starttime")) != null){ %>value="<%= c.get(ournames.get("starttime")) %>"<% } %>>
				</div>
				<div class="col-md-5">
					<label for="endtime" class="control-label">End Time</label>
					<input class="form-control conflictCheck" name="endtime" id="endtime" placeholder="HH:MM:SS am/pm" <% if(c != null && c.get(ournames.get("endtime")) != null){ %>value="<%= c.get(ournames.get("endtime")) %>"<% } %>>
				</div>
			</div>
			<div class="col-md-5">
				<div class="col-md-5">
					<label for="startdate" class="control-label">Start Date</label>
					<input class="form-control conflictCheck" name="startdate" id="startdate" placeholder="MM/DD/YYYY" <% if(c != null && c.get(ournames.get("startdate")) != null){ %>value="<%= c.get(ournames.get("startdate")) %>"<% } %>>
				</div>
				<div class="col-md-5">
					<label for="enddate" class="control-label">End Date</label>
					<input class="form-control conflictCheck" name="enddate" id="enddate" placeholder="MM/DD/YYYY" <% if(c != null && c.get(ournames.get("enddate")) != null){ %>value="<%= c.get(ournames.get("enddate")) %>"<% } %>>
				</div>
			</div>
			<div class="col-md-2">
				<label for="days" class="control-label">Days</label>
				<input class="form-control conflictCheck" name="days" id="days" placeholder="MTWRFS" <% if(c != null && c.get(ournames.get("days")) != null){ %>value="<%= c.get(ournames.get("days")) %>"<% } %> >
			</div>
		</div>
		<div class="form-group">
			<input type="checkbox" name="applyall" value="applyall" id="applyall" <% if (c != null && c.getGroupNumber() > 0){ %>checked<% } %>>
			<label for="checkbox">
				Apply to Group
			</label>
		</div>
		<hr role="seperator" class="divider">
		<div class="form-group">
			<div class="col-md-5">
				<label for="instructorfirst" class="control-label">Instructor's First Name</label>
				<input class="form-control conflictCheck" name="instructorfirst" id="instructorfirst" <% if(c != null && c.get(ournames.get("instructorfirst")) != null){ %>value="<%= c.get(ournames.get("instructorfirst")) %>"<% } %>>
			</div>
			<div class="col-md-offset-2 col-md-5">
				<label for="instructorlast" class="control-label">Instructor's Last Name</label>
				<input class="form-control conflictCheck" name="instructorlast" id="instructorlast" <% if(c != null && c.get(ournames.get("instructorlast")) != null){ %>value="<%= c.get(ournames.get("instructorlast")) %>"<% } %>>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-2">
				<label for="classnumber" class="control-label">Class Number</label>
				<input class="form-control" name="classnumber" id="classnumber" <% if(c != null && c.get(ournames.get("classnumber")) != null){ %>value="<%= c.get(ournames.get("classnumber")) %>"<% } %>>
			</div>
			<div class="col-md-offset-1 col-md-9">
				<label for="classname" class="control-label">Class Name</label>
				<input class="form-control" name="classname" id="classname" <% if(c != null && c.get(ournames.get("classname")) != null){ %>value="<%= c.get(ournames.get("classname")) %>"<% } %>>
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-3">
				<label for="subject" class="control-label">Subject</label>
				<input class="form-control" name="subject" id="subject" <% if(c != null && c.get(ournames.get("subject")) != null){ %>value="<%= c.get(ournames.get("subject")) %>"<% } %>>
			</div>
			<div class="col-md-offset-1 col-md-2">
				<label for="catalog" class="control-label">Catalog</label>
				<input class="form-control" name="catalog" id="catalog" <% if(c != null && c.get(ournames.get("catalog")) != null){ %>value="<%= c.get(ournames.get("catalog")) %>"<% } %>>
			</div>
			<div class="col-md-offset-1 col-md-2">
				<label for="section" class="control-label">Section</label>
				<input class="form-control" name="section" id="section" <% if(c != null && c.get(ournames.get("section")) != null){ %>value="<%= c.get(ournames.get("section")) %>"<% } %>>
			</div>
			<div class="col-md-offset-1 col-md-2">
				<label for="component" class="control-label">Component</label>
				<input class="form-control" name="component" id="component" <% if(c != null && c.get(ournames.get("component")) != null){ %>value="<%= c.get(ournames.get("component")) %>"<% } %>>
			</div>
		</div>
		<div class="form-group<% if (c != null && c.get(ournames.get("totenrolled")) != null && c.get(ournames.get("capenrolled")) != null && c.get(ournames.get("capacity")) != null && (Integer.parseInt(c.get(ournames.get("totenrolled"))) > Integer.parseInt(c.get(ournames.get("capacity"))) || Integer.parseInt(c.get(ournames.get("totenrolled"))) > Integer.parseInt(c.get(ournames.get("capenrolled"))))){ %> has-error<% } %>">
			<div class="col-md-3">
				<label for="totenrolled" class="control-label">Total Enrolled</label>
				<input class="form-control conflictCheck" name="totenrolled" id="totenrolled" <% if(c != null && c.get(ournames.get("totenrolled")) != null){ %>value="<%= c.get(ournames.get("totenrolled")) %>"<% } %> >
			</div>
			<div class="col-md-offset-1 col-md-3">
				<label for="capenrolled" class="control-label">Class Capacity</label>
				<input class="form-control" name="capenrolled" id="capenrolled" <% if(c != null && c.get(ournames.get("capenrolled")) != null){ %>value="<%= c.get(ournames.get("capenrolled")) %>"<% } %> >
			</div>
			<div class="col-md-offset-1 col-md-3">
				<label for="capacity" class="control-label">Classroom Capacity</label>
				<input class="form-control" name="capacity" id="capacity" <% if(c != null && c.get(ournames.get("capacity")) != null){ %>value="<%= c.get(ournames.get("capacity")) %>"<% } %> readonly>
			</div>
		</div>
		<p class="text-center conflictDisplay" style="color:red;display:none;"><i>Creates a Conflict</i></p>
		<%	List<String> headers = dbServices.getHeaders(semester);
			for (String s : headers){
				if(!ournames.containsValue(s)){ %>
		<div class="form-group">
			<div>
			<label for="<%= s %>" class="control-label"><%= s %></label>
			<input class="form-control" name="<%= s %>" id="<%= s %>" <% if(c != null){ %>value="<%= c.get(s) %>"<% } %>>
		</div></div>
			<% }} %>
		<p class="text-center conflictDisplay" style="color:red;display:none;"><i>Creates a Conflict</i></p>
		<div class="form-group">
			<button type="submit" class="btn btn-default">Apply</button>
		</div>
		<% if (updateFailed){ %>
		<p style="color:red"><i>Failed to apply changes.</i></p>
		<% } %>
	</Form>
		<div class="col-md-4">
			<% if (classlist != null && classlist.size() > 1){ %>
			<div class="container-fluid">
				<h4><u>Other classes in group</u></h4>
				<% for (Class1 clist: classlist) { 
						if (!clist.equals(c)){ %>
				<div class="container-fluid">
					<p><a href="<%= response.encodeURL(context.getContextPath()+"/Classes/Edit?classID=" + clist.getClassID()) %>"><%= clist.get(ournames.get("subject")) %> <%= clist.get(ournames.get("catalog")) %>-<%= clist.get(ournames.get("section")) %></a></p>
				</div>
				<hr role="seperator" class="divider">
				<% }} %>
			</div>
			<% } 
			Instructor instructor = null;
			if (c != null){ instructor = dbServices.getInstructorByName(semester, c.get(ournames.get("instructorfirst")), c.get(ournames.get("instructorlast"))); }
			if (instructor != null) {%>
			<div class="container-fluid">
			<h4><u>Instructor Notes</u></h4>
			<dl>
				<dt>Name</dt>
				<dd><%= instructor.getNameFirst() %> <%= instructor.getNameLast() %></dd>
				<dt>Board Preference<dt>
				<dd><%= instructor.getPrefBoard() %></dd>
				<dt>Chair Preference<dt>
				<dd><%= instructor.getPrefChair() %></dd>
				<dt>Desk Preference<dt>
				<dd><%= instructor.getPrefDesk() %></dd>
				<dt>Comment<dt>
				<dd><%= instructor.getComment() %></dd>
			</dl>
			</div>
			<% } %>
		</div>
	</div>
	
	<script>
	$(document).ready( function () {
	    
	    $('.conflictCheck').each(function(){
	    	$(this).blur(function(){
				var formData = $('#editForm').serializeArray();
	    		$.ajax({
	   				dataType:"json",
	   				url:"<%= response.encodeURL(context.getContextPath()+"/Classes/Check") %>",
	   			  	data: formData,
	   				success: function (json) {
						if (json.data == true){
							$('.conflictDisplay').show();
						} else {
							$('.conflictDisplay').hide();
						}
	   				},
	   				type: "POST"
	   			});
	    	});
	    });
	    
	    $('#applyall').blur(function(){
	    		if (!$('#applyall').prop("checked")){
	    			$('#groupnum').val("");
	    		} else if ($.trim($('#groupnum').val()) == ""){
	    			$('#groupnum').val(<%if(c!=null && c.getGroupNumber() != 0){ %><%= c.getGroupNumber() %><% } else { %><%= dbServices.getNextCombo(semester) %><% } %>);    			
	    		}
	    });
	    
	    $('#groupnum').blur(function(){
	    	if ($.trim($('#groupnum').val()) == ""){
				$('#applyall').prop("checked", false);    		
	    	} else {
	    		$('#applyall').prop("checked", true);
	    	}
	    });
	});
	</script>
	<% } else if (pathinfo.equalsIgnoreCase("Select")){
			Classlist classlist = dbServices.getClasses(semester, Integer.parseInt(request.getParameter("classID")));
			Class1 c = null;
			if (classlist != null && classlist.size() > 0){
				c = classlist.get(0);
			}
			Map<String, String> ournames = dbServices.getOurNames(semester);
	%>
	<div class="container-fluid">
		<h2 class="center-text">Select Classroom for <%= c.get(ournames.get("subject")) %> <%= c.get(ournames.get("catalog")) %>-<%= c.get(ournames.get("section")) %></h2>
		<div class="col-md-8">
			<table id="classroomTable" class="table table-striped table-bordered text-center">
			<thead>
				<tr>
				<th>Select</th>
				<th>Occupied</th>
				<th>Classroom</th>
		        <th>Capacity</th>
		        <th>Room Type</th>
		        <th>Board Type</th>
		        <th>Chair Type</th>
		        <th>Desk Type</th>
		        <th>Distance Learning</th>
		        <th>Projectors</th>
		        </tr>
			</thead>
			<tbody>
			<% 
			List<String> potentialTimeConflicts = conflictServices.findPotentialTimeConflicts(semester, c);
			for (Classroom room : dbServices.getClassrooms(semester)){ %>
				<tr>
				<td><form action='<%= response.encodeURL(context.getContextPath()+"/Classes/") %>' method='post' ><input type='hidden' name='RoomUpdate' value='RoomUpdate'><input type='hidden' name='classroom' value='<%= room.getRoomName() %>'><input type='hidden' name='classID' value='<%= c.getClassID() %>'><input type='submit' value='Select' alt='Select Class'/></form></td>
				<td><% if (!potentialTimeConflicts.contains(room.getRoomName())){ %>Available<% } %></td>
				<td><%= room.getRoomName() %></td>
				<td><%= room.getRoomCapacity() %></td>
				<td><%= room.getRoomType() %></td>
				<td><%= room.getRoomBoardType() %></td>
				<td><%= room.getRoomChairType() %></td>
				<td><%= room.getRoomDeskType() %></td>
				<td><%= room.getRoomDistLearning() %></td>
				<td><%= room.getRoomProjectors() %></td>
				</tr>
			<% } %>
			</tbody>
			</table>
		</div>
		<div class="col-md-4">
			<% if (classlist != null && classlist.size() > 1){ %>
			<div class="container-fluid">
				<h4><u>Other classes in group</u></h4>
				<% 
				for (Class1 clist: classlist) { 
					if (!clist.equals(c)){ %>
				<div class="container-fluid">
					<p><a href="<%= response.encodeURL(context.getContextPath()+"/Classes/Select?classID=" + clist.getClassID()) %>"><%= clist.get(ournames.get("subject")) %> <%= clist.get(ournames.get("catalog")) %>-<%= clist.get(ournames.get("section")) %></a></p>
				</div>
				<hr role="seperator" class="divider">
					<% } %>
				<% } %>
			</div>
			<% } 
			Instructor instructor = null;
			if (c != null){ instructor = dbServices.getInstructorByName(semester, c.get(ournames.get("instructorfirst")), c.get(ournames.get("instructorlast"))); }
			if (instructor != null){
			%>
			<div class="container-fluid">
			<h4><u>Instructor Notes</u></h4>
			<dl>
				<dt>Name</dt>
				<dd><%= instructor.getNameFirst() %> <%= instructor.getNameLast() %></dd>
				<dt>Board Preference<dt>
				<dd><%= instructor.getPrefBoard() %></dd>
				<dt>Chair Preference<dt>
				<dd><%= instructor.getPrefChair() %></dd>
				<dt>Desk Preference<dt>
				<dd><%= instructor.getPrefDesk() %></dd>
				<dt>Comment<dt>
				<dd><%= instructor.getComment() %></dd>
			</dl>
			</div>
			<% }
			if (c!=null) {%>
			<hr role="seperator" class="divider">
			<div class="container-fluid">
			<h4><u>Class Info</u></h4>
			<dl>
			<% for (String s : ournames.keySet()){ %>
				<div class="col-sm-6">
				<dt><%= s %></dt>
				<dd><%= c.get(ournames.get(s)) %></dd>
				</div>
				<% } %>
			</dl>
			</div>
			<% } %>
		</div>
	</div>
	<% } %>
<% } %>
		
<div class="container-fluid">
<%
if(semester.equals("summer")){%>
<h2 class="text-center">Summer Classes</h2>

<%} else if (semester.equals("spring")){%>
<h2 class="text-center">Spring Classes</h2>

<%} else {%>
<h2 class="text-center">Fall Classes</h2>
<%} %>
<br/><br/><br/><br/>
  <table id="classTable" class="table table-striped table-bordered">
    <thead>
      <tr>
        <% if (userlevel == User.USER_ADMIN) {%>
        <th>Select Class</th>
        <% } %>
        <th>Group</th>
        <% for (String s : Class1.OURNAMES){ %>
        <th><%= s %></th>
        <% } if (userlevel == User.USER_ADMIN) { %>
        <th>Edit Class</th>
        <th>Delete Class</th>
        <% } %>
      </tr>
    </thead>
    <tbody>
    </tbody>
  </table>
</div>

<% 	int val = 1; 
	if (userlevel == User.USER_ADMIN){
		val++;
	} 
%>
<script>
$(document).ready( function () {
    $('#classTable').DataTable({
        "lengthMenu": [[25, 50, 100, -1], [25, 50, 100, "All"]],
        "ajax" : {url : "<%= response.encodeURL(context.getContextPath()+"/Classes/") %>",
        	"data" : {getClassTable: "True"}},
        "columns": [
                    <% if (userlevel == User.USER_ADMIN) {%>
                    { "data": "Select_Room"},
					<% } %>
                    { "data": "Group_Number" },
                    <%for (String s : Class1.OURNAMES){%>
					{ "data": "<%= s %>"},
					<% } if (userlevel == User.USER_ADMIN) {%>
                    { "data": "Edit_Class" },
                    { "data": "Delete_Class" }
                    <% } %>
                ],
                "order": [[ <%= Class1.OURNAMES.indexOf("classroom") + val %>, "asc" ]]
    });
    
<% if(userlevel == User.USER_ADMIN){ %>
    $("#add > a").attr("href","<%= response.encodeURL(context.getContextPath()+"/Classes/Add") %>");
    $("#add > a").append(" Class");
    $("#add").show();
<% } %>
});
</script>
</body>
</html>
<% } %>