package com.scheduler.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.scheduler.valueObjects.*;

public class jsonServices {
	
	public static String buildClassChangeConflicts(String semester, Class1 c){
		JsonObject json = Json.createObjectBuilder()
				.add("data", conflictServices.findPotentialChangeConflicts(semester, c)).build();
		return json.toString();
	}
	
	public static String buildClasses(String semester, ServletContext context, HttpServletResponse response, int userlevel){
		Classlist items = dbServices.getClasses(semester);
		Map<String, String> headermap = items.getHeaders();
		JsonArrayBuilder classBuilder = Json.createArrayBuilder();
		for (Class1 c : items){ 
			JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
			if (userlevel == User.USER_ADMIN){
				jsonBuilder.add(  "Select_Room", "<form action='"+response.encodeURL(context.getContextPath()+"/Classes/Select")+"' method='post' ><input type='hidden' name='classID' value='" + c.getClassID() + "'><input class='center-block' type='submit' value='Select' alt='Select Class'/></form>");
			}
			jsonBuilder.add("Group_Number", c.getGroupNumber());
			for (String s : Class1.OURNAMES){
				String val = c.get(headermap.get(s));
				if (val == null) {
					val = "";
				}
				jsonBuilder.add(s,val);
			}
			if (userlevel == User.USER_ADMIN){
				jsonBuilder.add(  "Edit_Class", "<form action='"+response.encodeURL(context.getContextPath()+"/Classes/Edit")+"' method='post' ><input type='hidden' name='classID' value='" + c.getClassID() + "'><input type='submit' value='Edit' alt='Edit Class'/></form>")
				.add(  "Delete_Class", "<form action='"+response.encodeURL(context.getContextPath()+"/Classes/Delete")+"' method='post' ><input type='hidden' name='classID' value='" + c.getClassID() + "'><input type='submit' value='Delete' alt='Delete Class' onclick=\"return confirm('Are you sure you want to delete this Class?')\"/></form>");
			}
			classBuilder.add(jsonBuilder);
		}
		JsonObject json = Json.createObjectBuilder()
				.add("data", classBuilder).build();
		return json.toString();
	}
	
	public static String buildClassrooms(String semester, ServletContext context, HttpServletResponse response, int userlevel){
		List<Classroom> items = dbServices.getClassrooms(semester);
		JsonArrayBuilder classBuilder = Json.createArrayBuilder();
		for (Classroom c : items){ 
			JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
			jsonBuilder.add("Select_Room", "<form onsubmit=\"getSchedule(" + c.getRoomID() + " , '" + c.getRoomName() + "');return false;\" ><input type='hidden' name='roomID' value='" + c.getRoomID() + "'><input type='submit' value='Select' alt='Select Classroom'/></form>");
			jsonBuilder.add("name",c.getRoomName());
			jsonBuilder.add("capacity",c.getRoomCapacity());
			jsonBuilder.add("roomType",c.getRoomType());
			jsonBuilder.add("desk",c.getRoomDeskType());
			jsonBuilder.add("board",c.getRoomBoardType());
			jsonBuilder.add("chair",c.getRoomChairType());
			jsonBuilder.add("distLearning",c.getRoomDistLearning());
			jsonBuilder.add("projectors",c.getRoomProjectors());
			if (userlevel == User.USER_ADMIN){
				jsonBuilder.add(  "Edit_Room", "<form action='"+response.encodeURL(context.getContextPath()+"/Classrooms/Edit")+"' method='post' ><input type='hidden' name='roomID' value='" + c.getRoomID() + "'><input type='submit' value='Edit' alt='Edit Classroom'/></form>")
				.add(  "Delete_Room", "<form action='"+response.encodeURL(context.getContextPath()+"/Classrooms/Delete")+"' method='post' ><input type='hidden' name='roomID' value='" + c.getRoomID() + "'><input type='submit' value='Delete' alt='Delete Classroom' onclick=\"return confirm('Are you sure you want to delete this Classroom?')\"/></form>");
			}
			classBuilder.add(jsonBuilder);
		}
		JsonObject json = Json.createObjectBuilder()
				.add("data", classBuilder).build();
		return json.toString();
	}
	
