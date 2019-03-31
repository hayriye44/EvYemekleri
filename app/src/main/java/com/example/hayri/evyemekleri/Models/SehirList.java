package com.example.hayri.evyemekleri.Models;

import java.util.List;

public class SehirList{
	private List<CitysItem> citys;

	public void setCitys(List<CitysItem> citys){
		this.citys = citys;
	}

	public List<CitysItem> getCitys(){
		return citys;
	}

	@Override
 	public String toString(){
		return 
			"SehirList{" + 
			"citys = '" + citys + '\'' + 
			"}";
		}
}