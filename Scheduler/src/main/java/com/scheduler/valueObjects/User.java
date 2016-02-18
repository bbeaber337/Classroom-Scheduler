package com.scheduler.valueObjects;

public class User {
	private int user_id = 0;
	private String username = null;
	private String pass = null;
	private String fName;
	private String lName;
	private String email;
	private int admin = 0;
	

	public String getUserName(){
		return username;
	}
	public void setUserName(String username) {
		this.username = username;
		
	}
	
	public String getUserPassword(){
		return pass;
	}
	public void setUserPassword(String pass){
		this.pass = pass;
	}
	
	public String getUserFirst(){
		return fName;
	}
	public void setUserFirst(String fName){
		this.fName = fName;
	}
	
	public String getUserLast(){
		return lName;
	}
	public void setUserLast(String lName){
		this.lName = lName;
	}
	
	public String getUserEmail(){
		return email;
	}
	public void setUserEmail(String email){
		this.email = email;
	}
	public int getUserAdmin(){
		return admin;
	}
	public void setUserAdmin(int admin){
		this.admin = admin;
	}
	
	public int getUserID(){
		return user_id;
	}
	public void setUserID(int user_id){
		this.user_id = user_id;
	}
	

}
