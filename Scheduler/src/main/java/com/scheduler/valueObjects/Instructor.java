package com.scheduler.valueObjects;

public class Instructor {
	private int instructID = -1;
	private String nameFirst = "";
	private String nameLast = "";
	private String prefBoard = "";
	private String prefChair = "";
	private String prefDesk = "";
	private String comment = "";
	
	public int getInstructID() {
		return instructID;
	}
	public void setInstructID(int instructID) {
		this.instructID = instructID;
	}
	
	public int getID() {
		return instructID;
	}
	
	public void setID(int id){
		this.instructID = id;
	}
	
	public String getNameFirst() {
		return nameFirst;
	}
	public void setNameFirst(String nameFirst) {
		this.nameFirst = nameFirst;
	}
	
	public String getNameLast() {
		return nameLast;
	}
	public void setNameLast(String nameLast) {
		this.nameLast = nameLast;
	}
	
	public String getPrefBoard() {
		return prefBoard;
	}
	public void setPrefBoard(String prefBoard) {
		this.prefBoard = prefBoard;
	}
	
	public String getPrefChair() {
		return prefChair;
	}
	public void setPrefChair(String prefChair) {
		this.prefChair = prefChair;
	}
	
	public String getPrefDesk() {
		return prefDesk;
	}
	public void setPrefDesk(String prefDesk) {
		this.prefDesk = prefDesk;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
