package com.scheduler.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.scheduler.valueObjects.*;
/**
 * contains methods for detecting conflicts in the schedule
 */
public class conflictServices {
	
	/**
	 * generates a list of all conflicts for the given semester
	 * @param semester - the semester to check
	 * @return return a list of all conflicts for the given semester
	 */
	public static List<Conflict> getConflicts(String semester){
		return getConflicts(semester, 0);
	}
	
	/**
	 * Generates a list of conflicts for the given semester for the given classID, if the classID = 0 the list contains all conflicts for the semester
	 * @param semester - the semester to check
	 * @param classID - classID to return conflicts for, if 0 returns all conflicts
	 * @return returns a list of conflicts for the given semester and classID
	 */
	public static List<Conflict> getConflicts(String semester, int classID){
		List<Conflict> list = new ArrayList<Conflict>();
		list.addAll(checkClassrooms(semester));
		list.addAll(checkInstructors(semester));
		list.addAll(checkClasses(semester));
		if (classID > 0) {
			for (Conflict c : list){
				if (c.getClass1() == classID || c.getClass2() == classID){
					list.remove(c);
				}
			}
		}
		return list;
	}
	
	/**
	 * Checks classrooms of the given semester for classes with overlapping time frames
	 * @param semester - the semester to check
	 * @return  returns a list of all conflicts for a semester where two classes are in the same classroom and have overlapping times.
	 */
	private static List<Conflict> checkClassrooms(String semester){
		List<Conflict> list = new ArrayList<Conflict>();
		for (Classroom room : dbServices.getClassrooms(semester)){
			list.addAll(findTimeConflicts(semester, dbServices.getClassesByRoomID(semester, room.getRoomID())));
		}
		list.addAll(findCapacityConflicts(semester));
		return list;
	}
	
	/**
	 * Checks instructors of the given semester for classes with overlapping time frames
	 * @param semester - the semester to check
	 * @return returns a list of all conflicts for a semester where two classes have the same instructor and have overlapping times
	 */
	private static List<Conflict> checkInstructors(String semester){
		List<Conflict> list = new ArrayList<Conflict>();
		for (Instructor instructor : dbServices.getInstructors(semester)){
			list.addAll(findTimeConflicts(semester, dbServices.getClassesByInstructorID(semester, instructor.getID())));
		}
		return list;
	}
	
