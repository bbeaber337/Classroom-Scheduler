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
		
		String query = "INSERT INTO users (username, pass, fName, lName, email, admin) VALUES( ";
		query += "'" + set.getUsername() + "', ";
		query += "'" + set.getPass() + "', ";
		query += "'" + set.getFName() + "', ";
		query += "'" + set.getLName() + "', ";
		query += "'" + set.getEmail() + "', ";
		query += "'" + set.getAdmin() + "'";
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
		String query = "INSERT INTO classes (classNbr, subject, catalog, section, combo, name, description, acadGroup, capacity, enrolled, day,";
		query += " sTime, eTime, sDate, eDate, fName, lName, facil, location, mode, comp) VALUES( ";
		query += "'" + c.getClassNbr() + "', ";
		query += "'" + c.getSubject() + "', ";
		query += "'" + c.getCatalog() + "', ";
		query += "'" + c.getSection() + "', ";
		query += "'" + c.getCombo() + "', ";
		query += "'" + c.getName() + "', ";
		query += "'" + c.getDescription() + "', ";
		query += "'" + c.getAcadGroup() + "', ";
		query += "'" + c.getCapacity() + "', ";
		query += "'" + c.getEnrolled() + "', ";
		query += "'" + c.getDay() + "', ";
		query += "'" + c.getSTime() + "', ";
		query += "'" + c.getETime() + "', ";
		query += "'" + c.getSDate() + "', ";
		query += "'" + c.getEDate() + "', ";
		query += "'" + c.getFName() + "', ";
		query += "'" + c.getLName() + "', ";
		query += "'" + c.getFacil() + "', ";
		query += "'" + c.getLocation() + "', ";
		query += "'" + c.getMode() + "', ";
		query += "'" + c.getComp() + "'";
		query += ")";
		
			return conn.runUpdate(query);

	}
	
	public int updateClass(Class1 c) throws SQLException {
		
		System.out.printf("\n\n\nAdding Class: %d\n\n\n", c.getClassID());
		String query = "UPDATE `classes` SET "
				+ " classNbr=\"" + c.getClassNbr() + "\", "
				+ " subject=\"" + c.getSubject() + "\", "
				+ " catalog=\"" + c.getCatalog() + "\", "
				+ " section=\"" + c.getSection() + "\", "
				+ " combo=\"" + c.getCombo() + "\", "
				+ " name=\"" + c.getName() + "\", "
				+ " description=\"" + c.getDescription() + "\", "
				+ " acadGroup=\"" + c.getAcadGroup() + "\", "
				+ " capacity=\"" + c.getCapacity() + "\", "
				+ " enrolled=\"" + c.getEnrolled() + "\", "
				+ " day=\"" + c.getDay() + "\", "
				+ " sTime=\"" + c.getSTime() + "\", "
				+ " eTime=\"" + c.getETime() + "\", "
				+ " sDate=\"" + c.getSDate() + "\", "
				+ " eDate=\"" + c.getEDate() + "\", "
				+ " fName=\"" + c.getFName() + "\", "
				+ " lName=\"" + c.getLName() + "\", "
				+ " facil=\"" + c.getFacil() + "\", "
				+ " location=\"" + c.getLocation() + "\", "
				+ " mode=\"" + c.getMode() + "\", "
				+ " comp=\"" + c.getComp() + "\", "
				+ " chairType=\"" + c.getChairType() + "\", "
				+ " boardType=\"" + c.getBoardType() + "\", "
				+ " DeskType=\"" + c.getDeskType() + "\" "
				+ " WHERE class_id=\"" + c.getClassID() + "\"";			
		
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
	
	
	public List<Class1> getClasses() throws Exception {
		ResultSet rs = null;
		List<Class1> list = new ArrayList<Class1>();
		
		rs = conn.runQuery("SELECT class_id, classNbr, name, subject, fName, lName, sTime, eTime, sDate, eDate, capacity, enrolled FROM classes");
		
		if(rs != null){
			while(rs.next()){
				Class1 item = new Class1();
				//System.out.println(rs.getString("name"));
				item.setClassID(rs.getInt("class_id"));
				item.setClassNbr(rs.getInt("classNbr"));
				item.setName(rs.getString("name"));
				item.setSubject(rs.getString("subject"));
				item.setFName(rs.getString("fName"));
				item.setLName(rs.getString("lName"));
				item.setSTime(rs.getString("sTime"));
				item.setETime(rs.getString("eTime"));
				item.setSDate(rs.getString("sDate"));
				item.setEDate(rs.getString("eDate"));
				item.setCapacity(rs.getInt("capacity"));
				item.setEnrolled(rs.getInt("enrolled"));
				list.add(item);
			}
		}
		return list;
	}
	
	
	public List<User> getUsers() throws Exception {
		ResultSet rs = null;
		List<User> list = new ArrayList<User>();
		
		rs = conn.runQuery("SELECT username, fName, lName, email, admin FROM users");
		
		if(rs != null){
			while(rs.next()){
				User item = new User();
				System.out.println(rs.getString("username"));
				item.setUsername(rs.getString("username"));
				item.setFName(rs.getString("fName"));
				item.setLName(rs.getString("lName"));
				item.setEmail(rs.getString("email"));
				item.setAdmin(rs.getInt("admin"));
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
	
	public Class1 getClassFromID(int classID) throws Exception {
		ResultSet rs = null;
		Class1 list = new Class1();
		Class1 item = new Class1();
		
		rs = conn.runQuery("SELECT class_id, classNbr, subject, catalog, section, combo, name, description, acadGroup, capacity, enrolled, day, "
				+ "sTime, eTime, sDate, eDate, fName, lName, facil, location, mode, comp, chairType, boardType, deskType FROM classes WHERE class_id = '" + classID +"' ");
		
		
		if(rs != null){
			while(rs.next()){
				item = new Class1();
				item.setClassID(classID);
				System.out.printf("\n\nClass ID from MyServices: %d\n\n", item.getClassID());
				item.setClassNbr(rs.getInt("classNbr"));
				item.setSubject(rs.getString("subject"));
				item.setCatalog(rs.getString("catalog"));
				item.setSection(rs.getString("section"));
				item.setCombo(rs.getString("combo"));
				item.setName(rs.getString("name"));
				item.setDescription(rs.getString("description"));
				item.setAcadGroup(rs.getString("acadGroup"));
				item.setCapacity(rs.getInt("capacity"));
				item.setEnrolled(rs.getInt("enrolled"));
				item.setDay(rs.getString("day"));
				item.setSTime(rs.getString("sTime"));
				item.setETime(rs.getString("eTime"));
				item.setSDate(rs.getString("sDate"));
				item.setEDate(rs.getString("eDate"));
				item.setLName(rs.getString("lName"));
				item.setFName(rs.getString("fName"));
				item.setFacil(rs.getString("facil"));
				item.setLocation(rs.getString("location"));
				item.setMode(rs.getString("mode"));
				item.setComp(rs.getString("comp"));
				item.setChairType(rs.getString("chairType"));
				item.setBoardType(rs.getString("boardType"));
				item.setDeskType(rs.getString("deskType"));
				//list.add(item);
				
			}	
		}
		return item;
	}
	
	public int deleteClass(int classID){
		
		String query = "DELETE FROM classes WHERE class_id='" + classID + "' ";
		
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
		
		String query = "DELETE FROM users WHERE username='" +  username +"' ";
		
		try {
			return conn.runUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	public boolean validateLogin(String user, String pass) throws Exception {
			
		ResultSet rs = null;
		
		String query = "SELECT username FROM users WHERE username='" + user + "' AND pass='" + pass + "'";
		
		rs = conn.runQuery(query);
		
		if(rs.next()){
			return true;
		}else{
			return false;
		}


	}
	
	public boolean adminStatus(String user) throws Exception {
		
		ResultSet rs = null;
		
		String query = "SELECT admin FROM users WHERE username='" + user + "' ";
		
		rs = conn.runQuery(query);
		
		if(rs.next()){
			if(rs.getInt("admin") == 1){
				return true;
			}
		}
		
		return false;



	}
	

	
	
	
	
}
