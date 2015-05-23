package com.canyinghao.canquery.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostcodeCityInfo implements Serializable{

	private String id = "";
	private String province = "";
	private List<City> city = new ArrayList<PostcodeCityInfo.City>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public List<City> getCity() {
		return city;
	}

	public void setCity(List<City> city) {
		this.city = city;
	}

	public class City implements Serializable{
		private String id = "";
		private String city = "";
		private List<District> district = new ArrayList<PostcodeCityInfo.City.District>();

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public List<District> getDistrict() {
			return district;
		}

		public void setDistrict(List<District> district) {
			this.district = district;
		}

		public class District implements Serializable{

			private String id = "";

			private String district = "";

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getDistrict() {
				return district;
			}

			public void setDistrict(String district) {
				this.district = district;
			}

		}

	}

}
