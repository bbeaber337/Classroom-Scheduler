package com.scheduler.valueObjects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;
/**
 * A Class for representing a class
 *
 */
public class Class1 {

	public static final List<String> OURNAMES = Arrays.asList("subject", "catalog", "section", "combo", "classname", 
			"capenrolled","totenrolled","days","starttime","endtime","classroom","capacity","instructorlast",
			"instructorfirst","startdate","enddate","component","classnumber");

	Map<String, String> params;
	
	public Class1(){
		params = new HashMap<String, String>();
		setGroupNumber(0);
	}
	/**
	 * get all key values for the Map
	 * @return a the Key Set of the Map
	 */
	public Set<String> getParamKeys(){
		return params.keySet();
	}
	/**
	 * @see HashMap#get()
	 */
	public String get(String key){
		return params.get(key);
	}

	/**
	 * @see HashMap#put()
	 */
	public void set(String key, String value){
		params.put(key, value);
	}
	/**
	 * get the class ID
	 * @return the class ID
	 */
	public int getClassID(){
		return Integer.parseInt(params.get("classID"));
		//return class_id;
	}
	/**
	 * set the class ID
	 * @param class_id - the class ID
	 */
	public void setClassID(int class_id){
		this.params.put("classID", Integer.toString(class_id));
		//this.class_id = class_id;
	}
	/**
	 * check if the class is on a Monday
	 * @return 1 if class is on a Monday, otherwise 0
	 */
	public int getClassMon(){
		return Integer.parseInt(params.get("classMon"));
	}
	/**
	 * set a class value for if it meets on a Monday
	 * @param classMon - 1 if class is on a Monday, otherwise 0
	 */
	public void setClassMon(int classMon){
		this.params.put("classMon", Integer.toString(classMon));
	}

	/**
	 * check if the class is on a Tuesday
	 * @return 1 if class is on a Tuesday, otherwise 0
	 */
	public int getClassTues(){
		return Integer.parseInt(params.get("classTues"));
	}
	/**
	 * set a class value for if it meets on a Tuesday
	 * @param classTues - 1 if class is on a Tuesday, otherwise 0
	 */
	public void setClassTues(int classTues){
		this.params.put("classTues", Integer.toString(classTues));
	}

	/**
	 * check if the class is on a Wednesday
	 * @return 1 if class is on a Wednesday, otherwise 0
	 */
	public int getClassWed(){
		return Integer.parseInt(params.get("classWed"));
	}
	/**
	 * set a class value for if it meets on a Wednesday
	 * @param classWed - 1 if class is on a Wednesday, otherwise 0
	 */
	public void setClassWed(int classWed){
		this.params.put("classWed", Integer.toString(classWed));
	}

	/**
	 * check if the class is on a Thursday
	 * @return 1 if class is on a Thursday, otherwise 0
	 */
	public int getClassThurs(){
		return Integer.parseInt(params.get("classThurs"));
	}
	/**
	 * set a class value for if it meets on a Thursday
	 * @param classThurs - 1 if class is on a Thursday, otherwise 0
	 */
	public void setClassThurs(int classThurs){
		this.params.put("classThurs", Integer.toString(classThurs));
	}

	/**
	 * check if the class is on a Friday
	 * @return 1 if class is on a Friday, otherwise 0
	 */
	public int getClassFri(){
		return Integer.parseInt(params.get("classFri"));
	}
	/**
	 * set a class value for if it meets on a Friday
	 * @param classFri - 1 if class is on a Friday, otherwise 0
	 */
	public void setClassFri(int classFri){
		this.params.put("classFri", Integer.toString(classFri));
	}

	/**
	 * check if the class is on a Saturday
	 * @return 1 if class is on a Saturday, otherwise 0
	 */
	public int getClassSat(){
		return Integer.parseInt(params.get("classSat"));
	}
	/**
	 * set a class value for if it meets on a Saturday
	 * @param classSat - 1 if class is on a Saturday, otherwise 0
	 */
	public void setClassSat(int classSat){
		this.params.put("classSat", Integer.toString(classSat));
	}

	/**
	 * check if the class is on a Sunday
	 * @return 1 if class is on a Sunday, otherwise 0
	 */
	public int getClassSun(){
		return Integer.parseInt(params.get("classSun"));
	}
	/**
	 * set a class value for if it meets on a Sunday
	 * @param classSun - 1 if class is on a Sunday, otherwise 0
	 */
	public void setClassSun(int classSun){
		this.params.put("classSun", Integer.toString(classSun));
	}
	
	/**
	 * get the class's group number
	 * @return the group number for this class
	 */
	public int getGroupNumber(){
		return Integer.parseInt(params.get("groupNum"));
	}
	/**
	 * set the class's group number
	 * @param num - the group number for this class
	 */
	public void setGroupNumber(int num){
		this.params.put("groupNum", Integer.toString(num));
	}
	
	@Override
	public boolean equals( Object object ) {
		return this.getClassID() == ((Class1)object).getClassID();
	}
	
	public void print(){
		for (String k : params.keySet()){
			System.out.println(k + ": " + params.get(k));
		}
	}
}


