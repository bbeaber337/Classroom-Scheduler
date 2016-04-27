package com.scheduler.validation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.scheduler.dbconnector.JdbcManager;
import com.scheduler.valueObjects.Class1;
import com.scheduler.valueObjects.Classroom;
import com.scheduler.valueObjects.Conflict;

public class validateClassroom {
	
	/**
	 * @param semester - Used for querying the proper semester's tables
	 * @param conn - The connection to be used for queries
	 * @return - List of conflicts for existing classes
	 * @throws Exception - An exception is thrown if the SQL queries fail in case.
	 */
	public List<Conflict> validateClassroomRun( String semester, Connection conn) throws Exception {
		return validateClassroomRun( semester, conn, (List)null );
	}
	
	/**
	 * @param semester - Used for querying the proper semester's tables
	 * @param conn - The connection to be used for queries
	 * @param class1 - The class that was modified that have been modified. If null it checks all classes
	 * @return - List of conflicts for modified class
	 * @throws Exception - An exception is thrown if the SQL queries fail in case.
	 */
	public List<Conflict> validateClassroomRun( String semester, Connection conn, Class1 class1 ) throws Exception {
		return validateClassroomRun( semester, conn, Arrays.asList(class1) );
	}
	
	
	// These are all set to return void.
	/**
	 * @param semester - Used for querying the proper semester's tables
	 * @param conn - The connection to be used for queries
	 * @param classes - List of class(es) that have been modified. If null it checks all classes
	 * @return - List of conflicts for modified class(es)
	 * @throws Exception - An exception is thrown if the SQL queries fail in case.
	 */
	public List<Conflict> validateClassroomRun( String semester, Connection conn, List<Class1> classes ) throws Exception {
		
		
		Conflict conflict = null;
		List<Conflict> conList = new ArrayList<Conflict>();
		
		List<Class1> currClasses = null;
		if( classes == null ) {
			/* If null, query for classroom list, then for each
			 * class query for all of the classes in that room
			 * and check the first one with all the rest, then
			 * check the next one with all the rest except the
			 * first one and so forth until all of them have
			 * been checked
			 */
			
			int count1;
			int count2;
			
			List<Classroom> classrooms = getClassrooms(semester, conn, null);
			
			// First loop, go through classrooms
			// Second loop, select class
			// Third loop, compare selected class to all other classes
			
			for( Classroom room : classrooms) {
				currClasses = queryClassroom(semester, conn, room.getRoomName());
				
				for( count1 = 0; count1 < currClasses.size(); count1++ ) {
					Class1 class1 = currClasses.get(count1);
					
					/*** Check room capacity with class ***/
					if( class1.getClassCapacity() > room.getRoomCapacity() ) {
						conflict = new Conflict();
						conflict.setClass1(class1.getClassID());
						conflict.setClass2(room.getRoomID());
						conflict.setConfType("Room Capacity");
						conflict.setValue1(class1.getClassTimeStart());
						conflict.setValue2(((Integer)room.getRoomCapacity()).toString());
						conList.add(conflict);
					}
					
					
					for( count2 = count1 + 1; count2 < currClasses.size(); count2++ ) {
						Class1 class2 = currClasses.get(count2);
						
						/*** Check for schedule conflict ***/
						if( checkTime( class1, class2 ) ) {
							conflict = new Conflict();
							if( class1.getClassID() < class2.getClassID() ) {
								conflict.setClass1(class1.getClassID());
								conflict.setClass2(class2.getClassID());
								conflict.setConfType("Room Time");
								conflict.setValue1(class1.getClassTimeStart());
								conflict.setValue2(class2.getClassTimeStart());
								conList.add(conflict);
							} else {
								conflict.setClass1(class2.getClassID());
								conflict.setClass2(class1.getClassID());
								conflict.setConfType("Room Time");
								conflict.setValue1(class2.getClassTimeStart());
								conflict.setValue2(class1.getClassTimeStart());
								conList.add(conflict);
							}
						}
					}
					
				}
			}
			
		} else {
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
							conflict = new Conflict();
							if( class1.getClassID() < class2.getClassID() ) {
								conflict.setClass1(class1.getClassID());
								conflict.setClass2(class2.getClassID());
								conflict.setConfType("Room Time");
								conflict.setValue1(class1.getClassTimeStart());
								conflict.setValue2(class2.getClassTimeStart());
								conList.add(conflict);
							} else {
								conflict.setClass1(class2.getClassID());
								conflict.setClass2(class1.getClassID());
								conflict.setConfType("Room Time");
								conflict.setValue1(class2.getClassTimeStart());
								conflict.setValue2(class1.getClassTimeStart());
								conList.add(conflict);
							}
						}
					}
					
