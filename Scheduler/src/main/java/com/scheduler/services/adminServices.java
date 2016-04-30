package com.scheduler.services;

import java.sql.*;

import com.scheduler.dbconnector.*;
import com.scheduler.valueObjects.*;

public class adminServices {
	
	public static User validLogin(String username, String password){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		String query = "select userPassword, userLevel from users where userName = ?";
		User user = new User();
		try{
			conn = JdbcManager.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1,username.toLowerCase());
			rset = stmt.executeQuery();
			if (rset.next()){
				String pass = rset.getString("userPassword");
				if(pass.equals(password)){
					user.setUserLevel(rset.getInt("userLevel"));
					user.setUserName(username);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcManager.close(rset);
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		return user;
	}

}
