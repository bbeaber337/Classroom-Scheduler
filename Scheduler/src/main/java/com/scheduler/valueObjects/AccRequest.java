package com.scheduler.valueObjects;

public class AccRequest {
	private int accRequest_id = -1;
	private String fName = null;
	private String lName = null;
	private String username = null;
	private String email = null;
	private String pass = null;
	private String reasoning = null;
	
	


	public String getFName(){
		return fName;
	}
	public void setFName(String fName) {
		this.fName = fName;	
	}
	
	public String getLName(){
		return lName;
	}
	public void setLName(String lName) {
		this.lName = lName;	
	}
	
	
	public String getUsername(){
		return username;
	}
	public void setUsername(String username) {
		this.username = username;	
	}
	
	public String getEmail(){
		return email;
	}
	public void setEmail(String email) {
		this.email = email;	
	}
	
	public String getPass(){
		return pass;
	}
	public void setPass(String pass){
		this.pass = pass;
	}
	
	public String getReasoning(){
		return reasoning;
	}
	public void setReasoning(String reasoning){
		this.reasoning = reasoning;
	}

	public int getAccRequestId(){
		return accRequest_id;
	}
	public void setAccRequestId(int accRequest_id){
		this.accRequest_id = accRequest_id;
	}


}