package com.canyinghao.canquery.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarCityInfo implements Serializable{

	private String province="";
	private String province_code="";
	private List<CityInfo> citys=new ArrayList<CarCityInfo.CityInfo>();
	
	
	
	public String getProvince() {
		return province;
	}



	public void setProvince(String province) {
		this.province = province;
	}



	public String getProvince_code() {
		return province_code;
	}



	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}



	public List<CityInfo> getCitys() {
		return citys;
	}



	public void setCitys(List<CityInfo> citys) {
		this.citys = citys;
	}



	public class CityInfo  implements Serializable{
		
		private String city_name="";
		private String city_code="";
		private String abbr="";
		private String engine="";
		private String engineno="";
		private String classa="";
		private String classno="";
		private String regist="";
		private String registno="";
		public String getCity_name() {
			return city_name;
		}
		public void setCity_name(String city_name) {
			this.city_name = city_name;
		}
		public String getCity_code() {
			return city_code;
		}
		public void setCity_code(String city_code) {
			this.city_code = city_code;
		}
		public String getAbbr() {
			return abbr;
		}
		public void setAbbr(String abbr) {
			this.abbr = abbr;
		}
		public String getEngine() {
			return engine;
		}
		public void setEngine(String engine) {
			this.engine = engine;
		}
		public String getEngineno() {
			return engineno;
		}
		public void setEngineno(String engineno) {
			this.engineno = engineno;
		}
		public String getClassa() {
			return classa;
		}
		public void setClassa(String classa) {
			this.classa = classa;
		}
		public String getClassno() {
			return classno;
		}
		public void setClassno(String classno) {
			this.classno = classno;
		}
		public String getRegist() {
			return regist;
		}
		public void setRegist(String regist) {
			this.regist = regist;
		}
		public String getRegistno() {
			return registno;
		}
		public void setRegistno(String registno) {
			this.registno = registno;
		}
		
		
		
		
	}
}

