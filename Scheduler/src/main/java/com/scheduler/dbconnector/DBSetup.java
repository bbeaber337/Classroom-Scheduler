package com.scheduler.dbconnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.scheduler.valueObjects.Semester;

/**
 * Servlet implementation class DBSetup
 */
public class DBSetup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBSetup() {
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
					"`userFirst` VARCHAR(45) DEFAULT ''," +
					"`userLast` VARCHAR(45) DEFAULT ''," +
					"`userEmail` VARCHAR(45) NULL," +
					"`userLevel` INT NOT NULL DEFAULT 0," +
					"PRIMARY KEY (`userID`)," +
					"UNIQUE INDEX `userName_UNIQUE` (`userName` ASC)," +
					"UNIQUE INDEX `userID_UNIQUE` (`userID` ASC)," +
					"UNIQUE INDEX `userEmail_UNIQUE` (`userEmail` ASC))");
			rset = stmt.executeQuery("SELECT * FROM USERS");
			if (!rset.next()) {
				stmt.executeUpdate("INSERT INTO USERS (userName, userPassword, userLevel)" +
									"VALUES ('admin', 'admin', 2)");
			}
			for (String s:Semester.SEMESTERS){
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `" + s + "headers` (" +
									"`header` VARCHAR(45) NOT NULL," +
									"`collumn` INT NOT NULL," +
									"`ourname` VARCHAR(45) NULL," +
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
									"`instructorID` INT NOT NULL AUTO_INCREMENT," +
									"`instructorFirst` VARCHAR(45) NULL," +
									"`instructorLast` VARCHAR(45) NULL," +
									"`instructorBoard` VARCHAR(45) NULL DEFAULT 'Any'," +
									"`instructorDesk` VARCHAR(45) NULL DEFAULT 'Any'," +
									"`instructorChair` VARCHAR(45) NULL DEFAULT 'Any'," +
									"`instructorComment` VARCHAR(250) NULL DEFAULT ''," +
									"PRIMARY KEY (`instructorID`)," +
									"UNIQUE INDEX `instructorID_UNIQUE` (`instructorID` ASC))");
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
