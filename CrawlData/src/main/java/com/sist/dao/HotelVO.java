package com.sist.dao;
/*
 *  HNO         NOT NULL NUMBER        
	ALL_CATE_NO          NUMBER        
	H_CRAWL_NO           NUMBER        
	GRADE                VARCHAR2(20)  
	NAME        NOT NULL VARCHAR2(200) 
	ADDR        NOT NULL VARCHAR2(200) 
	INTRO       NOT NULL CLOB          
	TIME        NOT NULL VARCHAR2(100) 
	STAR                 NUMBER(2,1)   
	ACCOUNT              NUMBER        
	HOTEL_IMAGE NOT NULL VARCHAR2(260)      
	
	==> hotel_hno_seq_1
 */
public class HotelVO {

	private int hno,all_cate_no,h_crawl_no,account;
	private String grade,name,addr,intro,time,hotel_image;
	
	public String getHotel_image() {
		return hotel_image;
	}
	public void setHotel_image(String hotel_image) {
		this.hotel_image = hotel_image;
	}
	public int getHno() {
		return hno;
	}
	public void setHno(int hno) {
		this.hno = hno;
	}
	public int getAll_cate_no() {
		return all_cate_no;
	}
	public void setAll_cate_no(int all_cate_no) {
		this.all_cate_no = all_cate_no;
	}
	public int getH_crawl_no() {
		return h_crawl_no;
	}
	public void setH_crawl_no(int h_crawl_no) {
		this.h_crawl_no = h_crawl_no;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	
	

}
