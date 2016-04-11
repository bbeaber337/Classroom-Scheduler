package com.scheduler.services;

import java.util.*;
import java.sql.*;
import java.io.*;

import javax.naming.NamingException;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

import com.scheduler.dbconnector.*;
import com.scheduler.dbconnector.JdbcManager;
import com.scheduler.valueObjects.*;
import com.scheduler.jsp.*;


public class MyServices extends baseJSP {

	//String semester = session.getAttribute("semester").toString();
	
	public MyServices(HttpSession session, HttpServletRequest request, HttpServletResponse response, JspWriter stream) throws Exception {
		super(session, request, response, stream);
		
	}
	
	
// --------------------------------------------------------------------------------------------
//						CLASS FUNCTIONS
//--------------------------------------------------------------------------------------------
	
	
	public List<Class1> getClasses() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Class1> list = new ArrayList<Class1>();
		
		String semester = session.getAttribute("semester").toString();
		
		// Not using '*' because we are not pulling every field
		String query = "SELECT classID, classNumber, classSubject, classCatalog, classSection, classCombination, className, classDescription, classAcadGroup, classCapacity, classEnrolled, classDays, classTimeStart, classTimeEnd, classDateStart, classDateEnd, "
				+ "classInstructFirst, classInstructLast, classRole, classSession, classRoom, classCampus, classMode, classComponent, classCrsAttrVal, classMon, classTues, classWed, classThurs, classFri, classSat FROM " + semester + "classes";

