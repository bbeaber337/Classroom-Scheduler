package com.scheduler.valueObjects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Class1 {

	public static final List<String> OURNAMES = Arrays.asList("subject", "catalog", "section", "combo", "classname", 
			"capenrolled","totenrolled","days","starttime","endtime","classroom","capacity","instructorlast",
			"instructorfirst","startdate","enddate","component","classnumber");

	Map<String, String> params;
	
	public Class1(){
		params = new HashMap<String, String>();
		setGroupNumber(0);
	}
	
	//String chairType = null;
	//String boardType = null;
	//String deskType = null;
	
	public Set<String> getParamKeys(){
		return params.keySet();
	}
	
	public String get(String key){
		return params.get(key);
	}
	
	public void set(String key, String value){
		params.put(key, value);
	}
	
	public int getClassID(){
		return Integer.parseInt(params.get("classID"));
		//return class_id;
	}
	public void setClassID(int class_id){
		this.params.put("classID", Integer.toString(class_id));
		//this.class_id = class_id;
	}
	
	public int getClassMon(){
		return Integer.parseInt(params.get("classMon"));
	}
	public void setClassMon(int classMon){
		this.params.put("classMon", Integer.toString(classMon));
	}
	
	public int getClassTues(){
		return Integer.parseInt(params.get("classTues"));
	}
	public void setClassTues(int classTues){
		this.params.put("classTues", Integer.toString(classTues));
	}
	
	public int getClassWed(){
		return Integer.parseInt(params.get("classWed"));
	}
	public void setClassWed(int classWed){
		this.params.put("classWed", Integer.toString(classWed));
	}
	
	public int getClassThurs(){
		return Integer.parseInt(params.get("classThurs"));
	}
	public void setClassThurs(int classThurs){
		this.params.put("classThurs", Integer.toString(classThurs));
	}
	
	public int getClassFri(){
		return Integer.parseInt(params.get("classFri"));
	}
	public void setClassFri(int classFri){
		this.params.put("classFri", Integer.toString(classFri));
	}
	
	public int getClassSat(){
		return Integer.parseInt(params.get("classSat"));
	}
	public void setClassSat(int classSat){
		this.params.put("classSat", Integer.toString(classSat));
	}
	
	public int getClassSun(){
		return Integer.parseInt(params.get("classSun"));
	}
	public void setClassSun(int classSun){
		this.params.put("classSun", Integer.toString(classSun));
	}
	public int getGroupNumber(){
		return Integer.parseInt(params.get("groupNum"));
	}
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
	
	
	
	/*public String getChairType(){
		return chairType;
	}
	public void setChairType(String chairType){
		this.chairType = chairType;
	}
	
	public String getBoardType(){
		return boardType;
	}
	public void setBoardType(String boardType){
		this.boardType = boardType;
	}
	
	public String getDeskType(){
		return deskType;
	}
	public void setDeskType(String deskType){
		this.deskType = deskType;
	}*/
}


