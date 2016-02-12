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
		out.append("<table class=\"table sortable\"><thead><tr><th>Class Number</th><th>Name</th><th>Subject</th><th>First Name</th><th>Last Name</th><th>Start Time</th><th>End Time</th><th>Start Date</th><th>End Date</th><th>Capacity</th><th>Enrolled</th><th>Edit Class</th></tr></thead><tbody>");
		for(Class1 c : items){
			
			out.append("<tr><td>" + c.getClassNbr() + "</td>");
			out.append("<td>" + c.getName() + "</td>");
			out.append("<td>" + c.getSubject() + "</td>");
			out.append("<td>" + c.getFName() + "</td>");
			out.append("<td>" + c.getLName() + "</td>");
			out.append("<td>" + c.getSTime() + "</td>");
			out.append("<td>" + c.getETime() + "</td>");
			out.append("<td>" + c.getSDate() + "</td>");
			out.append("<td>" + c.getEDate() + "</td>");
			out.append("<td>" + c.getCapacity() + "</td>");
			out.append("<td>" + c.getEnrolled() + "</td>");
		    out.append("<td><form action='viewClasses.jsp' method='post' ><input type='hidden' name='editClass' value='" + c.getClassID() + "'><input type='submit' value='Edit' alt='Edit Class'/></form></td>");
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
		
		out.append("<form method=\"POST\" action=\"viewClasses.jsp\">");
		out.append("<input type=\"hidden\" name=\"submitClassEdit\" value=\"submitClassEdit\">");
		out.append("<input type=\"hidden\" name=\"classID\" value=\"" + item.getClassID() + "\"");
		out.append("<div class=\"col-xs-3\"><label for=\"classNbr\">Class Number</label><input type=\"text\" class=\"form-control\" name=\"classNbr\" id=\"classNbr\" value='" + item.getClassNbr() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"class\">Class</label><input type=\"text\" class=\"form-control\" name=\"class\" id=\"class\" value='" + item.getName() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"subject\">Subject</label><input class=\"form-control\" name=\"subject\" id=\"subject\" value='" + item.getSubject() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"catalog\">Catalog</label><input class=\"form-control\" name=\"catalog\" id=\"catalog\" value='" + item.getCatalog() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"section\">Section</label><input class=\"form-control\" name=\"section\" id=\"section\" value='" + item.getSection() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"combo\">Combined</label><input class=\"form-control\" name=\"combo\" id=\"combo\" value='" + item.getCombo() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"Description\">Combined</label><input class=\"form-control\" name=\"description\" id=\"description\" value='" + item.getDescription() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"Academic Group\">Combined</label><input class=\"form-control\" name=\"acadGroup\" id=\"acadGroup\" value='" + item.getAcadGroup() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"Capacity\">Capacity</label><input class=\"form-control\" name=\"capacity\" id=\"capacity\" value='" + item.getCapacity() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"enrolled\">Enrolled</label><input class=\"form-control\" name=\"enrolled\" id=\"enrolled\" value='" + item.getEnrolled() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"day\">Days</label><input class=\"form-control\" name=\"day\" id=\"day\" value='" + item.getDay() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"sTime\">Start Time</label><input class=\"form-control\" name=\"sTime\" id=\"sTime\" value='" + item.getSTime() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"eTime\">End Time</label><input class=\"form-control\" name=\"eTime\" id=\"eTime\" value='" + item.getETime() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"sDate\">Start Date</label><input class=\"form-control\" name=\"sDate\" id=\"sDate\" value='" + item.getSDate() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"eDate\">End Date</label><input class=\"form-control\" name=\"eDate\" id=\"eDate\" value='" + item.getEDate() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"fName\">Teacher's First Name</label><input class=\"form-control\" name=\"fName\" id=\"fName\" value='" + item.getFName() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"lName\">Teacher's Last Name</label><input class=\"form-control\" name=\"lName\" id=\"lName\" value='" + item.getLName() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"facil\">Room</label><input class=\"form-control\" name=\"facil\" id=\"facil\" value='" + item.getFacil() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"location\">Location</label><input class=\"form-control\" name=\"location\" id=\"location\" value='" + item.getLocation() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"mode\">Mode</label><input class=\"form-control\" name=\"mode\" id=\"mode\" value='" + item.getMode() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"comp\">Comp</label><input class=\"form-control\" name=\"comp\" id=\"comp\" value='" + item.getComp() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"chairType\">Chair Type</label><input class=\"form-control\" name=\"chairType\" id=\"chairType\" value='" + item.getChairType() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"boardType\">Board Type</label><input class=\"form-control\" name=\"boardType\" id=\"boardType\" value='" + item.getBoardType() + "'/></div></br></br></br>");
		out.append("<div class=\"col-xs-3\"><label for=\"deskType\">Desk Type</label><input class=\"form-control\" name=\"deskType\" id=\"deskType\" value='" + item.getDeskType() + "'/></div></br></br></br>");
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
			
			out.append("<tr><td>" + u.getUsername() + "</td>");
			out.append("<td>" + u.getFName() + "</td>");
			out.append("<td>" + u.getLName() + "</td>");
			out.append("<td>" + u.getEmail() + "</td>");
			//out.append("<td>" + u.getUserid() + "</td>");

			if(u.getAdmin() == 1){
				out.append("<td> Yes </td>");
			}
			else{
				out.append("<td> No </td>");
			}
		    out.append("<td><form action='viewUsers.jsp' method='post' ><input type='hidden' name='delAccount' value='" + u.getUsername() + "'><input type='submit' value='Delete' alt='Delete Request' onclick=\"return confirm('Are you sure you want to delete this User?')\" /></form></td>");
			out.append("</tr>");		
		}
		out.append("</tbody></table>");
		stream.print(out.toString());
	}
	
	

	
}