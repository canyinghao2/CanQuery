package com.canyinghao.canquery.model;

import java.util.ArrayList;
import java.util.List;

public class PostcodeInfo {
	
	private String totalcount;
	private String totalpage;
	private String currentpage;
	private String pagesize;
	
	private List<PostcodeListInfo> list=new ArrayList<PostcodeListInfo>();

	public String getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}

	public String getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(String totalpage) {
		this.totalpage = totalpage;
	}

	public String getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public List<PostcodeListInfo> getList() {
		return list;
	}

	public void setList(List<PostcodeListInfo> list) {
		this.list = list;
	}

	public class PostcodeListInfo{
		private String PostNumber;
		private String Province;
		private String City;
		private String District;
		private String Address;
		public String getPostNumber() {
			return PostNumber;
		}
		public void setPostNumber(String postNumber) {
			PostNumber = postNumber;
		}
		public String getProvince() {
			return Province;
		}
		public void setProvince(String province) {
			Province = province;
		}
		public String getCity() {
			return City;
		}
		public void setCity(String city) {
			City = city;
		}
		public String getDistrict() {
			return District;
		}
		public void setDistrict(String district) {
			District = district;
		}
		public String getAddress() {
			return Address;
		}
		public void setAddress(String address) {
			Address = address;
		}
		
	}
	
	
	

	
	
	
	
}
