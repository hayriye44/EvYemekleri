package com.example.hayri.evyemekleri.Models;

public class YemekEkle{
	private String message;
	private int isSuccess;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setIsSuccess(int isSuccess){
		this.isSuccess = isSuccess;
	}

	public int getIsSuccess(){
		return isSuccess;
	}

	@Override
 	public String toString(){
		return 
			"YemekEkle{" + 
			"message = '" + message + '\'' + 
			",isSuccess = '" + isSuccess + '\'' + 
			"}";
		}
}
