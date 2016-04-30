package com.scheduler.valueObjects;

public class Conflict {
	public static String TIME_CONFLICT = "Time Overlap";
	public static String CLASSROOM_CAPACITY_CONFLICT = "Group Over Classroom Capacity";
	public static String NO_TEACHER = "No Teacher Assigned";
	public static String NO_CLASSROOM = "No Classroom Assigned";
	public static String CAPACITY_CONFLICT = "Over Class Capacity";
	
	private int class1;
	private int class2;
	private String confType;
	
	public int getClass1() {
		return class1;
	}
	public void setClass1(int class1) {
		this.class1 = class1;
	}
	
	public int getClass2() {
		return class2;
	}
	public void setClass2(int class2) {
		this.class2 = class2;
	}
	
	public String getConfType() {
		return confType;
	}
	public void setConfType(String confType) {
		this.confType = confType;
	}
	
}
