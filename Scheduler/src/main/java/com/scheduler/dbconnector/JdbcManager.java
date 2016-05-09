package com.scheduler.dbconnector;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

/**
 * Handles creating connections to the database as a DataSource
 * if the DataSource isn't found, it defaults to initial testing connection.
 */
public class JdbcManager{

	private static InitialContext context = null;
	
	public static Connection getConnection()
		throws SQLException, NamingException {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		}

		if (context == null){
			context = new InitialContext();
		}
		try {
			DataSource datasource = (DataSource)context.lookup("java:jboss/datasources/MySQLDS");
			conn = datasource.getConnection();
		} catch (Exception e){
			System.out.println("No Datasource Connection");
		}
		if (conn == null){		
			String url = "jdbc:mysql://127.0.0.1:3306/classroom";
			String username = "root";
			String password = "element337";

			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Not a datasource connection");
		}
		
		return conn;
	}
	
	public static void close(ResultSet rset){
		if (rset != null) {
			try {
				rset.close();
			} catch (SQLException e) {
				System.out.println("Problem closing ResultSet");
			}
		}
	}
	
	public static void close(Connection conn){
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Problem closing Connection");
			}
		}
	}
	
	public static void close(Statement stmt){
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				System.out.println("Problem closing Statement");
			}
		}
	}
	
}