					/*** Query for current classroom ***/
					Classroom classroom = getClassrooms(semester, conn, class1.getClassRoom()).get(0);
					
					
					/*** Check room capacity with class ***/
					// TODO: Need to support combined classes total size check
					if( class1.getClassCapacity() > classroom.getRoomCapacity() ) {
						conflict = new Conflict();
						conflict.setClass1(class1.getClassID());
						conflict.setClass2(classroom.getRoomID());
						conflict.setConfType("Room Capacity");
						conflict.setValue1(class1.getClassTimeStart());
						conflict.setValue2(((Integer)classroom.getRoomCapacity()).toString());
						conList.add(conflict);
					}
					
				}
				
			} else {
				// TODO: Do we need to worry about this?!?
				// I don't think so, it will fall through to the null return.
			}
		}
		
		
		return conList;
	}
	
	/**
	 * @param semester - Used for querying the proper semester's tables
	 * @param conn - The connection to be used for queries
	 * @param classroom - The classroom to be queried for
	 * @return - The row for the classroom if specified, or all rows for classrooms
	 * @throws Exception - An exception is thrown if the SQL queries fail in case.
	 */
	private List<Classroom> getClassrooms( String semester, Connection conn, String classroom ) throws Exception {
		ResultSet rs = null;
		List<Classroom> list = new ArrayList<Classroom>();
		
		Statement stmt = null;
		String query = "SELECT * FROM " + semester + "classrooms";
		
		if( classroom != null ) {
			query += " WHERE classRoom ='" + classroom + "'";
		}
		
		try {
        	stmt = conn.createStatement();
        	rs = stmt.executeQuery(query);
		
			if(rs != null){
				while(rs.next()){
					Classroom item = new Classroom();
					//System.out.println(rs.toString());
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
		} catch (SQLException SQLE) {
        	throw new Exception("SQL Exception in validateClassroom, getClassrooms\n", SQLE.getCause());
        } finally {
        	JdbcManager.close(rs);
        	JdbcManager.close(stmt);
        }
		
		
		return list;
	}
	
	// Needs to be updated to reflect IA not classroom validation
	/**
	 * @param semester - Used for querying the proper semester's tables
	 * @param conn - The connection to be used for queries
	 * @param classroom - The classroom to query classes for
	 * @return - All table entries for classes in the specified classroom
	 * @throws Exception - An exception is thrown if the SQL queries fail in case.
	 */
	private List<Class1> queryClassroom( String semester, Connection conn, String classroom ) throws Exception {
		ResultSet rs = null;
		List<Class1> list = new ArrayList<Class1>();
		
		Statement stmt = null;
		String query = "SELECT * FROM " + semester + "classes";
		
		if( classroom != null ) {
			query += " WHERE classRoom ='" + classroom + "'";
		}

        try {
        	stmt = conn.createStatement();
        	rs = stmt.executeQuery(query);

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
        } catch (SQLException SQLE) {
        	if (stmt != null) { 
        		stmt.close(); 
        	}
        	throw new Exception("SQL Exception in validateClassroom, queryClassroom\n", SQLE.getCause());
        }
        
        if (stmt != null) { 
    		stmt.close(); 
    	}
        
		return list;
	}
	
	/**
	 * @param class1 - Class 1 to be checked
	 * @param class2 - Class 2 to be checked
	 * @return - returns true if the class times overlap
	 */
	private boolean checkTime( Class1 class1, Class1 class2 ) {
		
		// If they have class on at least one day
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
