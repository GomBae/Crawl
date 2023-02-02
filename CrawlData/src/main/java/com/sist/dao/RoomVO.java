package com.sist.dao;
/*
 *  ROOM_NO       NOT NULL NUMBER        
	HNO                    NUMBER        
	ROOM_NAME     NOT NULL VARCHAR2(100) 
	ROOM_PRICE    NOT NULL VARCHAR2(100) 
	ROOM_IMAGE             VARCHAR2(260) 
	ROOM_PERSONS           VARCHAR2(100) 
	ROOM_BED_INFO          VARCHAR2(200) 
	ACCOUNT                NUMBER   
 * 
 * => room_rno_seq_1
 */
public class RoomVO {
	private int room_no,hno,account;
	private String room_name,room_price,room_image,room_persons,room_bed_info;
	
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public int getRoom_no() {
		return room_no;
	}
	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}
	public int getHno() {
		return hno;
	}
	public void setHno(int hno) {
		this.hno = hno;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public String getRoom_price() {
		return room_price;
	}
	public void setRoom_price(String room_price) {
		this.room_price = room_price;
	}
	public String getRoom_image() {
		return room_image;
	}
	public void setRoom_image(String room_image) {
		this.room_image = room_image;
	}
	public String getRoom_persons() {
		return room_persons;
	}
	public void setRoom_persons(String room_persons) {
		this.room_persons = room_persons;
	}
	public String getRoom_bed_info() {
		return room_bed_info;
	}
	public void setRoom_bed_info(String room_bed_info) {
		this.room_bed_info = room_bed_info;
	}
	
	

}
