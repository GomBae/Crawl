package com.sist.dao;

import lombok.Getter;
import lombok.Setter;

/*
	 CNO           NOT NULL NUMBER         
	TITLE         NOT NULL VARCHAR2(300)  
	IMAGE                  VARCHAR2(4000) 
	PLACE                  VARCHAR2(50)   
	LOCATION               VARCHAR2(300)  
	SCHEDULE               CLOB           
	NOTICE                 CLOB           
	TIME                   VARCHAR2(100)  
	PERPRICE      NOT NULL VARCHAR2(150)  
	TOTALPRICE    NOT NULL VARCHAR2(200)  
	JJIM_COUNT             NUMBER         
	CATENO                 NUMBER         
	TNO                    NUMBER         
	DETAIL_CATENO          NUMBER         
	SUMMARY                CLOB           
	TARGET                 CLOB           
	TUTOR_INTRO            CLOB           
	CLASS_INTRO            CLOB           
	CLASS_CURRI            CLOB           
	CLASS_VIDEO            CLOB           
	ONOFF                  VARCHAR2(150)  
	INWON                  VARCHAR2(30)
	TUTOR_INFO_NICKNAME             VARCHAR2(100)  
	TUTOR_INFO_IMG                  VARCHAR2(500)  
	TUTOR_INFO_GRADE_TOTAL          NUMBER(2,1)
 */
public class ClassDetailVO {
	private int cno,jjim_count,cate_no,detail_cateno;
	private double tutor_info_grade_total;
	private String title,image,place,location,notice,time,perprice,totalprice,onoff,inwon,summary,
	target,tutor_intro,class_intro,class_curri,class_video,tutor_info_nickname,tutor_info_img,schedule;
	
	
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public double getTutor_info_grade_total() {
		return tutor_info_grade_total;
	}
	public void setTutor_info_grade_total(double tutor_info_grade_total) {
		this.tutor_info_grade_total = tutor_info_grade_total;
	}
	public String getTutor_info_nickname() {
		return tutor_info_nickname;
	}
	public void setTutor_info_nickname(String tutor_info_nickname) {
		this.tutor_info_nickname = tutor_info_nickname;
	}
	public String getTutor_info_img() {
		return tutor_info_img;
	}
	public void setTutor_info_img(String tutor_info_img) {
		this.tutor_info_img = tutor_info_img;
	}
	public String getOnoff() {
		return onoff;
	}
	public void setOnoff(String onoff) {
		this.onoff = onoff;
	}
	public String getInwon() {
		return inwon;
	}
	public void setInwon(String inwon) {
		this.inwon = inwon;
	}
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public int getJjim_count() {
		return jjim_count;
	}
	public void setJjim_count(int jjim_count) {
		this.jjim_count = jjim_count;
	}
	public int getCate_no() {
		return cate_no;
	}
	public void setCate_no(int cate_no) {
		this.cate_no = cate_no;
	}
	public int getDetail_cateno() {
		return detail_cateno;
	}
	public void setDetail_cateno(int detail_cateno) {
		this.detail_cateno = detail_cateno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPerprice() {
		return perprice;
	}
	public void setPerprice(String perprice) {
		this.perprice = perprice;
	}
	public String getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}

	public String getTutor_intro() {
		return tutor_intro;
	}
	public void setTutor_intro(String tutor_intro) {
		this.tutor_intro = tutor_intro;
	}
	public String getClass_intro() {
		return class_intro;
	}
	public void setClass_intro(String class_intro) {
		this.class_intro = class_intro;
	}
	public String getClass_curri() {
		return class_curri;
	}
	public void setClass_curri(String class_curri) {
		this.class_curri = class_curri;
	}
	public String getClass_video() {
		return class_video;
	}
	public void setClass_video(String class_video) {
		this.class_video = class_video;
	}
	
}
