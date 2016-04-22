package com.scheduler.valueObjects;

import java.util.HashMap;
import java.util.Map;

public class Class1 {

	Map<String, String> params = new HashMap<String, String>();
	int classMon = 0;
	int classTues = 0;
	int classWed = 0;
	int classThurs = 0;
	int classFri = 0;
	int classSat = 0;
	int classSun = 0;
	int groupNumber = 0;
	
	public Class1(){
		this.setClassID(-1);
		this.setClassNumber(0);
		this.setClassCapacity(0);
		this.setClassEnrolled(0);
		this.setClassSession(0);
	}
	//String chairType = null;
	//String boardType = null;
	//String deskType = null;
	public String get(String key){
		return params.get(key);
	}
	
	public void set(String key, String value){
		params.put(key, value);
	}
	
	public int getClassID(){
		return Integer.parseInt(params.get("lClassID"));
		//return class_id;
	}
	public void setClassID(int class_id){
		this.params.put("lClassID", Integer.toString(class_id));
		//this.class_id = class_id;
	}
	
	public int getClassSession(){
		return Integer.parseInt(params.get("lClassSession"));
		//return session;
	}
	public void setClassSession(int session){
		this.params.put("lClassSession", Integer.toString(session));
		//this.session = session;
	}


	public String getClassComponent(){
		return params.get("lClassComponent");
		//return comp;
	}
	public void setClassComponent(String comp){
		this.params.put("lClassComponent", comp);
		//this.comp = comp;
	}
	
	public String getClassMode(){
		return params.get("lClassMode");
		// return mode;
	}
	public void setClassMode(String mode){
		this.params.put("lClassMode", mode);
		//this.mode = mode;
	}
	
	public String getClassCampus(){
		return params.get("lClassCampus");
		// return campus;
	}
	public void setClassCampus(String campus){
		this.params.put("lClassCampus", campus);
		//this.campus = campus;
	}
	
	public String getClassRoom(){
		return params.get("lClassRoom");
		//return facil;
	}
	public void setClassRoom(String facil){
		this.params.put("lClassRoom", facil);
		//this.facil = facil;
	}
	
	public String getClassInstructLast(){
		return params.get("lClassInstructLast");
		//return lName;
	}
	public void setClassInstructLast(String lName){
		this.params.put("lClassInstructLast", lName);
		//this.lName = lName;
	}
	
	public String getClassRole(){
		return params.get("lClassRole");
		//return classRole;
	}
	public void setClassRole(String role){
		this.params.put("lClassRole", role);
		//this.role = role;
	}
	
	public String getClassInstructFirst(){
		return params.get("lClassInstructFirst");
		//return fName;
	}
	public void setClassInstructFirst(String fName){
		this.params.put("lClassInstructFirst", fName);
		//this.fName = fName;
	}
	
	public String getClassDateEnd(){
		return params.get("lClassDateEnd");
		//return eDate;
	}
	public void setClassDateEnd(String eDate){
		this.params.put("lClassDateEnd", eDate);
		//this.eDate = eDate;
	}
	
	public String getClassDateStart(){
		return params.get("lClassDateStart");
		//return sDate;
	}
	public void setClassDateStart(String sDate){
		this.params.put("lClassDateStart", sDate);
		//this.sDate = sDate;
	}
	
	public String getClassTimeEnd(){
		return params.get("lClassTimeEnd");
		//return eTime;
	}
	public void setClassTimeEnd(String eTime){
		this.params.put("lClassTimeEnd", eTime);
		//this.eTime = eTime;
	}
	
	public String getClassTimeStart(){
		return params.get("ClassTimeStart");
		//return sTime;
	}
	public void setClassTimeStart(String sTime){
		this.params.put("ClassTimeStart", sTime);
		//this.sTime = sTime;
	}
	
	public String getClassDays(){
		return this.params.get("lClassDays");
		//return day;
	}
	public void setClassDays(String day){
		this.params.put("lClassDays", day);
		//this.day = day;
	}
	
