package com.sist.dao;
/*
 CAR_NO      NOT NULL NUMBER         
RCNO                 NUMBER         
CAR_NAME             VARCHAR2(200)  
CAR_IMAGE            VARCHAR2(260)  
CAR_PRICE            VARCHAR2(50)   
CAR_OPTION2          VARCHAR2(2000) 
ACCOUNT              NUMBER         
CAR_OPTION1          VARCHAR2(300)  
 */
public class CarVO {
	private int car_no,rcno,account;
	private String car_name,car_image,car_price,car_type,car_option1,car_option2;
	
	
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public String getCar_option1() {
		return car_option1;
	}
	public void setCar_option1(String car_option1) {
		this.car_option1 = car_option1;
	}
	public String getCar_option2() {
		return car_option2;
	}
	public void setCar_option2(String car_option2) {
		this.car_option2 = car_option2;
	}
	public int getCar_no() {
		return car_no;
	}
	public void setCar_no(int car_no) {
		this.car_no = car_no;
	}
	public int getRcno() {
		return rcno;
	}
	public void setRcno(int rcno) {
		this.rcno = rcno;
	}
	public String getCar_name() {
		return car_name;
	}
	public void setCar_name(String car_name) {
		this.car_name = car_name;
	}
	public String getCar_image() {
		return car_image;
	}
	public void setCar_image(String car_image) {
		this.car_image = car_image;
	}
	public String getCar_price() {
		return car_price;
	}
	public void setCar_price(String car_price) {
		this.car_price = car_price;
	}

	
	
}
