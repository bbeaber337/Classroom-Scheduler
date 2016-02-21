package com.scheduler.valueObjects;

public class Classroom {

	int roomID = 0;
	int capacity = 0;
	String name = null;
	String desk = null;
	String board = null;
	String chair = null;
	String distLearning = null;
	String roomType = null;
	int projectors = 0;
	
	
	
	public int getRoomID(){
		return roomID;
	}
	public void setRoomID(int roomID){
		this.roomID = roomID;
	}

	public int getRoomCapacity(){
		return capacity;
	}
	public void setRoomCapacity(int capacity){
		this.capacity = capacity;
	}
	
	public String getRoomName(){
		return name;
	}
	public void setRoomName(String name){
		this.name = name;
	}
	
	public String getRoomDeskType(){
		return desk;
	}
	public void setRoomDeskType(String desk){
		this.desk = desk;
	}
	
	public String getRoomBoardType(){
		return board;
	}
	public void setRoomBoardType(String board){
		this.board = board;
	}
	
	public String getRoomChairType(){
		return chair;
	}
	public void setRoomChairType(String chair){
		this.chair = chair;
	}
	
	public String getRoomDistLearning(){
		return distLearning;
	}
	public void setRoomDistLearning(String distLearning){
		this.distLearning = distLearning;
	}
	public String getRoomType(){
		return roomType;
	}
	public void setRoomType(String roomType){
		this.roomType = roomType;
	}
	
	public int getRoomProjectors(){
		return projectors;
	}
	public void setRoomProjectors(int projectors){
		this.projectors = projectors;
	}
	
}
