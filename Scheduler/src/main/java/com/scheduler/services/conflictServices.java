package com.scheduler.services;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import com.scheduler.dbconnector.JdbcManager;
import com.scheduler.validation.validateClassroom;
import com.scheduler.validation.validateTeacher;
import com.scheduler.valueObjects.Class1;
import com.scheduler.valueObjects.Conflict;

public class conflictServices {
	
	private validateClassroom validatorClassroom;
	private validateTeacher validatorTeacher;
	
	/**
	 * Constructor, creates instances of the validation classes for later usage
	 */
	public conflictServices() {
		validatorClassroom = new validateClassroom();
		validatorTeacher = new validateTeacher();
	}
	
	/**
	 * @param semester - The semester to check conflicts for
	 * @return - Returns a list of all conflicts for all class for the specified semester 
	 */
	public List<Conflict> getConflicts( String semester) {
		return getConflicts( semester, (List)null );
	}
	
	/**
	 * @param semester - The semester to check conflicts for
	 * @param class1 - The modified class, will check if there are any conflicts between other classes and it
	 * @return - Returns a list of all conflicts for all class for the specified semester 
	 */
	public List<Conflict> getConflicts( String semester, Class1 class1 ) {
		return getConflicts( semester, Arrays.asList(class1) );
	}
	
	/**
	 * @param semester - The semester to check conflicts for
	 * @param classes - The modified class(es), will check if there are any conflicts between other classes and those provided
	 * @return - Returns a list of all conflicts for all class for the specified semester 
	 */
	public List<Conflict> getConflicts( String semester, List<Class1> classes ) {
		
		Connection jdbcConn = null;
		List<Conflict> conList = null;
			
		try {
			jdbcConn = JdbcManager.getConnection();
			conList = validatorClassroom.validateClassroomRun(semester, jdbcConn, classes);
			conList.addAll(validatorTeacher.validateTeacherRun(semester, jdbcConn, classes));
		} catch( Exception Exc ) {
			System.out.print("Error handling conflicts\n" + Exc.getMessage() + "\n");
		} finally {
			
			if( jdbcConn != null ) {
				JdbcManager.close(jdbcConn);
			}
		}
		
		return conList;
	}

	
}
