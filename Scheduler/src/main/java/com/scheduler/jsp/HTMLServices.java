package com.scheduler.jsp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import com.scheduler.services.*;
import com.scheduler.valueObjects.*;


public class HTMLServices extends baseJSP {
	
	public HTMLServices(HttpSession session, HttpServletRequest request, HttpServletResponse response, JspWriter stream) throws Exception {
		super(session, request, response, stream);
		ms = new MyServices();
	}
	
	private MyServices ms = null;
	
// --------------------------------------------------------------------------------------------
//									CLASS FUNCTIONS
//--------------------------------------------------------------------------------------------
	
	public void buildSelectClass(int classID) throws Exception {
		String hasRoom = null;
		Class1 item = new Class1();
		System.out.printf("\n\nClass ID to pull: %d\n\n", classID);
		
		item = ms.getClassFromID(classID);
		
		//IF the string does not have more then 1 char we can assume it is not assigned a room (TODO: add logic to check if the room it is assigned is really a valid room by checking database)
		//Checking is room is NULL does NOT work
		if(item.getClassRoom().length() < 2){
			System.out.println("Room is NULL\n\n");
			hasRoom = "Not Assinged";
		} else {
			System.out.println("Room is NOT NULL\n\n");
			hasRoom = item.getClassRoom();
		}
		
		StringBuilder out = new StringBuilder();
		System.out.printf("\n\nClass ID to pull: %d\n\n", item.getClassID());
		
		out.append("</br></br></br></br><h2 class=\"text-center\"><u>Current Room</u></h2></br></br>");
		out.append("</br><h3 class=\"text-center\">" + hasRoom + "</h3></br></br></br>");
		out.append("</br></br></br><h2 class=\"text-center\"><u>Available Rooms</u></h2>");
			
		stream.print(out.toString());
	}
	