	public static String buildInstructors(String semester, ServletContext context, HttpServletResponse response, int userlevel){
		List<Instructor> items = dbServices.getInstructors(semester);
		JsonArrayBuilder classBuilder = Json.createArrayBuilder();
		for (Instructor i : items){ 
			JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
			jsonBuilder.add("Select_Instructor", "<form onsubmit=\"getSchedule(" + i.getID() + " , '" + i.getNameFirst() + " " + i.getNameLast() + "');return false;\" ><input type='hidden' name='instructorID' value='" + i.getID() + "'><input type='submit' value='Select' alt='Select Instructor'/></form>");
			jsonBuilder.add("firstname",i.getNameFirst());
			jsonBuilder.add("lastname",i.getNameLast());
			jsonBuilder.add("desk",i.getPrefDesk());
			jsonBuilder.add("board",i.getPrefBoard());
			jsonBuilder.add("chair",i.getPrefChair());
			jsonBuilder.add("comment",i.getComment());
			if (userlevel == User.USER_ADMIN){
				jsonBuilder.add(  "Edit_Instructor", "<form action='"+response.encodeURL(context.getContextPath()+"/Instructors/Edit")+"' method='post' ><input type='hidden' name='instructorID' value='" + i.getID() + "'><input type='submit' value='Edit' alt='Edit Class'/></form>")
				.add(  "Delete_Instructor", "<form action='"+response.encodeURL(context.getContextPath()+"/Instructors/Delete")+"' method='post' ><input type='hidden' name='instructorID' value='" + i.getID() + "'><input type='submit' value='Delete' alt='Delete Class' onclick=\"return confirm('Are you sure you want to delete this Instructor?')\"/></form>");
			}
			classBuilder.add(jsonBuilder);
		}
		JsonObject json = Json.createObjectBuilder()
				.add("data", classBuilder).build();
		return json.toString();
	}
	
	public static String buildWeek(Classlist classlist, ServletContext context, HttpServletResponse response){
		JsonArrayBuilder classBuilder = Json.createArrayBuilder();
		try {
			Map<String,String> ournames = classlist.getHeaders();
			SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss a");
			Calendar dayStart = Calendar.getInstance();
			dayStart.setTime(df.parse("07:00:00 AM"));
			for (Class1 c : classlist){
				if (c.get(ournames.get("starttime")) != null){
					Calendar start = Calendar.getInstance();
					start.setTime(df.parse(c.get(ournames.get("starttime"))));
					Calendar end = Calendar.getInstance();
					end.setTime(df.parse(c.get(ournames.get("endtime"))));
					
					classBuilder.add( Json.createObjectBuilder()
					.add(  "Class_Id", c.getClassID())
					.add(  "Start", (int)((start.getTimeInMillis() - dayStart.getTimeInMillis()) / (60000 * 15)))
					.add(  "Duration",  (int)(Math.ceil(end.getTimeInMillis() - start.getTimeInMillis()) / (60000 * 15)))
					.add(  "Class_Number", c.get(ournames.get("subject")) + c.get(ournames.get("catalog")) + "-" + c.get(ournames.get("section")))
					.add(  "Classroom", c.get(ournames.get("classroom")))
					.add(  "Class_Name", c.get(ournames.get("classname")))
					.add(  "Time", c.get(ournames.get("starttime")) + " - " + c.get(ournames.get("endtime")) )
					.add(  "Sun", c.getClassSun())
					.add(  "Mon", c.getClassMon())
					.add(  "Tues", c.getClassTues())
					.add(  "Wed", c.getClassWed())
					.add(  "Thur", c.getClassThurs())
					.add(  "Fri", c.getClassFri())
					.add(  "Sat", c.getClassSat())
					.add(  "link", response.encodeURL(context.getContextPath()+"/Classes/Edit?classID=" + c.getClassID())));
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		JsonObject json = Json.createObjectBuilder()
				.add("data", classBuilder).build();
	    return json.toString();
	}

	public static Classlist buildClasslistByInstructor(String semester, int instructorID) {
		Classlist allClasses = dbServices.getClasses(semester);
		Map<String, String> ournames = allClasses.getHeaders();
		Instructor instructor = dbServices.getInstructorByID(semester, instructorID);
		Classlist returnList = new Classlist();
		returnList.setHeaders(ournames);
		for (Class1 c : allClasses){
			if (instructor.getNameLast().equals(c.get(ournames.get("instructorlast"))) &&
					instructor.getNameFirst().equals(c.get(ournames.get("instructorfirst")))){
				returnList.add(c);
			}
		}
		return returnList;
	}
	
	public static Classlist buildClasslistByClassroom(String semester, int roomID) {
		Classlist classlist = dbServices.getClasses(semester);
		Map<String,String> ournames = classlist.getHeaders();
		Classroom classroom = dbServices.getClassroomByID(semester, roomID);
		Classlist returnList = new Classlist();
		returnList.setHeaders(ournames);
		for (Class1 c : classlist){
			if (classroom.getRoomName().equals(c.get(ournames.get("classroom")))){
				returnList.add(c);
			}
		}
		return returnList;
	}
}
