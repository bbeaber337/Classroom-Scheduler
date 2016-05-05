package com.scheduler.valueObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.scheduler.services.dbServices;

public class Classlist extends ArrayList<Class1>{
	
	Map<String, String> myheaders;
	
	public Classlist(){}
	
	public Classlist(String semester){
		setHeaders(dbServices.getOurNames(semester));
	}
	
	public Map<String, String> getHeaders(){
		return myheaders;
	}
	
	public void setHeaders(Map<String,String> headers){
		this.myheaders = headers;
	}
	
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
	
	public void updateClasses(String semester){
		for (Class1 c : this){
			// set room capacity
			if (myheaders.get("roomname") != null && myheaders.get("capacity") != null){
				Classroom classroom = dbServices.getClassroomByName(semester, c.get(myheaders.get("classroom")));
				c.set("capacity", Integer.toString(classroom.getRoomCapacity()));
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
