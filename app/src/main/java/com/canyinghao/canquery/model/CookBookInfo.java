package com.canyinghao.canquery.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CookBookInfo implements Serializable{

	private List<Data> data=new ArrayList<CookBookInfo.Data>();
	private String totalNum="";
	private String pn="";
	private String rn="";
	
	
	public List<Data> getData() {
		return data;
	}


	public void setData(List<Data> data) {
		this.data = data;
	}


	public String getTotalNum() {
		return totalNum;
	}


	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}


	public String getPn() {
		return pn;
	}


	public void setPn(String pn) {
		this.pn = pn;
	}


	public String getRn() {
		return rn;
	}


	public void setRn(String rn) {
		this.rn = rn;
	}


	public class Data implements Serializable{
		
		private String id="";
		private String title="";
		private String tags="";
		private String imtro="";
		private String ingredients="";
		private String burden="";
		private List<String> albums=new ArrayList<String>();
		private List<Steps> steps=new ArrayList<CookBookInfo.Data.Steps>();
		
		
		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}


		public String getTitle() {
			return title;
		}


		public void setTitle(String title) {
			this.title = title;
		}


		public String getTags() {
			return tags;
		}


		public void setTags(String tags) {
			this.tags = tags;
		}


		public String getImtro() {
			return imtro;
		}


		public void setImtro(String imtro) {
			this.imtro = imtro;
		}


		public String getIngredients() {
			return ingredients;
		}


		public void setIngredients(String ingredients) {
			this.ingredients = ingredients;
		}


		public String getBurden() {
			return burden;
		}


		public void setBurden(String burden) {
			this.burden = burden;
		}


		public List<String> getAlbums() {
			return albums;
		}


		public void setAlbums(List<String> albums) {
			this.albums = albums;
		}


		public List<Steps> getSteps() {
			return steps;
		}


		public void setSteps(List<Steps> steps) {
			this.steps = steps;
		}


		public class Steps implements Serializable{

			private String img="";
			private String step="";
			public String getImg() {
				return img;
			}
			public void setImg(String img) {
				this.img = img;
			}
			public String getStep() {
				return step;
			}
			public void setStep(String step) {
				this.step = step;
			}
			
			
		}
		
	}
	
}
