package com.scheduler.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.scheduler.valueObjects.Class1;

public class ClassUtils {
	
	
	public Class1 rowToVO ( Statement row ) {
		Class1 classVO = null;
		ResultSet rs = null;
		
		try {
			classVO = new Class1();
			rs = row.getResultSet();
			
			System.out.printf("\n\nClass ID from MyServices: %d\n\n", rs.getInt("classID"));
			
			classVO.setClassID(rs.getInt("classID"));
			classVO.setClassNumber(rs.getInt("classNumber"));
			classVO.setClassSubject(rs.getString("classSubject"));
			classVO.setClassCatalog(rs.getString("classCatalog"));
			classVO.setClassSection(rs.getString("classSection"));
			classVO.setClassCombination(rs.getString("classCombination"));
			classVO.setClassName(rs.getString("className"));
			classVO.setClassDescription(rs.getString("classDescription"));
			classVO.setClassAcadGroup(rs.getString("classAcadGroup"));
			classVO.setClassCapacity(rs.getInt("classCapacity"));
			classVO.setClassEnrolled(rs.getInt("classEnrolled"));
			classVO.setClassDays(rs.getString("classDays"));
			classVO.setClassTimeStart(rs.getString("classTimeStart"));
			classVO.setClassTimeEnd(rs.getString("classTimeEnd"));
			classVO.setClassDateStart(rs.getString("classDateStart"));
			classVO.setClassDateEnd(rs.getString("classDateEnd"));
			classVO.setClassInstructLast(rs.getString("classInstructLast"));
			classVO.setClassInstructFirst(rs.getString("classInstructFirst"));
			classVO.setClassRoom(rs.getString("classRoom"));
			classVO.setClassCampus(rs.getString("classCampus"));
			classVO.setClassMode(rs.getString("classMode"));
			classVO.setClassComponent(rs.getString("classComponent"));
			classVO.setClassCrsAttrVal(rs.getString("classCrsAttrVal"));
			classVO.setClassMon(rs.getInt("classMon"));
			classVO.setClassTues(rs.getInt("classTues"));
			classVO.setClassWed(rs.getInt("classWed"));
			classVO.setClassThurs(rs.getInt("classThurs"));
			classVO.setClassFri(rs.getInt("classFri"));
			classVO.setClassSat(rs.getInt("classSat"));
		}
		catch (SQLException e) {
			System.out.println("SQL Error");
			e.printStackTrace();
			return null;
		}
		
		
		return classVO;
	}
}
