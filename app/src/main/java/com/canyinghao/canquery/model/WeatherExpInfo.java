package com.canyinghao.canquery.model;

import java.io.Serializable;
import java.util.List;

public class WeatherExpInfo implements Serializable{

	private int rid;
	private String title="";
	private List<String> list;
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public WeatherExpInfo(int rid, String title, List<String> list) {
		super();
		this.rid = rid;
		this.title = title;
		this.list = list;
	}
	public WeatherExpInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
