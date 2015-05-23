package com.canyinghao.canquery.model;

import java.io.Serializable;

public class CityString implements Serializable{
	int id;
	private String city = "";

	private String key="";
	
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}



}