		conn = JdbcManager.getConnection();
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
				item.setClassRole(rs.getString("classRole"));
				item.setClassRoom(rs.getString("classRoom"));
				item.setClassCampus(rs.getString("classCampus"));
				item.setClassMode(rs.getString("classMode"));
				item.setClassComponent(rs.getString("classComponent"));
				item.setClassCrsAttrVal(rs.getString("classCrsAttrVal"));
				item.setClassMon(rs.getInt("classMon"));
				item.setClassTues(rs.getInt("classTues"));
				item.setClassWed(rs.getInt("classWed"));
				item.setClassThurs(rs.getInt("classThurs"));
				item.setClassFri(rs.getInt("classFri"));
				item.setClassSat(rs.getInt("classSat"));
				item.setClassSession(rs.getInt("classSession"));
				list.add(item);
			}
		}
		JdbcManager.close(rs);
		JdbcManager.close(stmt);
		JdbcManager.close(conn);
		return list;
	}
	
	
	public List<Classroom> getClassrooms() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Classroom> list = new ArrayList<Classroom>();
		
		String semester = session.getAttribute("semester").toString();
		
		//rs = conn.runQuery("SELECT classID, classCapacity, classEnrolled, classRoom FROM classes");
		String query = "SELECT roomID, roomCapacity, roomName, roomChairType, roomDeskType, roomBoardType, roomDistLearning, "
				+ "roomType, roomProjectors FROM " + semester + "classrooms";
		conn = JdbcManager.getConnection();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(query);		
		if(rs != null){
			while(rs.next()){
				Classroom item = new Classroom();
				//System.out.println(rs.getString("name"));
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
		JdbcManager.close(rs);
		JdbcManager.close(stmt);
		JdbcManager.close(conn);
		return list;
	}
	
	public Class1 getClassFromID(int classID) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		//Class1 list = new Class1();
		Class1 item = null;
		
		String semester = session.getAttribute("semester").toString();
		
		String query = "SELECT classID, classNumber, classSubject, classCatalog, classSection, classCombination, className, classDescription, classAcadGroup, classCapacity, classEnrolled, classDays, classTimeStart, classTimeEnd, classDateStart, classDateEnd, "
				+ "classInstructFirst, classInstructLast, classRoom, classSession, classCampus, classMode, classComponent, classCrsAttrVal, classMon, classTues, classWed, classThurs, classFri, classSat FROM " + semester + "classes WHERE classID = '" + classID +"' ";

		conn = JdbcManager.getConnection();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(query);
		
		if(rs != null){
			while(rs.next()){
				item = new Class1();
				item.setClassID(classID);
				System.out.printf("\n\nClass ID from MyServices: %d\n\n", item.getClassID());
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
				item.setClassCrsAttrVal(rs.getString("classCrsAttrVal"));
				item.setClassMon(rs.getInt("classMon"));
				item.setClassTues(rs.getInt("classTues"));
				item.setClassWed(rs.getInt("classWed"));
				item.setClassThurs(rs.getInt("classThurs"));
				item.setClassFri(rs.getInt("classFri"));
				item.setClassSat(rs.getInt("classSat"));
				item.setClassSession(rs.getInt("classSession"));
				//item.setChairType(rs.getString("chairType"));
				//item.setBoardType(rs.getString("boardType"));
				//item.setDeskType(rs.getString("deskType"));
				//list.add(item);	
			}
		}

		JdbcManager.close(rs);
		JdbcManager.close(stmt);
		JdbcManager.close(conn);
		return item;
	}
	
	
	public Classroom getClassroomFromID(int classroomID) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		//Class1 list = new Class1();
		Classroom item = null;
		String semester = session.getAttribute("semester").toString();
		
		String query = "SELECT * FROM " + semester + "classrooms WHERE roomID = '" + classroomID +"' ";
		conn = JdbcManager.getConnection();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(query);
		if(rs != null){
			while(rs.next()){
				item = new Classroom();
				item.setRoomID(classroomID);
				System.out.printf("\n\nClassroom ID from MyServices: %d\n\n", item.getRoomID());
				item.setRoomCapacity(rs.getInt("roomCapacity"));
				item.setRoomName(rs.getString("roomName"));
				item.setRoomDeskType(rs.getString("roomDeskType"));
				item.setRoomBoardType(rs.getString("roomBoardType"));
				item.setRoomChairType(rs.getString("roomChairType"));
				item.setRoomType(rs.getString("roomType"));
				item.setRoomDistLearning(rs.getString("roomDistLearning"));
				item.setRoomProjectors(rs.getInt("roomProjectors"));
				//list.add(item);	
			}
		}
		JdbcManager.close(rs);
		JdbcManager.close(stmt);
		JdbcManager.close(conn);
		return item;
	}
	
	
	public int deleteClass(int classID){
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		String semester = session.getAttribute("semester").toString();
		
		String query = "DELETE FROM " + semester + "classes WHERE classID='" + classID + "' ";
		
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e){
			e.printStackTrace();
		}
		finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);	
		}
		return rs;
	}
	
	
	
	public int deleteClassroom(int roomID){
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		String semester = session.getAttribute("semester").toString();
		
		String query = "DELETE FROM " + semester + "classrooms WHERE roomID='" + roomID + "' ";
		
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e){
			e.printStackTrace();
		}
		finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);	
		}
		return rs;
	}
	

	public int deleteDuplicates(){
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		String semester = session.getAttribute("semester").toString();
		
		String query = "DELETE FROM " + semester + "classrooms WHERE roomID NOT IN (SELECT * FROM (SELECT MIN(n.roomID) FROM classrooms n GROUP BY n.roomName) x)";
		
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e){
			e.printStackTrace();
		}
		finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);	
		}
		return rs;
	}
	
	
	public int clearClasses() {
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		String semester = session.getAttribute("semester").toString();
		
		String query = "truncate " + semester + "classes;";
		
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e){
			e.printStackTrace();
		}
		finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);	
		}
		return rs;
	}
	
	
	public int clearClassrooms()
	{
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		String semester = session.getAttribute("semester").toString();
		
		String query = "truncate " + semester + "classrooms;";
		
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e){
			e.printStackTrace();
		}
		finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);	
		}
		return rs;
	}
	
	public int addClass(Class1 c) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		String semester = session.getAttribute("semester").toString();
		
		//System.out.printf("\n\n\nAdding Class: %s\n\n\n", c.getCombo());
		String query = "INSERT INTO " + semester + "classes (classNumber, classSubject, classCatalog, classSection, classCombination, className, classDescription, classAcadGroup, classCapacity, classEnrolled, classDays,";
		query += " classTimeStart, classTimeEnd, classDateStart, classDateEnd, classSession, classInstructFirst, classInstructLast, classRole, classRoom, classCampus, classMode, classCrsAttrVal, classComponent) VALUES( ";
		query += "'" + c.getClassNumber() + "', ";
		query += "'" + c.getClassSubject() + "', ";
		query += "'" + c.getClassCatalog() + "', ";
		query += "'" + c.getClassSection() + "', ";
		query += "'" + c.getClassCombination() + "', ";
		query += "'" + c.getClassName() + "', ";
		query += "'" + c.getClassDescription() + "', ";
		query += "'" + c.getClassAcadGroup() + "', ";
		query += "'" + c.getClassCapacity() + "', ";
		query += "'" + c.getClassEnrolled() + "', ";
		query += "'" + c.getClassDays() + "', ";
		query += "'" + c.getClassTimeStart() + "', ";
		query += "'" + c.getClassTimeEnd() + "', ";
		query += "'" + c.getClassDateStart() + "', ";
		query += "'" + c.getClassDateEnd() + "', ";
		query += "'" + c.getClassSession() + "', ";
		query += "'" + c.getClassInstructFirst() + "', ";
		query += "'" + c.getClassInstructLast() + "', ";
		query += "'" + c.getClassRole() + "', ";
		query += "'" + c.getClassRoom() + "', ";
		query += "'" + c.getClassCampus() + "', ";
		query += "'" + c.getClassMode() + "', ";
		query += "'" + c.getClassCrsAttrVal() + "', ";
		query += "'" + c.getClassComponent() + "'";
		query += ")";		

		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e){
			e.printStackTrace();
		}
		finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);	
		}
		return rs;
	}
	
	
	public int addClassroom(Classroom cr) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		String semester = session.getAttribute("semester").toString();
				
		List<Classroom> list = new ArrayList<Classroom>();
		
		//System.out.printf("\n\n\nAdding Class: %s\n\n\n", c.getCombo());
		String query = "INSERT INTO " + semester + "classrooms (roomCapacity, roomName) VALUES( ";
		query += "'" + cr.getRoomCapacity() + "', ";
		query += "'" + cr.getRoomName() + "'";
		query += ")";
		
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e){
			e.printStackTrace();
		}
		finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);	
		}
		return rs;
	}
	

	public int updateClass(Class1 c) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		String semester = session.getAttribute("semester").toString();
		
		System.out.printf("\n\n\nAdding Class: %d\n\n\n", c.getClassID());
		String query = "UPDATE `" + semester + "classes` SET "
				+ " classNumber=\"" + c.getClassNumber() + "\", "
				+ " classSubject=\"" + c.getClassSubject() + "\", "
				+ " classCatalog=\"" + c.getClassCatalog() + "\", "
				+ " classSection=\"" + c.getClassSection() + "\", "
				+ " classCombination=\"" + c.getClassCombination() + "\", "
				+ " className=\"" + c.getClassName() + "\", "
				+ " classDescription=\"" + c.getClassDescription() + "\", "
				+ " classAcadGroup=\"" + c.getClassAcadGroup() + "\", "
				+ " classCapacity=\"" + c.getClassCapacity() + "\", "
				+ " classEnrolled=\"" + c.getClassEnrolled() + "\", "
				+ " classDays=\"" + c.getClassDays() + "\", "
				+ " classTimeStart=\"" + c.getClassTimeStart() + "\", "
				+ " classTimeEnd=\"" + c.getClassTimeEnd() + "\", "
				+ " classDateStart=\"" + c.getClassDateStart() + "\", "
				+ " classDateEnd=\"" + c.getClassDateEnd() + "\", "
				+ " classInstructFirst=\"" + c.getClassInstructFirst() + "\", "
				+ " classInstructLast=\"" + c.getClassInstructLast() + "\", "
				+ " classRoom=\"" + c.getClassRoom() + "\", "
				+ " classCampus=\"" + c.getClassCampus() + "\", "
				+ " classMode=\"" + c.getClassMode() + "\", "
				+ " classComponent=\"" + c.getClassComponent() + "\" "
				+ " WHERE classID=\"" + c.getClassID() + "\"";				
		//String query = "UPDATE `classes` SET classNbr=\"" + c.getClassNbr() + "\" WHERE class_id=\"" + c.getClassID() + "\"";	
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e){
			e.printStackTrace();
		}
		finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		return rs;
	}
	
	
	public int getClassroomCapacity(String cr) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int cap = 0;
		
		String semester = session.getAttribute("semester").toString();
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT roomCapacity FROM " + semester + "classrooms WHERE roomName = '" + cr +"' ");
			
			if(rs != null){
				while(rs.next()){
					cap = rs.getInt("roomCapacity");
				}
			}
		}
		catch (NamingException e){
			e.printStackTrace();
		}
		finally{
			JdbcManager.close(rs);
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		return cap;
		
	}
	
	
	public int updateClassroom(Classroom cr) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		String semester = session.getAttribute("semester").toString();
		
		System.out.printf("\n\n\nAdding Classroom: %d\n\n\n", cr.getRoomID());
		String query = "UPDATE `" + semester + "classrooms` SET "
				+ " roomName=\"" + cr.getRoomName() + "\", "
				+ " roomCapacity=\"" + cr.getRoomCapacity() + "\", "
				+ " roomType=\"" + cr.getRoomType() + "\", "
				+ " roomChairType=\"" + cr.getRoomChairType() + "\", "
				+ " roomDeskType=\"" + cr.getRoomDeskType() + "\", "
				+ " roomBoardType=\"" + cr.getRoomBoardType() + "\", "
				+ " roomDistLearning=\"" + cr.getRoomDistLearning() + "\", "
				+ " roomProjectors=\"" + cr.getRoomProjectors() + "\" "
				+ " WHERE roomID=\"" + cr.getRoomID() + "\"";				
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e){
			e.printStackTrace();
		}
		finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		return rs;
	}
	
	
	
	public int updateClassDays(Class1 c) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		String semester = session.getAttribute("semester").toString();
		
		System.out.printf("\n\n\nAdding Class: %d\n\n\n", c.getClassID());
		String query = "UPDATE `" + semester + "classes` SET "
				+ " classMon=\"" + c.getClassMon() + "\", "
				+ " classTues=\"" + c.getClassTues() + "\", "
				+ " classWed=\"" + c.getClassWed() + "\", "
				+ " classThurs=\"" + c.getClassThurs() + "\", "
				+ " classFri=\"" + c.getClassFri() + "\", "
				+ " classSat=\"" + c.getClassSat() + "\" "
				+ " WHERE classID=\"" + c.getClassID() + "\"";				
		//String query = "UPDATE `classes` SET classNbr=\"" + c.getClassNbr() + "\" WHERE class_id=\"" + c.getClassID() + "\"";	
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e){
			e.printStackTrace();
		}
		finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		return rs;
	}
	

	
