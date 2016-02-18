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
	
	
	public void buildClasses() throws Exception {
		List<Class1> items = ms.getClasses();
		
		StringBuilder out = new StringBuilder();
		
		//out.append("<table>");
		//out.append("<tr><th>Users</th></tr>");
		out.append("<table class=\"table sortable\"><thead><tr><th>Class Number</th><th>Name</th><th>Subject</th><th>First Name</th><th>Last Name</th><th>Start Time</th><th>End Time</th><th>Start Date</th><th>End Date</th><th>Capacity</th><th>Enrolled</th><th>Edit Class</th><th>Delete Class</th></tr></thead><tbody>");
		for(Class1 c : items){
			
			out.append("<tr><td>" + c.getClassNumber() + "</td>");
			out.append("<td>" + c.getClassName() + "</td>");
			out.append("<td>" + c.getClassSubject() + "</td>");
			out.append("<td>" + c.getClassInstructFirst() + "</td>");
			out.append("<td>" + c.getClassInstructLast() + "</td>");
			out.append("<td>" + c.getClassTimeStart() + "</td>");
			out.append("<td>" + c.getClassTimeEnd() + "</td>");
			out.append("<td>" + c.getClassDateStart() + "</td>");
			out.append("<td>" + c.getClassDateEnd() + "</td>");
			out.append("<td>" + c.getClassCapacity() + "</td>");
			out.append("<td>" + c.getClassEnrolled() + "</td>");
		    out.append("<td><form action='viewClasses.jsp' method='post' ><input type='hidden' name='editClass' value='" + c.getClassID() + "'><input type='submit' value='Edit' alt='Edit Class'/></form></td>");
		    out.append("<td><form action='viewClasses.jsp' method='post' ><input type='hidden' name='deleteClass' value='" + c.getClassID() + "'><input type='submit' value='Delete' alt='Delete Class' onclick=\"return confirm('Are you sure you want to delete this Class?')\"/></form></td>");
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
	
	
	public void buildEditClass(int classID) throws Exception {
		Class1 item = new Class1();
		System.out.printf("\n\nClass ID to pull: %d\n\n", classID);
		
		item = ms.getClassFromID(classID);
		
		StringBuilder out = new StringBuilder();

		System.out.printf("\n\nClass ID to pull: %d\n\n", item.getClassID());
		
		out.append("</br></br></br></br><h2 class=\"text-center\">Edit Class</h2></br></br>");
		out.append("<form method=\"POST\" action=\"viewClasses.jsp\">");
		out.append("<input type=\"hidden\" name=\"submitClassEdit\" value=\"submitClassEdit\">");
		out.append("<input type=\"hidden\" name=\"classID\" value=\"" + item.getClassID() + "\"</br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classNumber\">Class Number</label><input type=\"text\" class=\"form-control\" name=\"classNumber\" id=\"classNumber\" value='" + item.getClassNumber() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"className\">Class</label><input type=\"text\" class=\"form-control\" name=\"className\" id=\"className\" value='" + item.getClassName() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classSubject\">Subject</label><input class=\"form-control\" name=\"classSubject\" id=\"classSubject\" value='" + item.getClassSubject() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classCatalog\">Catalog</label><input class=\"form-control\" name=\"classCatalog\" id=\"classCatalog\" value='" + item.getClassCatalog() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classSection\">Section</label><input class=\"form-control\" name=\"classSection\" id=\"classSection\" value='" + item.getClassSection() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classCombination\">Combined</label><input class=\"form-control\" name=\"classCombination\" id=\"classCombination\" value='" + item.getClassCombination() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classDescription\">Description</label><input class=\"form-control\" name=\"classDescription\" id=\"classDescription\" value='" + item.getClassDescription() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classAcadGroup\">Academic Group</label><input class=\"form-control\" name=\"classAcadGroup\" id=\"classAcadGroup\" value='" + item.getClassAcadGroup() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classCapacity\">Capacity</label><input class=\"form-control\" name=\"classCapacity\" id=\"classCapacity\" value='" + item.getClassCapacity() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classEnrolled\">Enrolled</label><input class=\"form-control\" name=\"classEnrolled\" id=\"classEnrolled\" value='" + item.getClassEnrolled() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classDays\">Days</label><input class=\"form-control\" name=\"classDays\" id=\"classDays\" value='" + item.getClassDays() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classTimeStart\">Start Time</label><input class=\"form-control\" name=\"classTimeStart\" id=\"classTimeStart\" value='" + item.getClassTimeStart() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classTimeEnd\">End Time</label><input class=\"form-control\" name=\"classTimeEnd\" id=\"classTimeEnd\" value='" + item.getClassTimeEnd() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classDateStart\">Start Date</label><input class=\"form-control\" name=\"classDateStart\" id=\"classDateStart\" value='" + item.getClassDateStart() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classDateEnd\">End Date</label><input class=\"form-control\" name=\"classDateEnd\" id=\"classDateEnd\" value='" + item.getClassDateEnd() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classInstructFirst\">Teacher's First Name</label><input class=\"form-control\" name=\"classInstructFirst\" id=\"classInstructFirst\" value='" + item.getClassInstructFirst() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classInstructLast\">Teacher's Last Name</label><input class=\"form-control\" name=\"classInstructLast\" id=\"classInstructLast\" value='" + item.getClassInstructLast() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classRoom\">Room</label><input class=\"form-control\" name=\"classRoom\" id=\"classRoom\" value='" + item.getClassRoom() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classCampus\">Location</label><input class=\"form-control\" name=\"classCampus\" id=\"classCampus\" value='" + item.getClassCampus() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classMode\">Mode</label><input class=\"form-control\" name=\"classMode\" id=\"classMode\" value='" + item.getClassMode() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"classComponent\">Comp</label><input class=\"form-control\" name=\"classComponent\" id=\"classComponent\" value='" + item.getClassComponent() + "'/></div></br></br></br>");
		//out.append("<div class=\"col-xs-3\"><label for=\"chairType\">Chair Type</label><input class=\"form-control\" name=\"chairType\" id=\"chairType\" value='" + item.getChairType() + "'/></div></br></br></br>");
		//out.append("<div class=\"col-xs-3\"><label for=\"boardType\">Board Type</label><input class=\"form-control\" name=\"boardType\" id=\"boardType\" value='" + item.getBoardType() + "'/></div></br></br></br>");
		//out.append("<div class=\"col-xs-3\"><label for=\"deskType\">Desk Type</label><input class=\"form-control\" name=\"deskType\" id=\"deskType\" value='" + item.getDeskType() + "'/></div></br></br></br></br></br>");
		out.append("<div class=\"row-md-5\"><button type=\"submit\" class=\"btn btn-default\"></t>Save Changes</button></div></form>");
		
		stream.print(out.toString());

	}
	
	
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
	
	

	
}