	public int getClassEnrolled(){
		return Integer.parseInt(params.get("lClassEnrolled"));
		//return enrolled;
	}
	public void setClassEnrolled(int enrolled){
		this.params.put("lClassEnrolled", Integer.toString(enrolled));
		//this.enrolled = enrolled;
	}
	
	public int getClassCapacity(){
		return Integer.parseInt(params.get("lClassCapacity"));
		//return capacity;
	}
	public void setClassCapacity(int capacity){
		this.params.put("lClassCapacity", Integer.toString(capacity));
		//this.capacity = capacity;
	}
	
	public String getClassAcadGroup(){
		return params.get("lClassAcadGroup");
		//return acadGroup;
	}
	public void setClassAcadGroup(String acadGroup){
		this.params.put("lClassAcadGroup", acadGroup);
		//this.acadGroup = acadGroup;
	}
	
	public String getClassDescription(){
		return params.get("lClassDescription");
		//return description;
	}
	public void setClassDescription(String description){
		this.params.put("lClassDescription", description);
		//this.description = description;
	}
	
	public int getClassNumber(){
		return Integer.parseInt(params.get("lClassNbr"));
		//return classNbr;
	}
	public void setClassNumber(int classNbr){
		this.params.put("lClassNbr", Integer.toString(classNbr));
		//this.classNbr = classNbr;
	}
	
	public String getClassSubject(){
		return params.get("lClassSubject");
		//return subject;
	}
	public void setClassSubject(String subject){
		this.params.put("lClassSubject", subject);
		//this.subject = subject;
	}
	
	public String getClassCatalog(){
		return params.get("lClassCatalog");
		//return catalog;
	}
	public void setClassCatalog(String catalog){
		this.params.put("lClassCatalog", catalog);
		//this.catalog = catalog;
	}
	
	public String getClassSection(){
		return params.get("lClassSection");
		//return section;
	}
	public void setClassSection(String section){
		this.params.put("lClassSection", section);
		//this.section = section;
	}
	
	public String getClassCombination(){
		return params.get("lClassCombination");
		//return combo;
	}
	public void setClassCombination(String combo){
		this.params.put("lClassCombination", combo);
		//this.combo = combo;
	}
	
	public String getClassName(){
		return params.get("lClassName");
		//return name;
	}
	public void setClassName(String name){
		this.params.put("lClassName", name);
		//this.name = name;
	}
	
	public String getClassCrsAttrVal(){
		return params.get("lClassCrsAttrVal");
		//return classCrsAttrVal;
	}
	public void setClassCrsAttrVal(String classCrsAttrVal){
		this.params.put("lClassCrsAttrVal", classCrsAttrVal);
		//this.classCrsAttrVal = classCrsAttrVal;
	}
	
	public int getClassMon(){
		return classMon;
	}
	public void setClassMon(int classMon){
		this.classMon = classMon;
	}
	
	public int getClassTues(){
		return classTues;
	}
	public void setClassTues(int classTues){
		this.classTues = classTues;
	}
	
	public int getClassWed(){
		return classWed;
	}
	public void setClassWed(int classWed){
		this.classWed = classWed;
	}
	
	public int getClassThurs(){
		return classThurs;
	}
	public void setClassThurs(int classThurs){
		this.classThurs = classThurs;
	}
	
	public int getClassFri(){
		return classFri;
	}
	public void setClassFri(int classFri){
		this.classFri = classFri;
	}
	
	public int getClassSat(){
		return classSat;
	}
	public void setClassSat(int classSat){
		this.classSat = classSat;
	}
	
	public int getClassSun(){
		return classSun;
	}
	public void setClassSun(int classSun){
		this.classSun = classSun;
	}
	public int getGroupNumber(){
		return groupNumber;
	}
	public void setGroupNumber(int num){
		this.groupNumber = num;
	}
	
	
	@Override
	public boolean equals( Object object ) {
		return this.getClassID() == ((Class1)object).getClassID();
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


