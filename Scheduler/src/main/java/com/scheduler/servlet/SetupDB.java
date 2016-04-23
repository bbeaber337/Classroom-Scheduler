package com.scheduler.servlet;

import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.scheduler.dbconnector.*;
import com.scheduler.valueObjects.*;

/**
 * Servlet implementation class SetupDB
 */
public class SetupDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetupDB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		try{
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `users` (" +
					"`userID` INT(11) NOT NULL AUTO_INCREMENT," +
					"`userName` VARCHAR(45) NOT NULL," +
					"`userPassword` VARCHAR(45) NOT NULL," +
					"`userFirst` VARCHAR(45) NULL DEFAULT ''," +
					"`userLast` VARCHAR(45) NULL DEFAULT ''," +
					"`userEmail` VARCHAR(45) NULL DEFAULT ''," +
					"`userAdmin` INT NOT NULL DEFAULT 0," +
					"PRIMARY KEY (`userID`)," +
					"UNIQUE INDEX `userName_UNIQUE` (`userName` ASC)," +
					"UNIQUE INDEX `userID_UNIQUE` (`userID` ASC)," +
					"UNIQUE INDEX `userEmail_UNIQUE` (`userEmail` ASC))");
			rset = stmt.executeQuery("SELECT * FROM USERS");
			if (!rset.next()) {
				stmt.executeUpdate("INSERT INTO USERS (userName, userPassword, userAdmin)" +
									"VALUES ('admin', 'admin', 1)");
			}
			for (String s:Semester.SEMESTERS){
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `" + s + "headers` (" +
									"`header` VARCHAR(45) NOT NULL," +
									"`column` INT NOT NULL," +
									"`name` VARCHAR(45) NULL," +
									"PRIMARY KEY (`header`))");
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `" + s + "classrooms` (" +
									"`roomID` INT NOT NULL AUTO_INCREMENT," +
									"`roomCapacity` INT NULL DEFAULT 0," +
									"`roomName` VARCHAR(45) NULL," +
									"`roomDeskType` VARCHAR(45) NULL DEFAULT 'Not Specified' ," +
									"`roomChairType` VARCHAR(45) NULL DEFAULT 'Not Specified' ," +
									"`roomBoardType` VARCHAR(45) NULL DEFAULT 'Not Specified' ," +
									"`roomDistLearning` VARCHAR(45) NULL," +
									"`roomType` VARCHAR(45) NULL  DEFAULT 'Not Specified' ," +
									"`roomProjectors` INT NULL DEFAULT 0," +
									"PRIMARY KEY (`roomID`)," +
									"UNIQUE INDEX `roomID_UNIQUE` (`roomID` ASC))");
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `" + s + "instructors` (" +
									"`instructID` INT NOT NULL AUTO_INCREMENT," +
									"`instructFirst` VARCHAR(45) NULL," +
									"`instructLast` VARCHAR(45) NULL," +
									"`instructBoard` VARCHAR(45) NULL DEFAULT 'Any'," +
									"`instructDesk` VARCHAR(45) NULL DEFAULT 'Any'," +
									"`instructChair` VARCHAR(45) NULL DEFAULT 'Any'," +
									"`instructComment` VARCHAR(250) NULL DEFAULT ''," +
									"PRIMARY KEY (`instructID`)," +
									"UNIQUE INDEX `instrucID_UNIQUE` (`instructID` ASC))");
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `" + s + "conflicts` (" +
									"`conflictID` INT NOT NULL," +
									"`conflictClass1` VARCHAR(45) NULL," +
									"`conflictClass2` VARCHAR(45) NULL," +
									"`conflictType` VARCHAR(45) NULL," +
									"`conflictValue1` VARCHAR(45) NULL," +
									"`conflictValue2` VARCHAR(45) NULL," +
									"UNIQUE INDEX `conflictID_UNIQUE` (`conflictID` ASC)," +
									"PRIMARY KEY (`conflictID`))");
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			JdbcManager.close(rset);
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		
	}

}