	//Build Classes
	public void buildClasses() throws Exception {
		String hasRoom;
		String combo;
		List<Class1> items = ms.getClasses();
			
		StringBuilder out = new StringBuilder();
		
		//out.append("<table>");
		//out.append("<tr><th>Users</th></tr>");
		out.append("<table class=\"table sortable\"><thead><tr><th>Change Room</th><th>Class Number</th><th>Name</th><th>Room</th><th>Subject</th><th>First Name</th><th>Last Name</th><th>Days</th><th>Start Time</th><th>End Time</th><th>Start Date</th><th>End Date</th><th>Capacity</th><th>Enrolled</th><th>Catalog</th><th>Section</th><th>Description</th><th>Campus</th><th>Academic Group</th><th>Mode</th><th>Combined</th><th>Edit Class</th><th>Delete Class</th></tr></thead><tbody>");
		for(Class1 c : items){
			
			//IF the string does not have more then 1 char we can assume it is not assigned a room (TODO: add logic to check if the room it is assigned is really a valid room by checking database)
			if(c.getClassRoom().length() < 2){
				hasRoom = "Not Assinged";
			} else {
				hasRoom = c.getClassRoom();
			}
			//Check if it combined or not
			//compareToIgnoreCase() returns 0 if true
			if(c.getClassCombination().compareToIgnoreCase("c") == 0){
				combo = "Yes";
			} else {
				combo = "No";
			}			
			out.append("<tr><td><form action='viewClasses.jsp' method='post' ><input type='hidden' name='selectClass' value='" + c.getClassID() + "'><input type='submit' value='Select' alt='Select Class'/></form></td>");
			out.append("<td>" + c.getClassNumber() + "</td>");
			out.append("<td>" + c.getClassName() + "</td>");
			out.append("<td class=\"active\">" + hasRoom + "</td>");
			out.append("<td>" + c.getClassSubject() + "</td>");
			out.append("<td>" + c.getClassInstructFirst() + "</td>");
			out.append("<td>" + c.getClassInstructLast() + "</td>");
			out.append("<td>" + c.getClassDays() + "</td>");
			out.append("<td>" + c.getClassTimeStart() + "</td>");
			out.append("<td>" + c.getClassTimeEnd() + "</td>");
			out.append("<td>" + c.getClassDateStart() + "</td>");
			out.append("<td>" + c.getClassDateEnd() + "</td>");
			out.append("<td>" + c.getClassCapacity() + "</td>");
			out.append("<td>" + c.getClassEnrolled() + "</td>");		
			out.append("<td>" + c.getClassCatalog() + "</td>");
			out.append("<td>" + c.getClassSection() + "</td>");
			out.append("<td>" + c.getClassDescription() + "</td>");
			out.append("<td>" + c.getClassCampus() + "</td>");
			out.append("<td>" + c.getClassAcadGroup() + "</td>");
			out.append("<td>" + c.getClassMode() + "</td>");
			out.append("<td>" + combo + "</td>");
		    out.append("<td><form action='viewClasses.jsp' method='post' ><input type='hidden' name='editClass' value='" + c.getClassID() + "'><input type='submit' value='Edit' alt='Edit Class'/></form></td>");
		    out.append("<td><form action='viewClasses.jsp' method='post' ><input type='hidden' name='deleteClass' value='" + c.getClassID() + "'><input type='submit' value='Delete' alt='Delete Class' onclick=\"return confirm('Are you sure you want to delete this Class?')\"/></form></td>");
			out.append("</tr>");
			System.out.printf("\nMon: %d\nTues: %d\nWed: %d\nThurs: %d\nFri: %d\nSat: %d\n\n", c.getClassMon(), c.getClassTues(), c.getClassWed(), c.getClassThurs(), c.getClassFri(), c.getClassSat());
		}
		out.append("</tbody></table>");
		stream.print(out.toString());
	}
	
	
	//Build Classrooms
	public void buildClassrooms() throws Exception {

		String typeTemp;
		String chairTemp;
		String deskTemp;
		String boardTemp;
		String dlTemp;
		
		List<Classroom> items = ms.getClassrooms();
			
		StringBuilder out = new StringBuilder();
		
		out.append("<table class=\"table sortable\"><thead><tr><th>Select</th><th>Room</th><th>Capacity</th><th>Type</th><th>Chair Type</th><th>Desk Type</th><th>Board Type</th><th>Distance Learning</th><th>Number of Projectors</th><th>Edit</th><th>Delete</th></tr></thead><tbody>");
		for(Classroom cr : items){
		
			if(cr.getRoomType() == null){
				typeTemp = "Unassigned";
			} else {
				typeTemp  = cr.getRoomType();
			}
			if(cr.getRoomChairType() == null){
				chairTemp = "Unassigned";
			} else {
				chairTemp  = cr.getRoomChairType();
			}
			if(cr.getRoomDeskType() == null){
				deskTemp = "Unassigned";
			} else {
				deskTemp  = cr.getRoomDeskType();
			}
			if(cr.getRoomBoardType() == null){
				boardTemp = "Unassigned";
			} else {
				boardTemp  = cr.getRoomBoardType();
			}
			if(cr.getRoomDistLearning() == null){
				dlTemp = "Unassigned";
			} else {
				dlTemp  = cr.getRoomDistLearning();
			}

			
			out.append("<tr><td><form action='viewClassrooms.jsp' method='post' ><input type='hidden' name='selectClassroom' value='" + cr.getRoomID() + "'><input type='submit' value='Select' alt='Select Classroom'/></form></td>");
			out.append("<td>" + cr.getRoomName() + "</td>");
			out.append("<td>" + cr.getRoomCapacity() + "</td>");
			out.append("<td>" + typeTemp + "</td>");
			out.append("<td>" + chairTemp + "</td>");
			out.append("<td>" + deskTemp + "</td>");
			out.append("<td>" + boardTemp + "</td>");
			out.append("<td>" + dlTemp + "</td>");
			out.append("<td>" + cr.getRoomProjectors() + "</td>");
		    out.append("<td><form action='viewClassrooms.jsp' method='post' ><input type='hidden' name='editClassroom' value='" + cr.getRoomID() + "'><input type='submit' value='Edit' alt='Edit Classroom'/></form></td>");
		    out.append("<td><form action='viewClassrooms.jsp' method='post' ><input type='hidden' name='deleteClassroom' value='" + cr.getRoomID() + "'><input type='submit' value='Delete' alt='Delete Classroom' onclick=\"return confirm('Are you sure you want to delete this Classroom?')\"/></form></td>");
			out.append("</tr>");
		}
		out.append("</tbody></table>");
		stream.print(out.toString());
	}
	
	
	public void buildEditClass(int classID) throws Exception {
		Class1 item = new Class1();
		String combo;
		String hasRoom = null;
		System.out.printf("\n\nClass ID to pull: %d\n\n", classID);
		
		item = ms.getClassFromID(classID);
		
		StringBuilder out = new StringBuilder();
		
		System.out.printf("\n\nClass ID to pull: %d\n\n", item.getClassID());
		
		if(item.getClassCombination().compareToIgnoreCase("c") == 0){
			combo = "Yes";
		} else {
			combo = "No";
		}
		
		out.append("</br></br></br></br><h2 class=\"text-center\">Edit Class</h2></br></br>");
		out.append("<form method=\"POST\" action=\"viewClasses.jsp\">");
		out.append("<input type=\"hidden\" name=\"submitClassEdit\" value=\"submitClassEdit\">");
		out.append("<input type=\"hidden\" name=\"classID\" value=\"" + item.getClassID() + "\"</br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classNumber\">Class Number</label><input type=\"text\" class=\"form-control\" name=\"classNumber\" id=\"classNumber\" value='" + item.getClassNumber() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"className\">Class</label><input type=\"text\" class=\"form-control\" name=\"className\" id=\"className\" value='" + item.getClassName() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classRoom\">Room</label><input class=\"form-control\" name=\"classRoom\" id=\"classRoom\" value='" + item.getClassRoom() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classSubject\">Subject</label><input class=\"form-control\" name=\"classSubject\" id=\"classSubject\" value='" + item.getClassSubject() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classCatalog\">Catalog</label><input class=\"form-control\" name=\"classCatalog\" id=\"classCatalog\" value='" + item.getClassCatalog() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classSection\">Section</label><input class=\"form-control\" name=\"classSection\" id=\"classSection\" value='" + item.getClassSection() + "'/></div></br></br></br>");
		out.append("</br><div class=\"col-xs-3\"><label for=\"classCombination\">Combined</label></br><select name=\"classCombination\"><option selected value='" + item.getClassCombination() + "'>" + combo + "</option><option value=''>No</option><option value='C'>Yes</option></select></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classDescription\">Description</label><input class=\"form-control\" name=\"classDescription\" id=\"classDescription\" value='" + item.getClassDescription() + "'/></div></br></br></br>");
		out.append("</br><div class=\"col-xs-3\"><label for=\"classAcadGroup\">Academic Group</label></br><select name=\"classAcadGroup\"><option selected value='" + item.getClassAcadGroup() + "'>" + item.getClassAcadGroup() + "</option><option value='OMEN'>OMEN</option><option value='OMIS'>OMIS</option><option value='OMAS'>OMAS</option></select></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classCapacity\">Capacity</label><input class=\"form-control\" name=\"classCapacity\" id=\"classCapacity\" value='" + item.getClassCapacity() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classEnrolled\">Enrolled</label><input class=\"form-control\" name=\"classEnrolled\" id=\"classEnrolled\" value='" + item.getClassEnrolled() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classDays\">Days</label><input class=\"form-control\" name=\"classDays\" id=\"classDays\" value='" + item.getClassDays() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classTimeStart\">Start Time</label><input class=\"form-control\" name=\"classTimeStart\" id=\"classTimeStart\" value='" + item.getClassTimeStart() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classTimeEnd\">End Time</label><input class=\"form-control\" name=\"classTimeEnd\" id=\"classTimeEnd\" value='" + item.getClassTimeEnd() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classDateStart\">Start Date</label><input class=\"form-control\" name=\"classDateStart\" id=\"classDateStart\" value='" + item.getClassDateStart() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classDateEnd\">End Date</label><input class=\"form-control\" name=\"classDateEnd\" id=\"classDateEnd\" value='" + item.getClassDateEnd() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classInstructFirst\">Teacher's First Name</label><input class=\"form-control\" name=\"classInstructFirst\" id=\"classInstructFirst\" value='" + item.getClassInstructFirst() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classInstructLast\">Teacher's Last Name</label><input class=\"form-control\" name=\"classInstructLast\" id=\"classInstructLast\" value='" + item.getClassInstructLast() + "'/></div></br></br></br>");
		out.append("</br><div class=\"col-xs-3\"><label for=\"classCampus\">Campus</label></br><select name=\"classCampus\"><option selected value='" + item.getClassCampus() + "'>" + item.getClassCampus() + "</option><option value='PACIFIC'>PACIFIC</option><option value='DODGE'>DODGE</option></select></div></br></br>");
		out.append("</br><div class=\"col-xs-3\"><label for=\"classMode\">Mode</label></br><select name=\"classMode\"><option selected value='" + item.getClassMode() + "'>" + item.getClassMode() + "</option><option value='P'>P</option><option value='DE'>DE</option></select></div></br></br></br>");
		//out.append("<div class=\"col-xs-3\"><label for=\"classMode\">Mode</label><input class=\"form-control\" name=\"classMode\" id=\"classMode\" value='" + item.getClassMode() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classComponent\">Component</label><input class=\"form-control\" name=\"classComponent\" id=\"classComponent\" value='" + item.getClassComponent() + "'/></div></br></br></br>");
		//out.append("<div class=\"col-xs-3\"><label for=\"chairType\">Chair Type</label><input class=\"form-control\" name=\"chairType\" id=\"chairType\" value='" + item.getChairType() + "'/></div></br></br></br>");
		//out.append("<div class=\"col-xs-3\"><label for=\"boardType\">Board Type</label><input class=\"form-control\" name=\"boardType\" id=\"boardType\" value='" + item.getBoardType() + "'/></div></br></br></br>");
		//out.append("<div class=\"col-xs-3\"><label for=\"deskType\">Desk Type</label><input class=\"form-control\" name=\"deskType\" id=\"deskType\" value='" + item.getDeskType() + "'/></div></br></br></br></br></br>");
		out.append("</br><div class=\"row-md-5\"><button type=\"submit\" class=\"btn btn-default\"></t>Save Changes</button></div></form>");
		
		stream.print(out.toString());
	}
	
	
	public void buildEditClassroom(int classroomID) throws Exception {
		Classroom item = new Classroom();
		
		item = ms.getClassroomFromID(classroomID);
		
		StringBuilder out = new StringBuilder();
		
		System.out.printf("\n\nClassroom ID to pull: %d\n\n", item.getRoomID());
		
		
		out.append("</br></br></br></br><h2 class=\"text-center\">Edit Class</h2></br></br>");
		out.append("<form method=\"POST\" action=\"viewClassrooms.jsp\">");
		out.append("<input type=\"hidden\" name=\"submitClassroomEdit\" value=\"submitClassroomEdit\">");
		out.append("<input type=\"hidden\" name=\"roomID\" value=\"" + item.getRoomID() + "\"</br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"roomName\">Room</label><input class=\"form-control\" name=\"roomName\" id=\"roomName\" value='" + item.getRoomName() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"roomCapacity\">Capacity</label><input type=\"text\" class=\"form-control\" name=\"roomCapacity\" id=\"roomCapacity\" value='" + item.getRoomCapacity() + "'/></div></br></br></br>");
		out.append("</br><div class=\"col-xs-3\"><label for=\"roomDeskType\">Desk Type</label></br><select name=\"roomDeskType\"><option selected value='" + item.getRoomDeskType() + "'>" + item.getRoomDeskType() + "</option><option value='Desks'>Desks</option><option value='Tables'>Tables</option><option value='Lab'>Lab</option><option value='Any'>Any</option></select></div></br></br></br>");
		out.append("</br><div class=\"col-xs-3\"><label for=\"roomBoardType\">Board Type</label></br><select name=\"roomBoardType\"><option selected value='" + item.getRoomBoardType() + "'>" + item.getRoomBoardType() + "</option><option value='Whiteboard'>Whiteboard</option><option value='S'>S</option><option value='Any'>Any</option></select></div></br></br></br>");
		out.append("</br><div class=\"col-xs-3\"><label for=\"roomChairType\">Chair Type</label></br><select name=\"roomChairType\"><option selected value='" + item.getRoomChairType() + "'>" + item.getRoomChairType() + "</option><option value='Soft Seats'>Soft Seats</option><option value='Hard Seats'>Hard Seats</option><option value='Any'>Any</option></select></div></br></br></br>");
		out.append("</br><div class=\"col-xs-3\"><label for=\"roomType\">Room Type</label></br><select name=\"roomType\"><option selected value='" + item.getRoomType() + "'>" + item.getRoomType() + "</option><option value='Lecture'>Lecture</option><option value='Lab'>Lab</option><option value='Any'>Any</option></select></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"roomDistLearning\">Distance Learning</label><input class=\"form-control\" name=\"roomDistLearning\" id=\"roomDistLearning\" value='" + item.getRoomDistLearning() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"roomProjectors\">Projectors</label><input class=\"form-control\" name=\"roomProjectors\" id=\"roomProjectors\" value='" + item.getRoomProjectors() + "'/></div></br></br></br>");
		out.append("</br><div class=\"row-md-5\"><button type=\"submit\" class=\"btn btn-default\"></t>Save Changes</button></div></form>");
		
		stream.print(out.toString());
	}
	
	

// --------------------------------------------------------------------------------------------
//									USER FUNCTIONS
//--------------------------------------------------------------------------------------------
	
