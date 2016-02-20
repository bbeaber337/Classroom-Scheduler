package com.scheduler.services;

import java.util.*;
import java.sql.*;
import java.io.*;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import com.scheduler.dbconnector.*;
import com.scheduler.valueObjects.*;
import com.scheduler.jsp.*;


public class MyServices {

	private dbConnector conn = null;
	
	public MyServices(){
		conn = new dbConnector();
	}
	
	
	public int addAccount(User set){
		
		String query = "INSERT INTO users (userName, userPassword, userFirst, userLast, userEmail, userAdmin) VALUES( ";
		query += "'" + set.getUserName() + "', ";
		query += "'" + set.getUserPassword() + "', ";
		query += "'" + set.getUserFirst() + "', ";
		query += "'" + set.getUserLast() + "', ";
		query += "'" + set.getUserEmail() + "', ";
		query += "'" + set.getUserAdmin() + "'";
		query += ")";
		
		try {
			return conn.runUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int clearClasses()
	{
		String query = "truncate classes;";
		
		try {
			return conn.runUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int addClass(Class1 c) throws SQLException {
		
		//System.out.printf("\n\n\nAdding Class: %s\n\n\n", c.getCombo());
		String query = "INSERT INTO classes (classNumber, classSubject, classCatalog, classSection, classCombination, className, classDescription, classAcadGroup, classCapacity, classEnrolled, classDays,";
		query += " classTimeStart, classTimeEnd, classDateStart, classDateEnd, classInstructFirst, classInstructLast, classRoom, classCampus, classMode, classComponent) VALUES( ";
		query += "'" + c.getClassNumber() + "', ";
		query += "'" + c.getClassSubject() + "', ";
		query += "'" + c.getClassCatalog() + "', ";
		query += "'" + c.getClassSection() + "', ";
		query += "'" + c.getClassCombination() + "', ";
		query += "'" + c.getClassName() + "', ";
		query += "'" + c.getClassDescription() + "', ";
		query += "'" + c.getClassAcadGroup() + "', ";
		query += "'" + c.getClassCapacity() + "', ";
		query += "'" + c.getClassEnrolled() + "', ";
		query += "'" + c.getClassDays() + "', ";
		query += "'" + c.getClassTimeStart() + "', ";
		query += "'" + c.getClassTimeEnd() + "', ";
		query += "'" + c.getClassDateStart() + "', ";
		query += "'" + c.getClassDateEnd() + "', ";
		query += "'" + c.getClassInstructFirst() + "', ";
		query += "'" + c.getClassInstructLast() + "', ";
		query += "'" + c.getClassRoom() + "', ";
		query += "'" + c.getClassCampus() + "', ";
		query += "'" + c.getClassMode() + "', ";
		query += "'" + c.getClassComponent() + "'";
		query += ")";		
			return conn.runUpdate(query);
	}
	

	public int updateClass(Class1 c) throws SQLException {
		
		System.out.printf("\n\n\nAdding Class: %d\n\n\n", c.getClassID());
		String query = "UPDATE `classes` SET "
				+ " classNumber=\"" + c.getClassNumber() + "\", "
				+ " classSubject=\"" + c.getClassSubject() + "\", "
				+ " classCatalog=\"" + c.getClassCatalog() + "\", "
				+ " classSection=\"" + c.getClassSection() + "\", "
				+ " classCombination=\"" + c.getClassCombination() + "\", "
				+ " className=\"" + c.getClassName() + "\", "
				+ " classDescription=\"" + c.getClassDescription() + "\", "
				+ " classAcadGroup=\"" + c.getClassAcadGroup() + "\", "
				+ " classCapacity=\"" + c.getClassCapacity() + "\", "
				+ " classEnrolled=\"" + c.getClassEnrolled() + "\", "
				+ " classDays=\"" + c.getClassDays() + "\", "
				+ " classTimeStart=\"" + c.getClassTimeStart() + "\", "
				+ " classTimeEnd=\"" + c.getClassTimeEnd() + "\", "
				+ " classDateStart=\"" + c.getClassDateStart() + "\", "
				+ " classDateEnd=\"" + c.getClassDateEnd() + "\", "
				+ " classInstructFirst=\"" + c.getClassInstructFirst() + "\", "
				+ " classInstructLast=\"" + c.getClassInstructLast() + "\", "
				+ " classRoom=\"" + c.getClassRoom() + "\", "
				+ " classCampus=\"" + c.getClassCampus() + "\", "
				+ " classMode=\"" + c.getClassMode() + "\", "
				+ " classComponent=\"" + c.getClassComponent() + "\" "
				+ " WHERE classID=\"" + c.getClassID() + "\"";				
		//String query = "UPDATE `classes` SET classNbr=\"" + c.getClassNbr() + "\" WHERE class_id=\"" + c.getClassID() + "\"";	
			return conn.runUpdate(query);
	}
	
	
	public int insertAccRequest(AccRequest ar){
					
		String query = "INSERT INTO accRequests (fName, lName, email, username, pass, reasoning) VALUES ( ";
		query += "'" + ar.getFName() + "', ";
		query += "'" + ar.getLName() + "', ";
		query += "'" + ar.getEmail() + "', ";
		query += "'" + ar.getUsername() + "', ";
		query += "'" + ar.getPass() + "', ";
		query += "'" + ar.getReasoning() + "'";
		query += ")";
		
		try {
			return conn.runUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	

	
	
	public List<User> getUsers() throws Exception {
		ResultSet rs = null;
		List<User> list = new ArrayList<User>();
		
		rs = conn.runQuery("SELECT userName, userFirst, userLast, userEmail, userAdmin FROM users");
		
		if(rs != null){
			while(rs.next()){
				User item = new User();
				//System.out.println(rs.getString("username"));
				item.setUserName(rs.getString("userName"));
				item.setUserFirst(rs.getString("userFirst"));
				item.setUserLast(rs.getString("userLast"));
				item.setUserEmail(rs.getString("userEmail"));
				item.setUserAdmin(rs.getInt("userAdmin"));
				list.add(item);
			}
		}
		return list;
	}
	
	public List<AccRequest> getAccRequests() throws Exception {
		ResultSet rs = null;
		List<AccRequest> list = new ArrayList<AccRequest>();
		
		rs = conn.runQuery("SELECT fName, lName, email, username, pass, reasoning FROM accRequests");
		
		if(rs != null){
			while(rs.next()){
				AccRequest item = new AccRequest();
				System.out.println(rs.getString("username"));
				item.setFName(rs.getString("fName"));
				item.setLName(rs.getString("lName"));
				item.setEmail(rs.getString("email"));
				item.setUsername(rs.getString("username"));
				item.setPass(rs.getString("pass"));
				item.setReasoning(rs.getString("reasoning"));
				list.add(item);
			}
		}
		return list;
	}
	
	
	public List<Class1> getClasses() throws Exception {
		ResultSet rs = null;
		List<Class1> list = new ArrayList<Class1>();
		
		rs = conn.runQuery("SELECT classID, classNumber, className, classDays, classSubject, classCatalog, classSection, classCombination, classDescription, classCampus, classAcadGroup, classInstructFirst, classInstructLast, classTimeStart, classTimeEnd, classDateStart, classDateEnd, classCapacity, classEnrolled, classRoom FROM classes");
		
		if(rs != null){
			while(rs.next()){
				Class1 item = new Class1();
				//System.out.println(rs.getString("name"));
				item.setClassID(rs.getInt("classID"));
				item.setClassNumber(rs.getInt("classNumber"));
				item.setClassName(rs.getString("className"));
				item.setClassRoom(rs.getString("classRoom"));
				item.setClassDays(rs.getString("classDays"));
				item.setClassSubject(rs.getString("classSubject"));
				item.setClassInstructFirst(rs.getString("classInstructFirst"));
				item.setClassInstructLast(rs.getString("classInstructLast"));
				item.setClassTimeStart(rs.getString("classTimeStart"));
				item.setClassTimeEnd(rs.getString("classTimeEnd"));
				item.setClassDateStart(rs.getString("classDateStart"));
				item.setClassDateEnd(rs.getString("classDateEnd"));
				item.setClassCapacity(rs.getInt("classCapacity"));
				item.setClassEnrolled(rs.getInt("classEnrolled"));
				item.setClassCatalog(rs.getString("classCatalog"));
				item.setClassSection(rs.getString("classSection"));
				item.setClassCombination(rs.getString("classCombination"));
				item.setClassDescription(rs.getString("classDescription"));
				item.setClassCampus(rs.getString("classCampus"));
				item.setClassAcadGroup(rs.getString("classAcadGroup"));
				list.add(item);
			}
		}
		return list;
	}
	
	public Class1 getClassFromID(int classID) throws Exception {
		ResultSet rs = null;
		//Class1 list = new Class1();
		Class1 item = new Class1();
		
		rs = conn.runQuery("SELECT classID, classNumber, classSubject, classCatalog, classSection, classCombination, className, classDescription, classAcadGroup, classCapacity, classEnrolled, classDays, "
				+ "classTimeStart, classTimeEnd, classDateStart, classDateEnd, classInstructFirst, classInstructLast, classRoom, classCampus, classMode, classComponent, classCrsAttrVal FROM classes WHERE classID = '" + classID +"' ");
		
		
		if(rs != null){
			while(rs.next()){
				item = new Class1();
				item.setClassID(classID);
				System.out.printf("\n\nClass ID from MyServices: %d\n\n", item.getClassID());
				item.setClassNumber(rs.getInt("classNumber"));
				item.setClassSubject(rs.getString("classSubject"));
				item.setClassCatalog(rs.getString("classCatalog"));
				item.setClassSection(rs.getString("classSection"));
				item.setClassCombination(rs.getString("classCombination"));
				item.setClassName(rs.getString("className"));
				item.setClassDescription(rs.getString("classDescription"));
				item.setClassAcadGroup(rs.getString("classAcadGroup"));
				item.setClassCapacity(rs.getInt("classCapacity"));
				item.setClassEnrolled(rs.getInt("classEnrolled"));
				item.setClassDays(rs.getString("classDays"));
				item.setClassTimeStart(rs.getString("classTimeStart"));
				item.setClassTimeEnd(rs.getString("classTimeEnd"));
				item.setClassDateStart(rs.getString("classDateStart"));
				item.setClassDateEnd(rs.getString("classDateEnd"));
				item.setClassInstructLast(rs.getString("classInstructLast"));
				item.setClassInstructFirst(rs.getString("classInstructFirst"));
				item.setClassRoom(rs.getString("classRoom"));
				item.setClassCampus(rs.getString("classCampus"));
				item.setClassMode(rs.getString("classMode"));
				item.setClassComponent(rs.getString("classComponent"));
				item.setClassCrsAttrVal(rs.getString("classCrsAttrVal"));
				//item.setChairType(rs.getString("chairType"));
				//item.setBoardType(rs.getString("boardType"));
				//item.setDeskType(rs.getString("deskType"));
				//list.add(item);
				
			}	
		}
		return item;
	}
	

	
	
	public int deleteClass(int classID){
		
		String query = "DELETE FROM classes WHERE classID='" + classID + "' ";
		
		try {
			return conn.runUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int deleteAccRequest(String username) throws Exception {
		
		String query = "DELETE FROM accRequests WHERE username='" + username +"' ";
		
		try {
			return conn.runUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public int deleteAccount (String username) throws Exception {
		
		String query = "DELETE FROM users WHERE userName='" +  username +"' ";
		
		try {
			return conn.runUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	public boolean validateLogin(String user, String pass) throws Exception {
			
		ResultSet rs = null;
		
		String query = "SELECT userName FROM users WHERE userName='" + user + "' AND userPassword='" + pass + "'";
		
		rs = conn.runQuery(query);
		
		if(rs.next()){
			return true;
		}else{
			return false;
		}


	}
	
	public boolean adminStatus(String user) throws Exception {
		
		ResultSet rs = null;
		
		String query = "SELECT userAdmin FROM users WHERE userName='" + user + "' ";
		
		rs = conn.runQuery(query);
		
		if(rs.next()){
			if(rs.getInt("userAdmin") == 1){
				return true;
			}
		}
		
		return false;



	}
	

	
	
	
	
}
