package com.sohamkamani.street_food;

public class FoodMenuItem {
	
	int price;
	String name;
	
	public FoodMenuItem(String name , int price){
		this.name = name;
		this.price=price;
	}
	
	public String toString(){
		return this.name + "," + Integer.toString(this.price) ;
	}

}