	public void buildUsers() throws Exception {
		List<User> items = ms.getUsers();
		
		StringBuilder out = new StringBuilder();
		
		//out.append("<table>");
		//out.append("<tr><th>Users</th></tr>");
		out.append("<table class=\"table\"><thead><tr><th> Username </th><th>First Name</th><th>Last Name</th><th>email</th><th>Admin</th><th>Delete Account</th></tr></thead><tbody>");
		for(User u : items){
			
			out.append("<tr><td>" + u.getUserName() + "</td>");
			out.append("<td>" + u.getUserFirst() + "</td>");
			out.append("<td>" + u.getUserLast() + "</td>");
			out.append("<td>" + u.getUserEmail() + "</td>");
			//out.append("<td>" + u.getUserid() + "</td>");

			if(u.getUserAdmin() == 1){
				out.append("<td> Yes </td>");
			}
			else{
				out.append("<td> No </td>");
			}
		    out.append("<td><form action='viewUsers.jsp' method='post' ><input type='hidden' name='delAccount' value='" + u.getUserName() + "'><input type='submit' value='Delete' alt='Delete Request' onclick=\"return confirm('Are you sure you want to delete this User?')\" /></form></td>");
			out.append("</tr>");		
		}
		out.append("</tbody></table>");
		stream.print(out.toString());
	}
	
	
	public void buildAccRequests() throws Exception {
		List<AccRequest> items = ms.getAccRequests();
		
		StringBuilder out = new StringBuilder();
		
		//out.append("<table>");
		//out.append("<tr><th>Users</th></tr>");
		out.append(" ");
		out.append("<table class=\"table\"><thead><tr><th>First Name</th><th>Last Name</th><th>Desired Username </th><th>Password</th><th>email</th><th>Reasoning</th></tr></thead><tbody>");
		for(AccRequest ar : items){
			//out.append("<form role=\"form\" action='viewRequests.jsp' method='post'><input type=\"hidden\" name=\"delAccRequest\" value=\"accountRequest\"><tr><td>" + ar.getUsername() + "</td>");
			out.append("<td>" + ar.getLName() + "</td>");
			out.append("<td>" + ar.getFName() + "</td>");
			out.append("<td>" + ar.getEmail() + "</td>");
			out.append("<td>" + ar.getPass() + "</td>");
			out.append("<td name=\"username\">" + ar.getUsername() + "</td>");
			out.append("<td>" + ar.getReasoning() + "</td>");
			
		    out.append("<td><form action='viewRequests.jsp' method='post' ><input type='hidden' name='delAccRequest' value='" + ar.getUsername() + "'><input type='submit' value='Delete' alt='Delete Request' /></form></td>");
			//out.append("<td><button class=\"btn btn-default\"  name=\"delAccRequest\" type=\"submit\" > Delete</button></form></td> ");

			out.append("</tr>");
		}
		out.append("</tbody></table>");
		//out.append("</form>");
		stream.print(out.toString());
	}

	
}