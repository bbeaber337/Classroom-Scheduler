package com.scheduler.validation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.scheduler.dbconnector.dbConnector;
import com.scheduler.valueObjects.Class1;
import com.scheduler.valueObjects.Classroom;

public class validateClassroom {
	
	public Object validateClassroomRun( String semester, dbConnector conn) throws SQLException {
		return validateClassroomRun( semester, conn, (List)null );
	}
	
	public Object validateClassroomRun( String semester, dbConnector conn, Class1 class1 ) throws SQLException {
		return validateClassroomRun( semester, conn, Arrays.asList(class1) );
	}
	
	
	// These are all set to return void.
	public Object validateClassroomRun( String semester, dbConnector conn, List<Class1> classes ) throws SQLException {
		//This runs stuff
		
		List<Class1> currClasses = null;
		if( classes == null ) {
			/* If null, query for classroom list, then for each
			 * class query for all of the classes in that room
			 * and check the first one with all the rest, then
			 * check the next one with all the rest except the
			 * first one and so forth until all of them have
			 * been checked
			 */
		}
		else {
			if( classes.size() > 0 ) {
				// This will assume all classes being changed are in the same classroom
				
				// Get all classes in the same room as the modified classes
				currClasses = queryClassroom(semester, conn, classes.get(0).getClassRoom());

				// Remove previous versions of the updated classes
				for( Class1 class1 : classes ) {
					currClasses.remove(class1);
				}
				
				// First loop goes through updated classes
				for( Class1 class1 : classes ) {
					
					
					/*** Check for schedule conflict ***/
					// Second loop goes through current classes
					for( Class1 class2: currClasses ) {
						
						if( checkTime( class1, class2 ) ) {
							// TODO: Create time conflict entry in database
						}
					}
					
					/*** Query for current classroom ***/
					Classroom classroom = getClassrooms(semester, conn, class1.getClassRoom()).get(0);
					
					
					/*** Check room capacity with class ***/
					// TODO: Need to support combined classes total size check
					if( class1.getClassCapacity() > classroom.getRoomCapacity() ) {
						// TODO: Create capacity conflict entry in database
					}
					
				}
				
			} else {
				// TODO: Do we need to worry about this?!?
				// I don't think so, it will fall through to the null return.
			}
		}
		
		
		return null;
	}
	
	private List<Classroom> getClassrooms( String semester, dbConnector conn, String classroom ) throws SQLException {
		ResultSet rs = null;
		List<Classroom> list = new ArrayList<Classroom>();
		
		String query = "SELECT * FROM " + semester + "classerooms";
		
		if( classroom != null ) {
			query += " WHERE classRoom ='" + classroom + "'";
		}
		
		rs = conn.runQuery(query);		
		
		if(rs != null){
			while(rs.next()){
				Classroom item = new Classroom();
				//System.out.println(rs.getString("name"));
				item.setRoomID(rs.getInt("roomID"));
				item.setRoomCapacity(rs.getInt("roomCapacity"));
				item.setRoomName(rs.getString("roomName"));
				item.setRoomDeskType(rs.getString("roomDeskType"));
				item.setRoomChairType(rs.getString("roomChairType"));
				item.setRoomBoardType(rs.getString("roomBoardType"));
				item.setRoomDistLearning(rs.getString("roomDistLearning"));
				item.setRoomType(rs.getString("roomType"));
				item.setRoomProjectors(rs.getInt("roomProjectors"));
				list.add(item);
			}
		}
		return list;
	}
	
	// Needs to be updated to reflect IA not classroom validation
	private List<Class1> queryClassroom( String semester, dbConnector conn, String classroom ) throws SQLException {
		ResultSet rs = null;
		List<Class1> list = new ArrayList<Class1>();
		
		rs = conn.runQuery("SELECT * FROM " + semester + "classes WHERE classRoom ='" + classroom + "'");
		
		
		if(rs != null){
			while(rs.next()){
				Class1 item = new Class1();
				//System.out.println(rs.getString("name"));
				item.setClassID(rs.getInt("classID"));
				item.setClassNumber(rs.getInt("classNumber"));
				item.setClassSubject(rs.getString("classSubject"));
				item.setClassCatalog(rs.getString("classCatalog"));
				item.setClassSection(rs.getString("classSection"));
				item.setClassCombination(rs.getString("classCombination"));
				item.setClassName(rs.getString("className"));
				item.setClassDescription(rs.getString("classDescription"));
				item.setClassAcadGroup(rs.getString("classAcadGroup"));
				item.setClassCapacity(rs.getInt("classCapacity"));
				item.setClassEnrolled(rs.getInt("classEnrolled"));
				item.setClassDays(rs.getString("classDays"));
				item.setClassTimeStart(rs.getString("classTimeStart"));
				item.setClassTimeEnd(rs.getString("classTimeEnd"));
				item.setClassDateStart(rs.getString("classDateStart"));
				item.setClassDateEnd(rs.getString("classDateEnd"));
				item.setClassInstructLast(rs.getString("classInstructLast"));
				item.setClassInstructFirst(rs.getString("classInstructFirst"));
				item.setClassRoom(rs.getString("classRoom"));
				item.setClassCampus(rs.getString("classCampus"));
				item.setClassMode(rs.getString("classMode"));
				item.setClassComponent(rs.getString("classComponent"));
				item.setClassSection(rs.getString("classSession"));
				item.setClassCrsAttrVal(rs.getString("classCrsAttrVal"));
				item.setClassMon(rs.getInt("classMon"));
				item.setClassTues(rs.getInt("classTues"));
				item.setClassWed(rs.getInt("classWed"));
				item.setClassThurs(rs.getInt("classThurs"));
				item.setClassFri(rs.getInt("classFri"));
				item.setClassSat(rs.getInt("classSat"));
				list.add(item);
			}
		}
		return list;
	}
	
	private boolean checkTime( Class1 class1, Class1 class2 ) {
		if( class1.getClassMon() == class2.getClassMon() || 
				class1.getClassTues() == class2.getClassTues() ||
				class1.getClassWed() == class2.getClassWed() ||
				class1.getClassThurs() == class2.getClassThurs() ||
				class1.getClassFri() == class2.getClassFri() ) {
			
			SimpleDateFormat sdfIn = new SimpleDateFormat("hh:mm:ss aa");
			
			Long start1, start2, end1, end2;
			start1 = start2 = end1 = end2 = 0L;
			
			try {
				start1 = sdfIn.parse(class1.getClassTimeStart()).getTime();
				start2 = sdfIn.parse(class2.getClassTimeStart()).getTime();
				end1 = sdfIn.parse(class1.getClassTimeEnd()).getTime();
				end2 = sdfIn.parse(class2.getClassTimeEnd()).getTime();
			} catch( ParseException e ) {
				System.out.println("Error parsing date comparison in validateClassroom\n" + e.getMessage());
				return false;
			}

			// They have class on at least one day
			if( start1 == start2 || end1 == end2 ) {
				// If they either start or end at the same time
				return true;
			} else if( start1 < start2 && start2 < end1 ) {
				// If class2 starts between the start and end time of class1
				return true;
			} else if( start1 < end2 ) {
				// If class1 starts between the start and end time of class2
				return true;
			}
			
		}
		
		return false;
	}
	 
	
}
