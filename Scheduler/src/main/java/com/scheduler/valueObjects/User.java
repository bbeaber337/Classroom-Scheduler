package com.scheduler.ValueObjects;

public class User {
	private int user_id = 0;
	private String username = null;
	private String pass = null;
	private String fName;
	private String lName;
	private String email;
	private int admin = 0;
	

	public String getUsername(){
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
		
	}
	
	public String getPass(){
		return pass;
	}
	public void setPass(String pass){
		this.pass = pass;
	}
	
	public String getFName(){
		return fName;
	}
	public void setFName(String fName){
		this.fName = fName;
	}
	
	public String getLName(){
		return lName;
	}
	public void setLName(String lName){
		this.lName = lName;
	}
	
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public int getAdmin(){
		return admin;
	}
	public void setAdmin(int admin){
		this.admin = admin;
	}
	
	public int getUserid(){
		return user_id;
	}
	public void setUserid(int user_id){
		this.user_id = user_id;
	}
	

}
