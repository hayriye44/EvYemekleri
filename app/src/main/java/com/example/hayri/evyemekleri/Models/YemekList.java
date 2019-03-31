package com.example.hayri.evyemekleri.Models;

import java.util.List;

public class YemekList{
	private List<FoodsItem> foods;

	public void setFoods(List<FoodsItem> foods){
		this.foods = foods;
	}

	public List<FoodsItem> getFoods(){
		return foods;
	}

	@Override
 	public String toString(){
		return 
			"YemekList{" + 
			"foods = '" + foods + '\'' + 
			"}";
		}
}