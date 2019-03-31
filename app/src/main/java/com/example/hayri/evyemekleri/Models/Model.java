package com.example.hayri.evyemekleri.Models;

public class Model{
	private String message;
	private String userid;
	private int isSuccess;
	private String username;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUserid(){
		return userid;
	}

	public void setIsSuccess(int isSuccess){
		this.isSuccess = isSuccess;
	}

	public int getIsSuccess(){
		return isSuccess;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"Model{" + 
			"message = '" + message + '\'' + 
			",userid = '" + userid + '\'' + 
			",isSuccess = '" + isSuccess + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}
