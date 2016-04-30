package com.scheduler.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.scheduler.dbconnector.*;
import com.scheduler.valueObjects.*;

public class dbServices {
	
/*
 * ===============================================================================================================
 * 								Misc Functions
 * ===============================================================================================================
 */
	public static List<String> getUploadedSemesters(){
		List<String> list = new ArrayList<String>();
		
		Connection conn = null;
		ResultSet rset = null;
		try{
			conn = JdbcManager.getConnection();
			DatabaseMetaData md = conn.getMetaData();
			for (String s : Semester.SEMESTERS){
				rset = md.getTables(null, null, s + "upload", null);
				if (rset.next()){
					list.add(s);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			JdbcManager.close(rset);
			JdbcManager.close(conn);
		}
		
		return list;
	}
	
	public static int getNextCombo(String semester){
		int rval = 1;
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rset = null;
			String query = "select max(groupNum) from " + semester + "classes";
			try {
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query);
				rset = stmt.executeQuery();
				if (rset.next()){
					rval = rset.getInt(1)+1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rset);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
		return rval;
	}
	
	public static List<String> getSubjects(String semester){
		List<String> list = new ArrayList<String>();
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rset = null;
			String query = "select `subject` from " + semester + "classes group by subject";
			try {
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query);
				rset = stmt.executeQuery();
				while (rset.next()){
					list.add(rset.getString("subject"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rset);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
		return list;
	}
	
	// return all classes part of a group over the classroom's capacity
	public static List<String> getOverCapacityList(String semester){
		List<String> classIDlist = new ArrayList<String>();
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			Statement stmt = null;
			ResultSet rset = null;
			String query = "select classID from " + semester + "classes inner join (select groupNum as gn from "
					+ "(SELECT groupNum, sum(`Cap Enrl`) as totcap,roomCapacity FROM " + semester + "classes inner "
					+ "join " + semester + "classrooms on `facil id` = `roomName` where groupNum != 0 group by groupNum) "
					+ "as getSum where getSum.totCap > getSum.roomCapacity) as overGroups on gn = groupNum;";
			try{
				conn = JdbcManager.getConnection();
				stmt = conn.createStatement();		
				rset = stmt.executeQuery(query);
				while (rset.next()){
					classIDlist.add(rset.getString("classID"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rset);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
		return classIDlist;
	}
	
/*
 * ===============================================================================================================
 * 								Extract Functions
 * ===============================================================================================================
 */
	public static void extractCombos(String semester){
		int comboNum = getNextCombo(semester);
		Classlist classlist = getClasses(semester);
		Map<String, String> ournames = classlist.getHeaders();
		Class1 prevCombo = null;
		for (Class1 c : classlist){
			if (ournames.get("combo") != null && c.get(ournames.get("combo")).equalsIgnoreCase("c")){
				if (prevCombo != null){
					if(c.get(ournames.get("classroom")).equalsIgnoreCase(prevCombo.get(ournames.get("classroom"))) && 
							c.get(ournames.get("days")).equalsIgnoreCase(prevCombo.get(ournames.get("days"))) &&
							c.get(ournames.get("starttime")).equalsIgnoreCase(prevCombo.get(ournames.get("starttime"))) &&
							c.get(ournames.get("startdate")).equalsIgnoreCase(prevCombo.get(ournames.get("startdate")))){
						c.setGroupNumber(comboNum);
					} else {
						prevCombo = c;
						c.setGroupNumber(++comboNum);								
					}
				} else {
					prevCombo = c;
					c.setGroupNumber(++comboNum);
				}
			}
		}
		updateClasses(semester, classlist);
	}
	

	public static void extractClassrooms(String semester){
		if (Semester.SEMESTERS.contains(semester)){
			Map<String, String> ournames = getOurNames(semester);
			List<Classroom> roomlist = new ArrayList<Classroom>();
			Connection conn = null;
			Statement stmt = null;
			ResultSet rset = null;
			String query = "select `" + ournames.get("classroom") + "`,`" + ournames.get("capacity") + "` from " + semester + "classes group by `" + ournames.get("classroom") + "`";
			try {
				conn = JdbcManager.getConnection();
				stmt = conn.createStatement();
				rset = stmt.executeQuery(query);
				while(rset.next()){
					Classroom classroom = new Classroom();
					classroom.setRoomName(rset.getString(1));
					classroom.setRoomCapacity(rset.getInt(2));
					roomlist.add(classroom);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rset);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
			updateClassrooms(semester, roomlist);
		}		
	}
	public static void extractInstructors(String semester){
		if (Semester.SEMESTERS.contains(semester)){
			Map<String, String> ournames = getOurNames(semester);
			List<Instructor> instructorlist = new ArrayList<Instructor>();
			Connection conn = null;
			Statement stmt = null;
			ResultSet rset = null;
			String query = "select `" + ournames.get("instructorfirst") + "`,`"  + ournames.get("instructorlast")+ "` from " + semester + "classes group by `" 
					+ ournames.get("instructorfirst") + "`,`"  + ournames.get("instructorlast")+"`";
			try {
				conn = JdbcManager.getConnection();
				stmt = conn.createStatement();
				rset = stmt.executeQuery(query);
				while(rset.next()){
					Instructor instructor = new Instructor();
					instructor.setNameFirst(rset.getString(1));
					instructor.setNameLast(rset.getString(2));
					instructorlist.add(instructor);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rset);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
			updateInstructors(semester, instructorlist);
		}
	}
	
	public static void deleteDuplicates(String semester){
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			Statement stmt = null;
			String query1 = "DELETE FROM " + semester + "classrooms WHERE roomID NOT IN (SELECT * FROM (SELECT MIN(n.roomID) FROM " + semester + "classrooms n GROUP BY n.roomName) x)";
			String query2 = "DELETE FROM " + semester + "instructors WHERE instructorID NOT IN (SELECT * FROM (SELECT MIN(n.instructorID) FROM " + semester + "instructors n GROUP BY n.instructorFirst, n.instructorLast) x)";
			try {
				conn = JdbcManager.getConnection();
				stmt = conn.createStatement();
				stmt.executeUpdate(query1);
				stmt.executeUpdate(query2);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
	}

/*
 * ===============================================================================================================
 * 								Table Functions
 * ===============================================================================================================
 */
	
	public static void createClassTables(String semester, Map<String,Integer> sizes){
		Connection conn = null;
		PreparedStatement stmt = null;
		if (Semester.SEMESTERS.contains(semester)){
			String query0 = "create table if not exists `" + semester;
			String query = "` (`classID` INT NOT NULL AUTO_INCREMENT,";
			dropClassTables(semester);
			List<String> headers = getHeaders(semester);
			Map<String,String> ournames = getOurNames(semester);
			for (String s : headers) {
				query += "`" + s + "`";
				if (s.equalsIgnoreCase(ournames.get("classname"))){
					query += " varchar(90) NULL,";
				} else if(s.equalsIgnoreCase(ournames.get("instructorfirst"))){
					query += " varchar(45) NULL,";
				} else if(s.equalsIgnoreCase(ournames.get("instructorlast"))){
					query += " varchar(45) NULL,";
				} else {
					query += " varchar("+Integer.toString(sizes.get(s) + 1)+") NULL,";
				}
			}
			String queryend="PRIMARY KEY (`classID`), UNIQUE INDEX `classID_UNIQUE` (`classID` ASC))";
			try {
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query0 + "upload" + query+queryend);
				stmt.executeUpdate();
				try{
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				query += "`groupNum` INT NOT NULL DEFAULT 0," +
						  "`classMon` INT NOT NULL DEFAULT 0," +
						  "`classTues` INT NOT NULL DEFAULT 0," +
						  "`classWed` INT NOT NULL DEFAULT 0," +
						  "`classThurs` INT NOT NULL DEFAULT 0," +
						  "`classFri` INT NOT NULL DEFAULT 0," +
						  "`classSat` INT NOT NULL DEFAULT 0," +
						  "`classSun` INT NOT NULL DEFAULT 0," + queryend;
				stmt = conn.prepareStatement(query0 + "classes" + query);
				stmt.executeUpdate();
			} catch (Exception e) {
					e.printStackTrace();
			} finally {
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
	}
	
	public static void dropClassTables(String semester){
		Connection conn = null;
		PreparedStatement stmt = null;
		if (Semester.SEMESTERS.contains(semester)){
			String query = "drop table if exists " + semester;
			try {
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query + "upload");
				stmt.executeUpdate();
				stmt.close();
				stmt = conn.prepareStatement(query + "classes");
				stmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
	}

/*
 * ===============================================================================================================
 * 								Header Functions
 * ===============================================================================================================
 */
	public static List<String> getHeaders(String semester) {
		ArrayList<String> list = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		if (Semester.SEMESTERS.contains(semester)){
			String query = "select header, collumn from " + semester + "headers order by collumn asc";
			try {
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query);
				rset = stmt.executeQuery();
				while (rset.next()){
					list.add(rset.getInt("collumn"), rset.getString("header"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rset);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
		return list;
	}
	
	public static void setHeaders(String semester, List<String> headers, Map<String,String> ournames){
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			PreparedStatement stmt = null;
			Map<String, String> ournamesByHeader = new HashMap<String,String>();
			for (String s : ournames.keySet()){
				ournamesByHeader.put(ournames.get(s),s);
			}
			String query = "insert into " + semester + "headers (header, collumn, ourname) values (?,?,?)";
			try {
				clearHeaders(semester);
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query);
				for (String s : headers){
					stmt.setString(1, s);
					stmt.setInt(2, headers.indexOf(s));
					stmt.setString(3, ournamesByHeader.get(s));
					stmt.executeUpdate();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
	}
	
	public static void clearHeaders(String semester){
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			PreparedStatement stmt = null;
			String query = "truncate " + semester + "headers";
			try {
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query);
				stmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
	}
	
	public static Map<String, String> getOurNames(String semester) {
		Map<String, String> headermap = new HashMap<String, String>();
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rset = null;
			String query = "select * from " + semester +"headers where ourname is not null";
			try {
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query);
				rset = stmt.executeQuery();
				while (rset.next()){
					headermap.put(rset.getString("ourname"), rset.getString("header"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rset);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
		return headermap;
	}
	
/*
 * ===============================================================================================================
 * 								User Functions
 * ===============================================================================================================
 */
	
	public static List<User> getUsers(){
		List<User> users = new ArrayList<User>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String query = "select * from users";
		
		try{
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			while (rset.next()){
				User user = new User();
				user.setUserID(rset.getInt("userID"));
				user.setUserName(rset.getString("userName"));
				user.setUserFirst(rset.getString("userFirst"));
				user.setUserLast(rset.getString("userLast"));
				user.setUserEmail(rset.getString("userEmail"));
				user.setUserLevel(rset.getInt("userLevel"));
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcManager.close(rset);
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		
		return users;
	}
	
	public static User getUserByID(int userID){
		User user = new User();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		String query = "select * from users where userID = ?";
		
		try{
			conn = JdbcManager.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, Integer.toString(userID));
			rset = stmt.executeQuery();
			while (rset.next()){
				user.setUserID(rset.getInt("userID"));
				user.setUserName(rset.getString("userName"));
				user.setUserFirst(rset.getString("userFirst"));
				user.setUserLast(rset.getString("userLast"));
				user.setUserEmail(rset.getString("userEmail"));
				user.setUserPassword(rset.getString("userPassword"));
				user.setUserLevel(rset.getInt("userLevel"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcManager.close(rset);
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		
		return user;
	}
	
	public static Boolean updateUser(User user){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		int rval = 0;
		
		String query1 = "replace into users (userName,userFirst,userLast,userLevel";
		String query2 = "values (?,?,?,?";
		if (!user.getUserEmail().equalsIgnoreCase("")){
			query1 += ",userEmail";
			query2 += ",?";
		}
		if (user.getUserPassword() != null) {
			query1 += ",userPassword";
			query2 += ",?";
		}
		String query = query1 + ") " + query2 + ")";
		if (user.getUserID() != 0){
			query += "where userID = ?";
		}
		
		try{
			conn = JdbcManager.getConnection();
			if (user.getUserID() == 0){
				stmt = conn.prepareStatement("SELECT userName FROM users WHERE userName = ?");
				stmt.setString(1, user.getUserName());
				rset = stmt.executeQuery();
				if(rset.next()){
					throw new Exception();
				}
				JdbcManager.close(stmt);
			}
			stmt = conn.prepareStatement(query);
			stmt.setString(1, user.getUserName().toLowerCase());
			stmt.setString(2, user.getUserFirst());
			stmt.setString(3, user.getUserLast());
			stmt.setString(4, Integer.toString(user.getUserLevel()));
			int i = 5;
			if (!user.getUserEmail().equalsIgnoreCase("")){
				stmt.setString(i++, user.getUserEmail());
			}
			if (user.getUserPassword() != null){
				stmt.setString(i++, user.getUserPassword());
			}
			if (user.getUserID() != 0){
				stmt.setString(i++, Integer.toString(user.getUserID()));
			}
			rval = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcManager.close(rset);
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		if (rval > 0){
			return true;
		}
		return false;
	}
	
	public static void deleteUser(int userID){
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "delete from users where userID = ?";
		
		try{
			conn = JdbcManager.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, Integer.toString(userID));
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
	}
/*
 * ===============================================================================================================
 * 								Class1 Functions
 * ===============================================================================================================
 */
	// upload classes to database
	public static void uploadClasses(String semester, Classlist classlist){
		if (Semester.SEMESTERS.contains(semester)){
			if (classlist != null && classlist.size() > 0){
				Connection conn = null;
				PreparedStatement uploadstmt = null;
				PreparedStatement classstmt = null;
				List<String> headers = getHeaders(semester);
				try {
					String query0 = "insert into " + semester;
					String query1 = "(";
					String query2 = "values (";
					for (String s : headers){
						query1 += "`" + s + "`,";
						query2 += "?,";
					}
					String uploadquery = query0 + "upload " + query1.substring(0, query1.length()-1) + ") " + query2.substring(0, query2.length()-1) + ")";
					String classquery = query0 + "classes " + query1 + "groupNum,classMon, classTues, classWed, classThurs, classFri, classSat, classSun) " + query2 + "?,?,?,?,?,?,?,?)";
					conn = JdbcManager.getConnection();				
					uploadstmt = conn.prepareStatement(uploadquery);
					classstmt = conn.prepareStatement(classquery);
					for (Class1 c : classlist){
						int i = 1;
						for (String s : headers){
							uploadstmt.setString(i,c.get(s));
							classstmt.setString(i,c.get(s));
							i++;
						}
						uploadstmt.addBatch();
						classstmt.setString(i++, Integer.toString(c.getGroupNumber()));
						classstmt.setString(i++, Integer.toString(c.getClassMon()));
						classstmt.setString(i++, Integer.toString(c.getClassTues()));
						classstmt.setString(i++, Integer.toString(c.getClassWed()));
						classstmt.setString(i++, Integer.toString(c.getClassThurs()));
						classstmt.setString(i++, Integer.toString(c.getClassFri()));
						classstmt.setString(i++, Integer.toString(c.getClassSat()));
						classstmt.setString(i++, Integer.toString(c.getClassSun()));
						classstmt.addBatch();
					}
					uploadstmt.executeBatch();
					classstmt.executeBatch();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					JdbcManager.close(uploadstmt);
					JdbcManager.close(classstmt);
					JdbcManager.close(conn);
				}
			}
		}
	}
	
	// update a class group
	public static Boolean updateGroup(String semester, Class1 c){
		Classlist classlist = getClasses(semester, c.getClassID());
		Map<String, String>ournames = classlist.getHeaders();
		for (Class1 c2 : classlist){
			c2.set(ournames.get("classroom"), c.get(ournames.get("classroom")));
			c2.set(ournames.get("starttime"), c.get(ournames.get("starttime")));
			c2.set(ournames.get("endtime"), c.get(ournames.get("endtime")));
			c2.set(ournames.get("startdate"), c.get(ournames.get("startdate")));
			c2.set(ournames.get("enddate"), c.get(ournames.get("enddate")));
			c2.set(ournames.get("days"), c.get(ournames.get("days")));
		}
		return updateClasses(semester, classlist);
	}
	
	// update a class
	public static Boolean updateClasses(String semester, Class1 c){
		Classlist classlist = new Classlist(semester);
		classlist.add(c);
		return updateClasses(semester, classlist);
	}
	public static Boolean updateClasses(String semester, Classlist classlist){
		boolean success = false;
		classlist.updateClasses(semester);
		if (Semester.SEMESTERS.contains(semester)){
			if (classlist != null && classlist.size() > 0){
				classlist.updateClasses(semester);
				Connection conn = null;
				PreparedStatement stmt = null;
				try {
					Class1 first = classlist.get(0);
					String query1 = "replace into " + semester + "classes (";
					String query2 = "values (";
					Set<String> keys = first.getParamKeys();
					for (String s : keys){
						query1 += "`" + s + "`,";
						query2 += "?,";
					}
					String query = query1.substring(0,query1.length()-1) + ") " + query2.substring(0,query2.length()-1) + ")";				
					conn = JdbcManager.getConnection();				
					stmt = conn.prepareStatement(query);
					for (Class1 c : classlist){
						int i = 1;
						for (String k : keys){
							stmt.setString(i, c.get(k));
							i++;
						}
						stmt.addBatch();
					}
					stmt.executeBatch();
					success = true;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					JdbcManager.close(stmt);
					JdbcManager.close(conn);
				}
			}
		}
		return success;
	}
	
	public static Classlist getClassesBySubject(String semester, String subject){
		return getClasses(semester, 0, null, null, null, subject);
	}
	public static Classlist getClassesByRoomID(String semester, int roomID){
		Classroom classroom = getClassroomByID(semester, roomID);
		return getClasses(semester, 0, classroom.getRoomName(), null, null, null);
	}
	public static Classlist getClassesByInstructorID(String semester, int instructorID){
		Instructor instructor = getInstructorByID(semester, instructorID);
		return getClasses(semester, 0, null, instructor.getNameFirst(), instructor.getNameLast(), null);
	}
	// get all classes
	public static Classlist getClasses(String semester){
		return getClasses(semester, 0, null, null, null, null);
	}
	// get class group by id, class with classID will be first on list
	public static Classlist getClasses(String semester, int classID)
	{
		return getClasses(semester, classID, null, null, null, null);
	}	
	public static Classlist getClasses(String semester, int classID, String classroom, String instructorfirst, String instructorlast, String subject){
		Classlist classlist = new Classlist();
		if (Semester.SEMESTERS.contains(semester)){
			Map<String,String> ournames = getOurNames(semester);
			classlist.setHeaders(ournames);
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rset = null;
			String query = "select * from " + semester + "classes";
			if (classID > 0){
				query += " where classID = ?";
			} else if (classroom != null) {
				query += " where `" + ournames.get("classroom") + "` = ?";
			} else if (instructorfirst != null && instructorlast != null) {
				query += " where (`" + ournames.get("instructorfirst") + "`, `" + ournames.get("instructorlast") + "`) = (?,?)";
			} else if (subject != null) {
				query += " where subject = ?";
			}
			try{
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query);
				if (classID > 0){
					stmt.setInt(1, classID);
				} else if (classroom != null){
					stmt.setString(1, classroom);
				} else if (instructorfirst != null && instructorlast != null){
					stmt.setString(1, instructorfirst);
					stmt.setString(2, instructorlast);
				} else if (subject != null) {
					stmt.setString(1, subject);
				}
				rset = stmt.executeQuery();
				while (rset.next()){
					ResultSetMetaData md = rset.getMetaData();
					Class1 c = new Class1();
					for (int i = 1; i <= md.getColumnCount(); i++){
						c.set(md.getColumnLabel(i), rset.getString(i));
					}
					classlist.add(c);
				}
				if (classID > 0){
					rset.close();
					stmt.close();
					query = "select * from " + semester + "classes where groupNum != 0 and groupNum = (SELECT groupnum from " + semester +"classes where classID = ?) and (classID != ?)";
					stmt = conn.prepareStatement(query);
					stmt.setInt(1, classID);
					stmt.setInt(2, classID);
					rset = stmt.executeQuery();
					while (rset.next()){
						ResultSetMetaData md = rset.getMetaData();
						Class1 c = new Class1();
						for (int i = 1; i <= md.getColumnCount(); i++){
							c.set(md.getColumnLabel(i), rset.getString(i));
						}
						classlist.add(c);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rset);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
		classlist.updateClasses(semester);
		return classlist;
	}
	
	// delete class
	public static void deleteClasses(String semester, int classID){
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			PreparedStatement stmt = null;
			String query = "delete from " + semester + "classes where classID = ?";
			try{
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query);
				stmt.setString(1, Integer.toString(classID));		
				stmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
	}
	
	/*
	 * ===============================================================================================================
	 * 								Classroom Functions
	 * ===============================================================================================================
	 */
	public static boolean updateClassrooms(Classroom cr) {
		boolean success = true;
		for (String s : Semester.SEMESTERS){
			success = success && updateClassrooms(s, cr);
		}
		return success;
	}
	public static boolean updateClassrooms(String semester, Classroom cr) {
		List<Classroom> roomlist = new ArrayList<Classroom>();
		roomlist.add(cr);
		return updateClassrooms(semester, roomlist);
	}
	public static boolean updateClassrooms(String semester, List<Classroom> roomlist){
		boolean success = false;
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			PreparedStatement stmt = null;
			try {
				conn = JdbcManager.getConnection();
				for (Classroom cr : roomlist){
					String query = "replace into " + semester + "classrooms (roomName, roomCapacity, roomType, roomChairType, roomDeskType, "
							+ "roomBoardType, roomDistLearning, roomProjectors";
					if (cr.getRoomID() > 0){
						query += ", roomID";
					}
					query += ") values (?,?,?,?,?,?,?,?";
					if (cr.getRoomID() > 0){
						query += ",?";
					}
					query += ")";
					try{
						stmt = conn.prepareStatement(query);
						stmt.setString(1, cr.getRoomName());
						stmt.setInt(2, cr.getRoomCapacity());
						stmt.setString(3, cr.getRoomType());
						stmt.setString(4, cr.getRoomChairType());
						stmt.setString(5, cr.getRoomDeskType());
						stmt.setString(6, cr.getRoomBoardType());
						stmt.setString(7, cr.getRoomDistLearning());
						stmt.setInt(8, cr.getRoomProjectors());
						if (cr.getRoomID() > 0){
							stmt.setInt(9, cr.getRoomID());
						}
						stmt.executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						JdbcManager.close(stmt);
					}
				}
				success = true;
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				JdbcManager.close(conn);
			}
		}
		return success;
	}
	
	public static Classroom getClassroomByName(String semester, String className){
		Classroom classroom = null;
		List<Classroom> classroomlist = getClassrooms(semester, 0, className);
		if (!classroomlist.isEmpty()){
			classroom = classroomlist.get(0);
		}
		return classroom;
	}
	public static Classroom getClassroomByID(String semester, int roomID){
		Classroom classroom = null;
		List<Classroom> classroomlist = getClassrooms(semester, roomID, null);
		if (!classroomlist.isEmpty()){
			classroom = classroomlist.get(0);
		}
		return classroom;
	}
	
	public static List<Classroom> getClassrooms(String semester){
		return getClassrooms(semester, 0, null);
	}
	public static List<Classroom> getClassrooms(String semester, int roomID, String className){
		List<Classroom> list = new ArrayList<Classroom>();
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String query = "SELECT * FROM " + semester + "classrooms";
			if (roomID > 0){
				query += " where roomID = ?";
			} else if(className != null){
				query += " where className = ?";
			}
			try{
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query);
				if (roomID > 0){
					stmt.setInt(1, roomID);
				} else if(className != null){
					stmt.setString(1, className);
				}
				rs = stmt.executeQuery();
				if(rs != null){
					while(rs.next()){
						Classroom item = new Classroom();
						item.setRoomID(rs.getInt("roomID"));
						item.setRoomCapacity(rs.getInt("roomCapacity"));
						item.setRoomName(rs.getString("roomName"));
						item.setRoomChairType(rs.getString("roomChairType"));
						item.setRoomDeskType(rs.getString("roomDeskType"));
						item.setRoomBoardType(rs.getString("roomBoardType"));
						item.setRoomDistLearning(rs.getString("roomDistLearning"));
						item.setRoomType(rs.getString("roomType"));
						item.setRoomProjectors(rs.getInt("roomProjectors"));
						list.add(item);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rs);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
		return list;
	}
	
	public static void deleteClassroom(String semester, int roomID){
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rset = null;
			String query = "DELETE FROM " + semester + "classrooms WHERE roomID='" + roomID + "' ";
			try{
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query);
				stmt.setString(1, Integer.toString(roomID));		
				stmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rset);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
	}
	
	public static void clearClassrooms(String semester) {
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			Statement stmt = null;
			ResultSet rset = null;
			String query = "truncate " + semester + "classrooms";
			try{
				conn = JdbcManager.getConnection();
				stmt = conn.createStatement();		
				stmt.executeUpdate(query);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rset);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
	}
	
	/*
	 * ===============================================================================================================
	 * 								Instructor Functions
	 * ===============================================================================================================
	 */
	public static boolean updateInstructors(Instructor instructor) {
		boolean success = true;
		for (String s : Semester.SEMESTERS){
			success = success && updateInstructors(s, instructor);
		}
		return success;
	}
	public static boolean updateInstructors(String semester, Instructor instructor) {
		List<Instructor> instructorlist = new ArrayList<Instructor>();
		instructorlist.add(instructor);
		return updateInstructors(semester, instructorlist);
	}
	public static boolean updateInstructors(String semester, List<Instructor> instructorlist){
		boolean success = false;
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			PreparedStatement stmt = null;
			try {
				conn = JdbcManager.getConnection();
				for (Instructor i : instructorlist){
					String query = "replace into " + semester + "instructors (instructorFirst, instructorLast, instructorBoard, instructorDesk, instructorChair, instructorComment";
					if (i.getID() > 0){
						query += ", instructorID";
					}
					query += ") values (?,?,?,?,?,?";
					if (i.getID() > 0){
						query += ",?";
					}
					query += ")";
					try{
						stmt = conn.prepareStatement(query);
						stmt.setString(1, i.getNameFirst());
						stmt.setString(2, i.getNameLast());
						stmt.setString(3, i.getPrefBoard());
						stmt.setString(4, i.getPrefDesk());
						stmt.setString(5, i.getPrefChair());
						stmt.setString(6, i.getComment());
						if (i.getID() > 0){
							stmt.setInt(7, i.getID());
						}
						stmt.executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						JdbcManager.close(stmt);
					}
				}
			success = true;
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				JdbcManager.close(conn);
			}
		}
		return success;
	}
	
	public static Instructor getInstructorByName(String semester, String instructorfirst, String instructorlast){
		Instructor instructor = null;
		List<Instructor> instructorlist = getInstructors(semester, 0, instructorfirst, instructorlast);
		if (!instructorlist.isEmpty()){
			instructor = instructorlist.get(0);
		}
		return instructor;
	}
	public static Instructor getInstructorByID(String semester, int instructorID){
		Instructor instructor = null;
		List<Instructor> instructorlist = getInstructors(semester, instructorID, null, null);
		if (!instructorlist.isEmpty()){
			instructor = instructorlist.get(0);
		}
		return instructor;
	}
	
	public static List<Instructor> getInstructors(String semester){
		return getInstructors(semester, 0, null, null);
	}
	public static List<Instructor> getInstructors(String semester, int instructorID, String instructorfirst, String instructorlast){
		List<Instructor> list = new ArrayList<Instructor>();
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			String query = "SELECT * FROM " + semester + "instructors";
			if (instructorID > 0){
				query += " where instructorID = ?";
			} else if(instructorfirst != null && instructorlast != null){
				query += " where (instructorFirst, instructorLast) = (?, ?)";
			}
			try{
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query);
				if (instructorID > 0){
					stmt.setInt(1, instructorID);
				} else if(instructorfirst != null && instructorlast != null){
					stmt.setString(1, instructorfirst);
					stmt.setString(2, instructorlast);
				}
				rs = stmt.executeQuery();
				if(rs != null){
					while(rs.next()){
						Instructor item = new Instructor();
						item.setID(rs.getInt("instructorID"));
						item.setNameFirst(rs.getString("instructorFirst"));
						item.setNameLast(rs.getString("instructorLast"));
						item.setPrefBoard(rs.getString("instructorBoard"));
						item.setPrefDesk(rs.getString("instructorDesk"));
						item.setPrefChair(rs.getString("instructorChair"));
						item.setComment(rs.getString("instructorComment"));
						list.add(item);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rs);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
		return list;
	}
	
	public static void deleteInstructor(String semester, int instructorID){
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rset = null;
			String query = "DELETE FROM " + semester + "instructors WHERE instructorID='" + instructorID + "' ";
			try{
				conn = JdbcManager.getConnection();
				stmt = conn.prepareStatement(query);
				stmt.setString(1, Integer.toString(instructorID));		
				stmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rset);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
	}
	
	public static void clearInstructors(String semester) {
		if (Semester.SEMESTERS.contains(semester)){
			Connection conn = null;
			Statement stmt = null;
			ResultSet rset = null;
			String query = "truncate " + semester + "instructors";
			try{
				conn = JdbcManager.getConnection();
				stmt = conn.createStatement();		
				stmt.executeUpdate(query);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JdbcManager.close(rset);
				JdbcManager.close(stmt);
				JdbcManager.close(conn);
			}
		}
	}
}
