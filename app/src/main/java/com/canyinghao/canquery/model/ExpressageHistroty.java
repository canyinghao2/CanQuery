package com.canyinghao.canquery.model;

public class ExpressageHistroty {
	private int id;
	private String num = "";
	private String com = "";
	private String no = "";

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public ExpressageHistroty(String num, String com, String no) {
		super();
		this.num = num;
		this.com = com;
		this.no = no;
	}

	public ExpressageHistroty() {
		super();
		// TODO Auto-generated constructor stub
	}



}
