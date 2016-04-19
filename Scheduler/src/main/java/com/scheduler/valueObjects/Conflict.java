package com.scheduler.valueObjects;

public class Conflict {
	private int class1;
	private int class2;
	private String confType;
	private String value1;
	private String value2;
	
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
	
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
}
