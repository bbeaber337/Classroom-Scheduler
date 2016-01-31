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
