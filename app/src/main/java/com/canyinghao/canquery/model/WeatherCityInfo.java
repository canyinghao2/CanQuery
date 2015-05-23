package com.canyinghao.canquery.model;

import java.io.Serializable;
import java.util.List;

public class WeatherCityInfo implements Serializable{

	private int id;
	private String city="";
	private int rid;
	private String weather="";
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	
	
}
