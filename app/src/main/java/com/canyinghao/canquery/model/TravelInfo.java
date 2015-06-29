package com.canyinghao.canquery.model;

import java.io.Serializable;
import java.util.List;

public class TravelInfo implements Serializable{

    private int id;
	private String total = "";
	private String limit = "";
	private List<Scenery> sceneryList;
	private List<Hotel> hotelList;

	
	public List<Hotel> getHotelList() {
		return hotelList;
	}

	public void setHotelList(List<Hotel> hotelList) {
		this.hotelList = hotelList;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public List<Scenery> getSceneryList() {
		return sceneryList;
	}

	public void setSceneryList(List<Scenery> sceneryList) {
		this.sceneryList = sceneryList;
	}

	public class Scenery implements Serializable{
        private int id;
		private String seller = "";
		private String title = "";
		private String grade = "";
		private String price_min = "";
		private String comm_cnt = "";
		private String cityId = "";
		private String address = "";
		private String sid = "";
		private String url = "";
		private String imgurl = "";
		private String intro = "";

		public String getSeller() {
			return seller;
		}

		public void setSeller(String seller) {
			this.seller = seller;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getGrade() {
			return grade;
		}

		public void setGrade(String grade) {
			this.grade = grade;
		}

		public String getPrice_min() {
			return price_min;
		}

		public void setPrice_min(String price_min) {
			this.price_min = price_min;
		}

		public String getComm_cnt() {
			return comm_cnt;
		}

		public void setComm_cnt(String comm_cnt) {
			this.comm_cnt = comm_cnt;
		}

		public String getCityId() {
			return cityId;
		}

		public void setCityId(String cityId) {
			this.cityId = cityId;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getSid() {
			return sid;
		}

		public void setSid(String sid) {
			this.sid = sid;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getImgurl() {
			return imgurl;
		}

		public void setImgurl(String imgurl) {
			this.imgurl = imgurl;
		}

		public String getIntro() {
			return intro;
		}

		public void setIntro(String intro) {
			this.intro = intro;
		}

	}
	
	public class Hotel implements Serializable{
		private String seller = "";
		private String title = "";
		private String grade = "";
		private String price_min = "";
		private String comm_cnt = "";
		private String cityId = "";
		private String address = "";
		private String hid = "";
		private String url = "";
		private String imgurl = "";
		private String intro = "";
		private String manyidu = "";
		public String getSeller() {
			return seller;
		}
		public void setSeller(String seller) {
			this.seller = seller;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getGrade() {
			return grade;
		}
		public void setGrade(String grade) {
			this.grade = grade;
		}
		public String getPrice_min() {
			return price_min;
		}
		public void setPrice_min(String price_min) {
			this.price_min = price_min;
		}
		public String getComm_cnt() {
			return comm_cnt;
		}
		public void setComm_cnt(String comm_cnt) {
			this.comm_cnt = comm_cnt;
		}
		public String getCityId() {
			return cityId;
		}
		public void setCityId(String cityId) {
			this.cityId = cityId;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getHid() {
			return hid;
		}
		public void setHid(String hid) {
			this.hid = hid;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getImgurl() {
			return imgurl;
		}
		public void setImgurl(String imgurl) {
			this.imgurl = imgurl;
		}
		public String getIntro() {
			return intro;
		}
		public void setIntro(String intro) {
			this.intro = intro;
		}
		public String getManyidu() {
			return manyidu;
		}
		public void setManyidu(String manyidu) {
			this.manyidu = manyidu;
		}
		
		
		
	}

}
