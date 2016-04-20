package com.scheduler.services;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.scheduler.dbconnector.JdbcManager;
import com.scheduler.dbconnector.dbConnector;
import com.scheduler.validation.validateClassroom;
import com.scheduler.validation.validateTeacher;
import com.scheduler.valueObjects.Class1;
import com.scheduler.valueObjects.Conflict;

public class conflictServices {
	
	private validateClassroom validatorClassroom;
	private validateTeacher validatorTeacher;
	
	public conflictServices() {
		validatorClassroom = new validateClassroom();
		validatorTeacher = new validateTeacher();
	}
	
	public List<Conflict> getConflicts( String semester) {
		return getConflicts( semester, (List)null );
	}
	
	public List<Conflict> getConflicts( String semester, Class1 class1 ) {
		return getConflicts( semester, Arrays.asList(class1) );
	}
	
	public List<Conflict> getConflicts( String semester, List<Class1> classes ) {
		
		dbConnector dbConn = new dbConnector();
		JdbcManager jdbc = new JdbcManager();
		List<Conflict> conList = null;
			
		/*
		try {
			conList = validatorClassroom.validateClassroomRun(semester, dbConn, classes);
			conList.addAll(validatorTeacher.validateTeacherRun(semester, dbConn, classes));
		} catch( SQLException SQLE ) {
			System.out.print("Error handling conflicts\n" + SQLE.getMessage() + "\n");
		}
		*/
		
		
		dbConn.closeConnection();
		
		return conList;
	}

	
}
