package com.canyinghao.canquery.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OilPriceInfo implements Serializable{
	
	private List<Data> data=new ArrayList<Data>();
	
	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public class Data implements Serializable{
		private String id="";
		private String name="";
		private String area="";
		private String areaname="";
		private String address="";
		private String brandname="";
		private String type="";
		private String discount="";
		private String exhaust="";
		private String position="";
		private String lon="";
		private String lat="";
		private String fwlsmc="";
		private String distance="";
		private String priceTemp="";
		private List<Price> price=new ArrayList<OilPriceInfo.Data.Price>();
		private List<Gastprice> gastprice=new ArrayList<OilPriceInfo.Data.Gastprice>();
		
		
		public String getPriceTemp() {
			return priceTemp;
		}
		public void setPriceTemp(String priceTemp) {
			this.priceTemp = priceTemp;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getAreaname() {
			return areaname;
		}
		public void setAreaname(String areaname) {
			this.areaname = areaname;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getBrandname() {
			return brandname;
		}
		public void setBrandname(String brandname) {
			this.brandname = brandname;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDiscount() {
			return discount;
		}
		public void setDiscount(String discount) {
			this.discount = discount;
		}
		public String getExhaust() {
			return exhaust;
		}
		public void setExhaust(String exhaust) {
			this.exhaust = exhaust;
		}
		public String getPosition() {
			return position;
		}
		public void setPosition(String position) {
			this.position = position;
		}
		public String getLon() {
			return lon;
		}
		public void setLon(String lon) {
			this.lon = lon;
		}
		public String getLat() {
			return lat;
		}
		public void setLat(String lat) {
			this.lat = lat;
		}
		public String getFwlsmc() {
			return fwlsmc;
		}
		public void setFwlsmc(String fwlsmc) {
			this.fwlsmc = fwlsmc;
		}
		public String getDistance() {
			return distance;
		}
		public void setDistance(String distance) {
			this.distance = distance;
		}
		
		public List<Price> getPrice() {
			return price;
		}
		public void setPrice(List<Price> price) {
			this.price = price;
		}
		public List<Gastprice> getGastprice() {
			return gastprice;
		}
		public void setGastprice(List<Gastprice> gastprice) {
			this.gastprice = gastprice;
		}

		public class Price implements Serializable{
			private String type="";
			private String price="";
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			public String getPrice() {
				return price;
			}
			public void setPrice(String price) {
				this.price = price;
			}
	       
		}
		public class Gastprice implements Serializable{
			private String name="";
			private String price="";
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getPrice() {
				return price;
			}
			public void setPrice(String price) {
				this.price = price;
			}
			
		}
	}
	
	
	
	

}