// --------------------------------------------------------------------------------------------
//								USER FUNCTIONS
//--------------------------------------------------------------------------------------------
	
	
	public int addAccount(User set){
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		String query = "INSERT INTO users (userName, userPassword, userFirst, userLast, userEmail, userAdmin) VALUES( ";
		query += "'" + set.getUserName() + "', ";
		query += "'" + set.getUserPassword() + "', ";
		query += "'" + set.getUserFirst() + "', ";
		query += "'" + set.getUserLast() + "', ";
		query += "'" + set.getUserEmail() + "', ";
		query += "'" + set.getUserAdmin() + "'";
		query += ")";
		
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e){
			e.printStackTrace();
		}
		finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		return rs;
	}
	
	
	public List<User> getUsers() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<User> list = new ArrayList<User>();
		
		try{
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT userName, userFirst, userLast, userEmail, userAdmin FROM users");
			
			if(rs != null){
				while(rs.next()){
					User item = new User();
					//System.out.println(rs.getString("username"));
					item.setUserName(rs.getString("userName"));
					item.setUserFirst(rs.getString("userFirst"));
					item.setUserLast(rs.getString("userLast"));
					item.setUserEmail(rs.getString("userEmail"));
					item.setUserAdmin(rs.getInt("userAdmin"));
					list.add(item);
				}
			}
		}
		finally {
			JdbcManager.close(rs);
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		return list;
	}
	
	
	public List<AccRequest> getAccRequests() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<AccRequest> list = new ArrayList<AccRequest>();
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT fName, lName, email, username, pass, reasoning FROM accRequests");
			
			if(rs != null){
				while(rs.next()){
					AccRequest item = new AccRequest();
					System.out.println(rs.getString("username"));
					item.setFName(rs.getString("fName"));
					item.setLName(rs.getString("lName"));
					item.setEmail(rs.getString("email"));
					item.setUsername(rs.getString("username"));
					item.setPass(rs.getString("pass"));
					item.setReasoning(rs.getString("reasoning"));
					list.add(item);
				}
			}
		}
		finally{
			JdbcManager.close(rs);
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		return list;
	}
	
	
	public int deleteAccount (String username) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		String query = "DELETE FROM users WHERE userName='" +  username +"' ";
		
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		return rs;
	}
	
	
	public boolean validateLogin(String user, String pass) throws Exception {
			
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean retval = false;
		String query = "SELECT userName FROM users WHERE userName='" + user + "' AND userPassword='" + pass + "'";
		
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		
			retval = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		return retval;
	}
	
	public boolean adminStatus(String user) throws Exception {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean retval = false;
		String query = "SELECT userAdmin FROM users WHERE userName='" + user + "' ";		
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		
		
		if(rs.next()){
			if(rs.getInt("userAdmin") == 1){
				retval = true;
			}
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		return retval;
	}
	

	
	public int insertAccRequest(AccRequest ar){
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		String query = "INSERT INTO accRequests (fName, lName, email, username, pass, reasoning) VALUES ( ";
		query += "'" + ar.getFName() + "', ";
		query += "'" + ar.getLName() + "', ";
		query += "'" + ar.getEmail() + "', ";
		query += "'" + ar.getUsername() + "', ";
		query += "'" + ar.getPass() + "', ";
		query += "'" + ar.getReasoning() + "'";
		query += ")";
		
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e){
			e.printStackTrace();
		}
		finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		return rs;
	}
	
	
	public int deleteAccRequest(String username) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		String query = "DELETE FROM accRequests WHERE username='" + username +"' ";
		
		try {
			conn = JdbcManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e){
			e.printStackTrace();
		}
		finally {
			JdbcManager.close(stmt);
			JdbcManager.close(conn);
		}
		return rs;
	}
	
	public static void initDB(){
		
	}
}