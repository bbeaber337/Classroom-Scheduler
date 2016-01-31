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