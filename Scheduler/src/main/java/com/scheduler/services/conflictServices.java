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
		
		Connection jdbcConn = null;
		List<Conflict> conList = null;
			
		try {
			jdbcConn = JdbcManager.getConnection();
			conList = validatorClassroom.validateClassroomRun(semester, jdbcConn, classes);
			conList.addAll(validatorTeacher.validateTeacherRun(semester, jdbcConn, classes));
		} catch( Exception Exc ) {
			if( jdbcConn != null ) {
				JdbcManager.close(jdbcConn);
			}
			System.out.print("Error handling conflicts\n" + Exc.getMessage() + "\n");
		}
		
		if( jdbcConn != null ) {
			JdbcManager.close(jdbcConn);
		}
		
		
		
		
		
		return conList;
	}

	
}
