package com.scheduler.services;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.*;
import javax.servlet.jsp.JspWriter;

import com.scheduler.services.*;
import com.scheduler.valueObjects.*;
import com.scheduler.jsp.*;
import com.scheduler.dbconnector.*;



public class adminServices extends baseJSP {
	
	private dbConnector conn = null;
	private MyServices ms = null;
	private HTMLServices hs = null;
	private Object adminKey = "abcWST6kks76bE73MmAA72Z3abc";
	private Object userKey = "abcWST6kks76bE73MmAA72Z3";

	
	public adminServices(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, JspWriter stream) throws Exception {
		super(session, request, response, stream);
		ms = new MyServices();
		hs = new HTMLServices(session, request, response, stream);
	    conn = new dbConnector();



	}
	
	
	public void adminLogin() {
		System.out.printf("Setting User KEY: %s\n\n", adminKey);
		session.setAttribute("userLogin", adminKey);
		System.out.printf("Setting User KEY: %s\n\n", session.getAttribute("userLogin"));
	}
	public void userLogin() {
		session.setAttribute("userLogin", userKey);
	}
	
	public void logout() {
		if(request.getParameter("logout") != null){
			session.setAttribute("userLogin", null);
		}
	}
//	public void userLogout() {
//		if(request.getParameter("logout") != null){
//			session.setAttribute("userLogin", null);
//		}
//	}
	
	

	
	public void addAccount() throws Exception {
		
		
		if(request.getParameter("addAccount") != null){
			
			User u = new User();
			
			u.setUsername(request.getParameter("username"));
			u.setPass(request.getParameter("pass"));
			u.setFName(request.getParameter("fName"));
			u.setLName(request.getParameter("lName"));
			u.setEmail(request.getParameter("email"));
			System.out.printf("Value of ADMIN  is: %s", request.getParameter("admin"));
			if(request.getParameter("admin").equals("Yes") || request.getParameter("admin").equals("yes")){
				u.setAdmin(1);
			}else{
				u.setAdmin(0);
			}
			ms.addAccount(u);
		}
	}
	
	public void addAccRequest() throws Exception {
		
		
		if(request.getParameter("accountRequest") != null){
			
			AccRequest ar = new AccRequest();
			
			System.out.printf("Value of FIRST NAME is: %s", request.getParameter("fName"));
			ar.setFName(request.getParameter("fName"));
			ar.setLName(request.getParameter("lName"));
			ar.setUsername(request.getParameter("username"));
			ar.setPass(request.getParameter("pass"));
			ar.setEmail(request.getParameter("email"));
			ar.setReasoning(request.getParameter("reasoning"));
			ms.insertAccRequest(ar);
		}
	}
	

	
	public void delAccRequest() throws Exception {
		
		String user = null;
		//int id;
		
		if(request.getParameter("delAccRequest") != null){
						
			user = request.getParameter("delAccRequest");
			 //username = request.getParameter("delAccRequest");
			ms.deleteAccRequest(user);
		}
	}
	
	public void delAccount() throws Exception {
		
		String username;
		
		if(request.getParameter("delAccount") != null){
			
			
			username = request.getParameter("delAccount");
			ms.deleteAccount(username);
		}
		
	}
	
	public void directLogin() throws Exception {
		
		String user = null;
		String pass = null;
		
		if(request.getParameter("userLogin") != null){
			 user = request.getParameter("username");
			 pass = request.getParameter("pass");	
			 
			if(ms.validateLogin(user, pass)){
				if(ms.adminStatus(user)){
					adminLogin();
					redirect("/Scheduler/Administrator/AdminHomepage.jsp");
				}else{
					userLogin();
					redirect("UserHomepage.jsp");
				}
			}else{
				redirect("LoginError.jsp");
			}
		}			
	}
	
	public boolean editClass() throws Exception {
		
		int classID = 0;
		
		if(request.getParameter("editClass") != null){
			//Need to convert getParameter to an integer
			classID = Integer.parseInt(request.getParameter("editClass"));
			hs.buildEditClass(classID);
			
			return true;
		}
		return false;
	}
	
	public void submitClassEdit() throws Exception {
		
		if(request.getParameter("submitClassEdit") != null){

			Class1 c = new Class1();
			
			System.out.printf("\n\nClass ID: %d\n\n\n",Integer.parseInt(request.getParameter("classID")) );
			c.setClassID(Integer.parseInt(request.getParameter("classID")));
			c.setClassNbr(Integer.parseInt(request.getParameter("classNbr")));	
			c.setSubject(request.getParameter("subject"));
			c.setCatalog(request.getParameter("catalog"));
			c.setSection(request.getParameter("section"));
			c.setCombo(request.getParameter("combo"));
			c.setName(request.getParameter("name"));
			c.setDescription(request.getParameter("description"));
			c.setAcadGroup(request.getParameter("acadGroup"));
			c.setCapacity(Integer.parseInt(request.getParameter("capacity")));
			c.setEnrolled(Integer.parseInt(request.getParameter("enrolled")));
			c.setDay(request.getParameter("day"));
			c.setSTime(request.getParameter("sTime"));
			c.setETime(request.getParameter("eTime"));
			c.setFName(request.getParameter("fName"));
			c.setLName(request.getParameter("lName"));
			c.setSDate(request.getParameter("sDate"));
			c.setEDate(request.getParameter("eDate"));
			c.setFacil(request.getParameter("facil"));
			c.setLocation(request.getParameter("location"));
			c.setMode(request.getParameter("mode"));
			c.setComp(request.getParameter("comp"));
			c.setChairType(request.getParameter("chairType"));
			c.setBoardType(request.getParameter("boardType"));
			c.setDeskType(request.getParameter("deskType"));
			
			//c.setClassID(ms.updateClass(c));
			System.out.printf("\n\nClass ID: %d\n\n\n",ms.updateClass(c));
		}
		
	}
	
	public boolean invalidAdmin() {
		if (session.getAttribute("userLogin") == null){
			//Redirect user
			return true;
		}
		if (session.getAttribute("userLogin").equals(adminKey)) {
			//Valid User, Continue to page
			return false;
		}
		return true;
	}
	
	public boolean invalidUser() {
		if (session.getAttribute("userLogin") == null){
			//Redirect user
			return true;
		}
		if (session.getAttribute("userLogin").equals(userKey)) {
			//Valid User, Continue to page
			return false;
		}
		return true;
	}
	

	
	


	

}
