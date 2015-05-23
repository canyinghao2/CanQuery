package com.canyinghao.canquery.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarResultInfo implements Serializable{

	private String province="";
	private String city="";
	private String hphm="";
	private String hpzl="";
	private String classno="";
	private String engineno="";
	private List<CarWZInfo> lists=new ArrayList<CarResultInfo.CarWZInfo>();
	
	
	
	public String getProvince() {
		return province;
	}



	public void setProvince(String province) {
		this.province = province;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getHphm() {
		return hphm;
	}



	public void setHphm(String hphm) {
		this.hphm = hphm;
	}



	public String getHpzl() {
		return hpzl;
	}



	public void setHpzl(String hpzl) {
		this.hpzl = hpzl;
	}



	



	public List<CarWZInfo> getLists() {
		return lists;
	}



	public void setLists(List<CarWZInfo> lists) {
		this.lists = lists;
	}







	public String getClassno() {
		return classno;
	}



	public void setClassno(String classno) {
		this.classno = classno;
	}



	public String getEngineno() {
		return engineno;
	}



	public void setEngineno(String engineno) {
		this.engineno = engineno;
	}







	public class CarWZInfo implements Serializable{
		
		private String date="";
		private String area="";
		private String act="";
		private String code="";
		private String fen="";
		private String money="";
		private String handled="";
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getAct() {
			return act;
		}
		public void setAct(String act) {
			this.act = act;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getFen() {
			return fen;
		}
		public void setFen(String fen) {
			this.fen = fen;
		}
		public String getMoney() {
			return money;
		}
		public void setMoney(String money) {
			this.money = money;
		}
		public String getHandled() {
			return handled;
		}
		public void setHandled(String handled) {
			this.handled = handled;
		}
		
		
	}
	
	
	
	
	
	
	
	
}
