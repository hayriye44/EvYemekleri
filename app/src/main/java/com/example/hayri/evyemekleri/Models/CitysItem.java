package com.example.hayri.evyemekleri.Models;

public class CitysItem{
	private String sehirAdi;
	private String id;

	public void setSehirAdi(String sehirAdi){
		this.sehirAdi = sehirAdi;
	}

	public String getSehirAdi(){
		return sehirAdi;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"CitysItem{" + 
			"sehirAdi = '" + sehirAdi + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
