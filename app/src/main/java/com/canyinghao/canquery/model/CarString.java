package com.canyinghao.canquery.model;

import java.io.Serializable;

public class CarString implements Serializable{
	int id;
	private String city = "";
	private String mes = "";
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

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

}