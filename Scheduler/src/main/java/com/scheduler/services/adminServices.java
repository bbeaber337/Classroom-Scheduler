package com.scheduler.services;

import java.sql.*;

import com.scheduler.dbconnector.*;
import com.scheduler.valueObjects.*;

/**
 * class for verifying login credentials
 */
public class adminServices {
	
	/**
	 * Takes a username and password and returns the associated User
	 * @param username - username to match in database
	 * @param password - password to match in database
	 * @return User - a User class representing the user's info from the login parameters.  Class values will not be set if user not found.
	 */
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
