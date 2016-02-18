package com.scheduler.valueObjects;

public class Instructor {
	private String nameFirst = "";
	private String nameLast = "";
	private String prefBoard = "";
	private String prefChair = "";
	private String prefDesk = "";
	private String comment = "";
	
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
