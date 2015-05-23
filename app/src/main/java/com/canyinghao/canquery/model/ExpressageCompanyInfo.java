package com.canyinghao.canquery.model;

import java.io.Serializable;

public class ExpressageCompanyInfo implements Serializable{

	private int id;
	private String com="";
	private String no="";
	private int type;
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public ExpressageCompanyInfo(String com, String no) {
		super();
		this.com = com;
		this.no = no;
	}
	public ExpressageCompanyInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