	/**
	 * Checks the given classlist for overlapping time frames
	 * @param semester - the semester to check
	 * @param classlist - the classlist to check for time conflicts
	 * @return a list of conflicts where two classes from the given classlist have overlapping timeframes
	 */
	private static List<Conflict> findTimeConflicts(String semester, Classlist classlist){
		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
		SimpleDateFormat tf = new SimpleDateFormat("hh:mm:ss a");
		
		List<Conflict> list = new ArrayList<Conflict>();
		Map<String,String> ournames = classlist.getHeaders();
		
		for (int i = 0; i < classlist.size(); i++){
			Class1 c1 = classlist.get(i);
			for (int j = i + 1; j < classlist.size(); j++){
				Class1 c2 = classlist.get(j);
				if (c1.getGroupNumber() == 0 || c1.getGroupNumber() != c2.getGroupNumber()){
					if((c1.getClassMon() == 1 && c2.getClassMon() == 1) || (c1.getClassTues() == 1 && c2.getClassTues() == 1)
							|| (c1.getClassWed() == 1 && c2.getClassWed() == 1) || (c1.getClassThurs() == 1 && c2.getClassThurs() == 1)
							|| (c1.getClassFri() == 1 && c2.getClassFri() == 1) || (c1.getClassSat() == 1 && c2.getClassSat() == 1)
							|| (c1.getClassSun() == 1 && c2.getClassSun() == 1)){
						try{
							Calendar c1Start = Calendar.getInstance();
							c1Start.setTime(tf.parse(c1.get(ournames.get("starttime"))));
							Calendar c1End = Calendar.getInstance();
							c1End.setTime(tf.parse(c1.get(ournames.get("endtime"))));
							Calendar c2Start = Calendar.getInstance();
							c2Start.setTime(tf.parse(c2.get(ournames.get("starttime"))));
							Calendar c2End = Calendar.getInstance();
							c2End.setTime(tf.parse(c2.get(ournames.get("endtime"))));
							if (c1Start.equals(c2Start) || (c1Start.before(c2Start) && c2Start.before(c1End)) || 
									(c2Start.before(c1Start) && c1Start.before(c2End))){
								c1Start.setTime(df.parse(c1.get(ournames.get("startdate"))));
								c1End.setTime(df.parse(c1.get(ournames.get("enddate"))));
								c2Start.setTime(df.parse(c2.get(ournames.get("startdate"))));
								c2End.setTime(df.parse(c2.get(ournames.get("enddate"))));
								if (c1Start.equals(c2Start) || (c1Start.before(c2Start) && c2Start.before(c1End)) || 
										((c2Start.before(c1Start) && c1Start.before(c2End)))){
									// conflict found
									Conflict conflict = new Conflict();
									conflict.setClass1(c1.getClassID());
									conflict.setClass2(c2.getClassID());
									conflict.setConfType(Conflict.TIME_CONFLICT);
									list.add(conflict);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * Checks that all classes for the given semester have an assigned teacher and classroom.  Also checks if the class if over class capacity
	 * @param semester - the semester to check
	 * @return a list of conflicts where a class is not assigned a teacher or classroom or is over class capacity.
	 */
	private static List<Conflict> checkClasses(String semester){
		List<Conflict> list = new ArrayList<Conflict>();
		Classlist classlist = dbServices.getClasses(semester);
		Map<String, String> ournames = dbServices.getOurNames(semester);
		for (Class1 c : classlist){
			if (c.get(ournames.get("instructorfirst")) == null && c.get(ournames.get("instructorlast")) == null){
				Conflict conflict = new Conflict();
				conflict.setClass1(c.getClassID());
				conflict.setConfType(Conflict.NO_TEACHER);
				list.add(conflict);
			}
			if (c.get(ournames.get("classroom")) == null){
				Conflict conflict = new Conflict();
				conflict.setClass1(c.getClassID());
				conflict.setConfType(Conflict.NO_CLASSROOM);
				list.add(conflict);
			}
			if (Integer.parseInt(c.get(ournames.get("totenrolled"))) > Integer.parseInt(c.get(ournames.get("capenrolled")))){
				Conflict conflict = new Conflict();
				conflict.setClass1(c.getClassID());
				conflict.setConfType(Conflict.CAPACITY_CONFLICT);
				list.add(conflict);
			}
		}
		return list;
	}
	
	/**
	 * Checks the given semester for classes that are over classroom capacity
	 * @param semester - the given semester to check
	 * @return a list of conflicts where a class has exceeded classroom capacity
	 */
	private static List<Conflict> findCapacityConflicts(String semester){
		List<Conflict> list = new ArrayList<Conflict>();
		List<String> capIDlist = dbServices.getOverCapacityList(semester);
		for (String s : capIDlist){
			Conflict conflict = new Conflict();
			conflict.setClass1(Integer.parseInt(s));
			conflict.setConfType(Conflict.CLASSROOM_CAPACITY_CONFLICT);
			list.add(conflict);
		}
		return list;		
	}
	
	/**
	 * Checks a given semester for classrooms that have classes at the same time as the given class
	 * @param semester - the semester to check
	 * @param c1 - the class to check for conflicts
	 * @return returns a list of classrooms that have other classes scheduled at the same time as the given class
	 */
	public static List<String> findPotentialTimeConflicts(String semester, Class1 c1){
		Map<String,String> ournames = dbServices.getOurNames(semester);
		List<String> list = new ArrayList<String>();
		Classlist classlist = dbServices.getClasses(semester);
		
		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
		SimpleDateFormat tf = new SimpleDateFormat("hh:mm:ss a");

		for (Class1 c2 : classlist){	
			if (!c1.get(ournames.get("classroom")).equalsIgnoreCase(c2.get(ournames.get("classroom")))){
				if (c1.getGroupNumber() == 0 || c1.getGroupNumber() != c2.getGroupNumber()){
					if((c1.getClassMon() == 1 && c2.getClassMon() == 1) || (c1.getClassTues() == 1 && c2.getClassTues() == 1)
							|| (c1.getClassWed() == 1 && c2.getClassWed() == 1) || (c1.getClassThurs() == 1 && c2.getClassThurs() == 1)
							|| (c1.getClassFri() == 1 && c2.getClassFri() == 1) || (c1.getClassSat() == 1 && c2.getClassSat() == 1)
							|| (c1.getClassSun() == 1 && c2.getClassSun() == 1)){
						try{
							Calendar c1Start = Calendar.getInstance();
							c1Start.setTime(tf.parse(c1.get(ournames.get("starttime"))));
							Calendar c1End = Calendar.getInstance();
							c1End.setTime(tf.parse(c1.get(ournames.get("endtime"))));
							Calendar c2Start = Calendar.getInstance();
							c2Start.setTime(tf.parse(c2.get(ournames.get("starttime"))));
							Calendar c2End = Calendar.getInstance();
							c2End.setTime(tf.parse(c2.get(ournames.get("endtime"))));
							if (c1Start.equals(c2Start) || (c1Start.before(c2Start) && c2Start.before(c1End)) || 
									(c2Start.before(c1Start) && c1Start.before(c2End))){
								c1Start.setTime(df.parse(c1.get(ournames.get("startdate"))));
								c1End.setTime(df.parse(c1.get(ournames.get("enddate"))));
								c2Start.setTime(df.parse(c2.get(ournames.get("startdate"))));
								c2End.setTime(df.parse(c2.get(ournames.get("enddate"))));
								if (c1Start.equals(c2Start) || (c1Start.before(c2Start) && c2Start.before(c1End)) || 
										((c2Start.before(c1Start) && c1Start.before(c2End)))){
									if (!list.contains(c2.get(ournames.get("classroom")))){
										list.add(c2.get(ournames.get("classroom")));
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * Checks a semester for conflicts with a given class
	 * @param semester - the semester to check
	 * @param c - the class to check against
	 * @return returns true if the class has conflicts, otherwise false
	 */
	public static Boolean findPotentialChangeConflicts(String semester, Class1 c){
		Classlist cl = new Classlist(semester);
		cl.add(c);
		cl.updateClasses(semester);
		Class1 c1 = cl.get(0);
		Boolean rval = false;
		Map<String,String> ournames = dbServices.getOurNames(semester);
		Classlist classlist = dbServices.getClasses(semester);
		
		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
		SimpleDateFormat tf = new SimpleDateFormat("hh:mm:ss a");

		for (Class1 c2 : classlist){	
			if (c1.getClassID() != c2.getClassID() && (c1.get(ournames.get("classroom")).equalsIgnoreCase(c2.get(ournames.get("classroom"))) || 
					(c1.get(ournames.get("instructorfirst")).equalsIgnoreCase(c2.get(ournames.get("instructorfirst"))) &&
					 c1.get(ournames.get("instructorlast")).equalsIgnoreCase(c2.get(ournames.get("instructorlast")))))){
				if (c1.getGroupNumber() == 0 || c1.getGroupNumber() != c2.getGroupNumber()){
					if((c1.getClassMon() == 1 && c2.getClassMon() == 1) || (c1.getClassTues() == 1 && c2.getClassTues() == 1)
							|| (c1.getClassWed() == 1 && c2.getClassWed() == 1) || (c1.getClassThurs() == 1 && c2.getClassThurs() == 1)
							|| (c1.getClassFri() == 1 && c2.getClassFri() == 1) || (c1.getClassSat() == 1 && c2.getClassSat() == 1)
							|| (c1.getClassSun() == 1 && c2.getClassSun() == 1)){
						try{
							Calendar c1Start = Calendar.getInstance();
							c1Start.setTime(tf.parse(c1.get(ournames.get("starttime"))));
							Calendar c1End = Calendar.getInstance();
							c1End.setTime(tf.parse(c1.get(ournames.get("endtime"))));
							Calendar c2Start = Calendar.getInstance();
							c2Start.setTime(tf.parse(c2.get(ournames.get("starttime"))));
							Calendar c2End = Calendar.getInstance();
							c2End.setTime(tf.parse(c2.get(ournames.get("endtime"))));
							if (c1Start.equals(c2Start) || (c1Start.before(c2Start) && c2Start.before(c1End)) || 
									(c2Start.before(c1Start) && c1Start.before(c2End))){
								c1Start.setTime(df.parse(c1.get(ournames.get("startdate"))));
								c1End.setTime(df.parse(c1.get(ournames.get("enddate"))));
								c2Start.setTime(df.parse(c2.get(ournames.get("startdate"))));
								c2End.setTime(df.parse(c2.get(ournames.get("enddate"))));
								if (c1Start.equals(c2Start) || (c1Start.before(c2Start) && c2Start.before(c1End)) || 
										((c2Start.before(c1Start) && c1Start.before(c2End)))){
									rval = true;
									break;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		rval = rval || Integer.parseInt(c1.get(ournames.get("totenrolled"))) > Integer.parseInt(c1.get(ournames.get("capacity")));
		return rval;
	}

}
