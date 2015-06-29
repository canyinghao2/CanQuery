package com.canyinghao.canquery.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Expressage100Info implements Serializable {

	private String nu = "";
	private String comcontact = "";
	private String companytype = "";
	private String com = "";
	private String status = "";
	private String condition = "";
	private String codenumber = "";
	private String state = "";
	private String message = "";
	private String ischeck = "";
	private String comurl = "";

	private List<Data> data = new ArrayList<Expressage100Info.Data>();

	public String getNu() {
		return nu;
	}

	public void setNu(String nu) {
		this.nu = nu;
	}

	public String getComcontact() {
		return comcontact;
	}

	public void setComcontact(String comcontact) {
		this.comcontact = comcontact;
	}

	public String getCompanytype() {
		return companytype;
	}

	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getCodenumber() {
		return codenumber;
	}

	public void setCodenumber(String codenumber) {
		this.codenumber = codenumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public String getComurl() {
		return comurl;
	}

	public void setComurl(String comurl) {
		this.comurl = comurl;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public class Data implements Serializable{
		private String time = "";
		private String location = "";
		private String context = "";

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getContext() {
			return context;
		}

		public void setContext(String context) {
			this.context = context;
		}

	}

}
