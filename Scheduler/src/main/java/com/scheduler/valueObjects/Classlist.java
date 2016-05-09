package com.scheduler.valueObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.scheduler.services.dbServices;
/**
 * a ArrayList specifically for storing Class1's
 *
 */
public class Classlist extends ArrayList<Class1>{
	
	Map<String, String> myheaders;
	
	public Classlist(){}
	
	/**
	 * creates a classlist, stores the associated names from the given semester this list should represent classes from
	 * @param semester - the semester to get associated values names
	 */
	public Classlist(String semester){
		setHeaders(dbServices.getOurNames(semester));
	}
	
	/**
	 * get the associated name map for this classlist
	 * @return the associated name map for this classlist
	 */
	public Map<String, String> getHeaders(){
		return myheaders;
	}
	
	/**
	 * set the associated name map for this classlist
	 * @param headers - the associated name map for this classlist
	 */
	public void setHeaders(Map<String,String> headers){
		this.myheaders = headers;
	}
	
	/**
	 * get the total enrollment for all classes in this Classlist
	 * @return the combined total enrollment for all classes in this Classlist
	 */
	public int getTotEnrolled(){
		int sum = 0;
		for (Class1 c : this){
			String t = c.get(myheaders.get("totenrolled"));
			if (t!=null){
				sum += Integer.parseInt(t);
			}
		}		
		return sum;
	}
	
	/**
	 * get the combined capacity for all classes in this Classlist
	 * @return the combined capacity for all classes in this Classlist
	 */
	public int getCapEnrolled(){
		int sum = 0;
		for (Class1 c : this){
			String t = c.get(myheaders.get("capenrolled"));
			if (t!=null){
				sum += Integer.parseInt(t);
			}
		}		
		return sum;
	}
	
	/**
	 * update each class in the classlist by
	 * 	- parsing meeting day field and setting appropriate class*** attributes
	 *  - updating the capacity to match the current classroom capacity from the classroom table
	 * @param semester - the semester to search
	 */
	public void updateClasses(String semester){
		for (Class1 c : this){
			// set room capacity
			if (myheaders.get("classroom") != null){
				Classroom classroom = dbServices.getClassroomByName(semester, c.get(myheaders.get("classroom")));
				if (classroom != null){
					c.set(myheaders.get("capacity"), Integer.toString(classroom.getRoomCapacity()));
				}
			}
			// set day values
			if (myheaders.get("days") != null){
				String days = c.get(myheaders.get("days")).toLowerCase();
				if (days.contains("-")){
					Boolean between = false;
					String first = days.substring(0, 1);
					String last = days.substring(days.length()-1, days.length());
					List<String> week = Arrays.asList("m","t","w","r","f","s");
					List<String> classWeek = Arrays.asList("classMon","classTues","classWed","classThurs","classFri","classSat");
					for(int i = 0; i < week.size(); i++){
						if (!between && first.equalsIgnoreCase(week.get(i))){
							c.set(classWeek.get(i), Integer.toString(1));
							between = true;
						} else if (between) {
							c.set(classWeek.get(i), Integer.toString(1));
							if (last.equalsIgnoreCase(week.get(i))){
								between = false;
							}
						} else {
							c.set(classWeek.get(i), Integer.toString(0));
						}
					}
				} else {
					if (days.contains("m")){
						c.setClassMon(1);
					} else {
						c.setClassMon(0);
					}
					if (days.contains("t")){
						c.setClassTues(1);
					} else {
						c.setClassTues(0);
					}
					if (days.contains("w")){
						c.setClassWed(1);
					} else {
						c.setClassWed(0);
					}
					if (days.contains("r")){
						c.setClassThurs(1);
					} else {
						c.setClassThurs(0);
					}
					if (days.contains("f")){
						c.setClassFri(1);
					} else {
						c.setClassFri(0);
					}
					if (days.contains("s")){
						c.setClassSat(1);
					} else {
						c.setClassSat(0);
					}
				}
				c.setClassSun(0);
			}
		}
	}

}
