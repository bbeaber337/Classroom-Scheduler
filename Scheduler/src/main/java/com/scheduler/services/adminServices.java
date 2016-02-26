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
	
	
	
// --------------------------------------------------------------------------------------------
//										CLASS FUNCTIONS
// --------------------------------------------------------------------------------------------
	public void deleteClass() throws Exception {

		int classID = 0;
		if (request.getParameter("deleteClass") != null) {
			// Set the ClassID to the class to be removed
			classID = Integer.parseInt(request.getParameter("deleteClass"));
			ms.deleteClass(classID);
		}
	}
	
	
	public void deleteClassroom() throws Exception {

		int roomID = 0;
		if (request.getParameter("deleteClassroom") != null) {
			// Set the ClassID to the class to be removed
			roomID = Integer.parseInt(request.getParameter("deleteClassroom"));
			ms.deleteClassroom(roomID);
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
	
	
	public boolean editClassroom() throws Exception {
		
		int classroomID = 0;
		
		if(request.getParameter("editClassroom") != null){
			//Need to convert getParameter to an integer
			classroomID = Integer.parseInt(request.getParameter("editClassroom"));
			hs.buildEditClassroom(classroomID);	
			return true;
		}
		return false;
	}
	
	
	public boolean selectClass() throws Exception {
		
		int classID = 0;
		
		if(request.getParameter("selectClass") != null){
			//Need to convert getParameter to an integer
			classID = Integer.parseInt(request.getParameter("selectClass"));
			hs.buildSelectClass(classID);	
			return true;
		}
		return false;
	}
	
	public void setDays(Class1 item) throws Exception {

		System.out.printf("\n\nMADE IT \n\n\n");

		//for (Class1 item : classList) {
			if (item != null) {
				//c.setClassID(item.getClassID());
				// parse through classDays attribute checking for M T W R F S

				if (item.getClassDays().contains("M") || item.getClassDays().contains("m")) {
					item.setClassMon(1);
				}
				if (item.getClassDays().contains("T") || item.getClassDays().contains("t")) {
					item.setClassTues(1);
				}
				if (item.getClassDays().contains("W") || item.getClassDays().contains("w")) {
					item.setClassWed(1);
				}
				if (item.getClassDays().contains("R") || item.getClassDays().contains("r")) {
					item.setClassThurs(1);
				}
				if (item.getClassDays().contains("F") || item.getClassDays().contains("f")) {
					item.setClassFri(1);
				}
				if (item.getClassDays().contains("S") | item.getClassDays().contains("s")) {
					item.setClassSat(1);
				}
				ms.updateClassDays(item);
			} else {
				System.out.printf("\n\nDIDN'T WORK!\n\n\n");
			}
		//}
	}
	
	
	public void submitClassEdit() throws Exception {
		
		if(request.getParameter("submitClassEdit") != null){

			
			Class1 c = new Class1();
			
			System.out.printf("\n\nClass ID: %d\n\n\n",Integer.parseInt(request.getParameter("classID")) );
			c.setClassID(Integer.parseInt(request.getParameter("classID")));
			c.setClassNumber(Integer.parseInt(request.getParameter("classNumber")));	
			c.setClassSubject(request.getParameter("classSubject"));
			c.setClassCatalog(request.getParameter("classCatalog"));
			c.setClassSection(request.getParameter("classSection"));
			c.setClassCombination(request.getParameter("classCombination"));
			c.setClassName(request.getParameter("className"));
			c.setClassDescription(request.getParameter("classDescription"));
			c.setClassAcadGroup(request.getParameter("classAcadGroup"));
			c.setClassCapacity(Integer.parseInt(request.getParameter("classCapacity")));
			c.setClassEnrolled(Integer.parseInt(request.getParameter("classEnrolled")));
			c.setClassDays(request.getParameter("classDays"));
			c.setClassTimeStart(request.getParameter("classTimeStart"));
			c.setClassTimeEnd(request.getParameter("classTimeEnd"));
			c.setClassInstructFirst(request.getParameter("classInstructFirst"));
			c.setClassInstructLast(request.getParameter("classInstructLast"));
			c.setClassDateStart(request.getParameter("classDateStart"));
			c.setClassDateEnd(request.getParameter("classDateEnd"));
			c.setClassRoom(request.getParameter("classRoom"));
			c.setClassCampus(request.getParameter("classCampus"));
			c.setClassMode(request.getParameter("classMode"));
			c.setClassComponent(request.getParameter("classComponent"));
			//c.setChairType(request.getParameter("chairType"));
			//c.setBoardType(request.getParameter("boardType"));
			//c.setDeskType(request.getParameter("deskType"));
			
			//c.setClassID(ms.updateClass(c));
			ms.updateClass(c);
			setDays(c);
		}
		
	}

	
	public void submitClassroomEdit() throws Exception {
		
		if(request.getParameter("submitClassroomEdit") != null){

			
			Classroom cr = new Classroom();
			
			System.out.printf("\n\nClassroom ID: %d\n\n\n",Integer.parseInt(request.getParameter("roomID")) );
			
			cr.setRoomID(Integer.parseInt(request.getParameter("roomID")));
			cr.setRoomCapacity(Integer.parseInt(request.getParameter("roomCapacity")));	
			cr.setRoomName(request.getParameter("roomName"));	
			cr.setRoomType(request.getParameter("roomType"));
			cr.setRoomChairType(request.getParameter("roomChairType"));
			cr.setRoomDeskType(request.getParameter("roomDeskType"));
			cr.setRoomBoardType(request.getParameter("roomBoardType"));
			cr.setRoomDistLearning(request.getParameter("roomDistLearning"));
			cr.setRoomProjectors(Integer.parseInt(request.getParameter("roomProjectors")));	
			
			ms.updateClassroom(cr);
		}
		
	}
	
	
// --------------------------------------------------------------------------------------------
// 										USER FUNCTIONS
// --------------------------------------------------------------------------------------------
	
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
	
	
	public void addAccount() throws Exception {
		
		
		if(request.getParameter("addAccount") != null){
			
			User u = new User();
			
			u.setUserName(request.getParameter("userName"));
			u.setUserPassword(request.getParameter("userPassword"));
			u.setUserFirst(request.getParameter("userFirst"));
			u.setUserLast(request.getParameter("userLast"));
			u.setUserEmail(request.getParameter("userEmail"));
			System.out.printf("Value of ADMIN  is: %s", request.getParameter("UserAdmin"));
			if(request.getParameter("userAdmin").equals("Yes") || request.getParameter("userAdmin").equals("yes")){
				u.setUserAdmin(1);
			}else{
				u.setUserAdmin(0);
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

	
	public void delAccount() throws Exception {
		
		String username;
		
		if(request.getParameter("delAccount") != null){
						
			username = request.getParameter("delAccount");
			ms.deleteAccount(username);
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
	
	
	public void directLogin() throws Exception {
		
		String user = null;
		String pass = null;
		
		if(request.getParameter("userLogin") != null){
			 user = request.getParameter("userName");
			 pass = request.getParameter("userPassword");	
			 
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
