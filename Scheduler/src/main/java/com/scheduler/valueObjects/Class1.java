package com.scheduler.valueObjects;

public class Class1 {

	int class_id = 0;
	int classNbr = 0;
	String subject = null;
	String catalog = null;
	String section = null;
	String combo = null;
	String descr = null;
	
	
	public int getClassid(){
		return class_id;
	}
	public void setClassid(int class_id){
		this.class_id = class_id;
	}
	
	public int getClassNrb(){
		return classNbr;
	}
	public void setClassNbr(int classNbr){
		this.classNbr = classNbr;
	}
	
	public String getSubject(){
		return subject;
	}
	public void setSubject(String subject){
		this.subject = subject;
	}
	
	public String getCatalog(){
		return catalog;
	}
	public void setCatalog(String catalog){
		this.catalog = catalog;
	}
	
	public String getSection(){
		return section;
	}
	public void setSection(String section){
		this.section = section;
	}
	
	public String getCombo(){
		return combo;
	}
	public void setCombo(String combo){
		this.combo = combo;
	}
	
	public String getDescr(){
		return descr;
	}
	public void setDescr(String descr){
		this.descr = descr;
	}
}
