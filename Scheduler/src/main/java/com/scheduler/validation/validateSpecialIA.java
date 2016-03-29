package com.scheduler.validation;

import java.util.Arrays;
import java.util.List;

import com.scheduler.valueObjects.Class1;

public class validateSpecialIA {
	
	public Object validateSpecialIA( String semester) {
		return validateSpecialIA( semester, (List)null );
	}
	
	public Object validateSpecialIA( String semester, Class1 class1 ) {
		return validateSpecialIA( semester, Arrays.asList(class1) );
	}
	
	
	// These are all set to return void.
	public Object validateSpecialIA( String semester, List<Class1> classes ) {
		//This runs stuff
		if( classes == null ) {
			// NOTE: The following note is for the classroom validation
			/* If null, query for classroom list, then for each
			 * class query for all of the classes in that room
			 * and check the first one with all the rest, then
			 * check the next one with all the rest except the
			 * first one and so forth until all of them have
			 * been checked
			 */
		}
		else {
			
		}
		
		
		
		
		
		return null;
	}
	
	// Needs to be updated to reflect IA not classroom validation
	private List<Class1> queryClassroom( String semester, String classroom ) {
		return null;
	}
	 
	
}
