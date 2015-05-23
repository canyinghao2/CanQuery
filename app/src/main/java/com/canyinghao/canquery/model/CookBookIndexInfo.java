package com.canyinghao.canquery.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CookBookIndexInfo implements Serializable{

	private String parentId = "";
	private String name = "";
	private List<ListInfo> list = new ArrayList<ListInfo>();

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ListInfo> getList() {
		return list;
	}

	public void setList(List<ListInfo> list) {
		this.list = list;
	}

	public class ListInfo implements Serializable{

		private String id = "";
		private String name = "";
		private String parentId = "";

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

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

	}

}
