package com.canyinghao.canquery.model;

import java.io.Serializable;

public class ExpressageInfo implements Serializable{

	private String datetime="";
	private String remark="";
	private String zone="";
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	
	
	
	
}
