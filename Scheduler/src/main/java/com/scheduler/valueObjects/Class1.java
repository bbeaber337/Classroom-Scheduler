package com.scheduler.valueObjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Class1 {

	Map<String, String> params = new HashMap<String, String>();
	
	public Class1(){
		this.setClassID(-1);
	}
	//String chairType = null;
	//String boardType = null;
	//String deskType = null;
	public String get(String key){
		return params.get(key);
	}
	
	public Set<String> getParamKeys(){
		return params.keySet();
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
	
	public String getClassRoom(){
		return params.get("classroom");
		//return facil;
	}
	public void setClassRoom(String facil){
		this.params.put("classroom", facil);
		//this.facil = facil;
	}
	
	public String getClassInstructLast(){
		return params.get("instructorlast");
		//return lName;
	}
	public void setClassInstructLast(String lName){
		this.params.put("instructorlast", lName);
		//this.lName = lName;
	}
	
	public String getClassInstructFirst(){
		return params.get("instructorfirst");
		//return fName;
	}
	public void setClassInstructFirst(String fName){
		this.params.put("instructorfirst", fName);
		//this.fName = fName;
	}
	
	public String getClassDateEnd(){
		return params.get("enddate");
		//return eDate;
	}
	public void setClassDateEnd(String eDate){
		this.params.put("enddate", eDate);
		//this.eDate = eDate;
	}
	
	public String getClassDateStart(){
		return params.get("startdate");
		//return sDate;
	}
	public void setClassDateStart(String sDate){
		this.params.put("startdate", sDate);
		//this.sDate = sDate;
	}
	
	public String getClassTimeEnd(){
		return params.get("endtime");
		//return eTime;
	}
	public void setClassTimeEnd(String eTime){
		this.params.put("endtime", eTime);
		//this.eTime = eTime;
	}
	
	public String getClassTimeStart(){
		return params.get("starttime");
		//return sTime;
	}
	public void setClassTimeStart(String sTime){
		this.params.put("starttime", sTime);
		//this.sTime = sTime;
	}
	
	public String getClassDays(){
		return this.params.get("days");
		//return day;
	}
	public void setClassDays(String day){
		this.params.put("days", day);
		//this.day = day;
	}
	
	public int getClassEnrolled(){
		return Integer.parseInt(params.get("totenrolled"));
		//return enrolled;
	}
	public void setClassEnrolled(int enrolled){
		this.params.put("totenrolled", Integer.toString(enrolled));
		//this.enrolled = enrolled;
	}
	
	public int getClassCapacity(){
		return Integer.parseInt(params.get("capacity"));
		//return capacity;
	}
	public void setClassCapacity(int capacity){
		this.params.put("capacity", Integer.toString(capacity));
		//this.capacity = capacity;
	}
	
	public String getClassSubject(){
		return params.get("subject");
		//return subject;
	}
	public void setClassSubject(String subject){
		this.params.put("subject", subject);
		//this.subject = subject;
	}
	
	public String getClassCatalog(){
		return params.get("catalog");
		//return catalog;
	}
	public void setClassCatalog(String catalog){
		this.params.put("catalog", catalog);
		//this.catalog = catalog;
	}
	
	public String getClassSection(){
		return params.get("section");
		//return section;
	}
	public void setClassSection(String section){
		this.params.put("section", section);
		//this.section = section;
	}
	
	public String getClassCombination(){
		return params.get("combo");
		//return combo;
	}
	public void setClassCombination(String combo){
		this.params.put("combo", combo);
		//this.combo = combo;
	}
	
	public String getClassName(){
		return params.get("classname");
		//return name;
	}
	public void setClassName(String name){
		this.params.put("classname", name);
		//this.name = name;
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
		return Integer.parseInt(params.get("groupnum"));
	}
	public void setGroupNumber(int num){
		this.params.put("groupnum", Integer.toString(num));
	}
	
	@Override
	public boolean equals( Object object ) {
		return this.getClassID() == ((Class1)object).getClassID();
	}
	
	public void print(){
		for (String k : params.keySet()){
			System.out.println(k + ": " + params.get(k));
			System.out.println("Group Number: " + getGroupNumber());
			System.out.printf("Mon Tues Wed Thur Fri Sat Sun: %d %d %d %d %d %d %d", 
					getClassMon(),getClassTues(),getClassWed(),getClassThurs(),getClassFri(),getClassSat(),getClassSun());
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


