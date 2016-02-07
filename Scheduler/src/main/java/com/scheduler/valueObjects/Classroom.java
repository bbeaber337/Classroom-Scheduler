package com.scheduler.valueObjects;

public class Classroom {

	int classroom_id = 0;
	int capacity = 0;
	String room = null;
	String desk = null;
	String board = null;
	String chair = null;
	
	
	public int getClassroomId(){
		return classroom_id;
	}
	public void setClassroomId(int classroom_id){
		this.classroom_id = classroom_id;
	}

	public int getCapacity(){
		return capacity;
	}
	public void setCapacity(int capacity){
		this.capacity = capacity;
	}
	
	public String getRoom(){
		return room;
	}
	public void setRoom(String room){
		this.room = room;
	}
	
	public String getDesk(){
		return desk;
	}
	public void setDesk(String desk){
		this.desk = desk;
	}
	
	public String getBoard(){
		return board;
	}
	public void setBoard(String board){
		this.board = board;
	}
	
	public String getChair(){
		return chair;
	}
	public void setChair(String chair){
		this.chair = chair;
	}
	
	 
}
