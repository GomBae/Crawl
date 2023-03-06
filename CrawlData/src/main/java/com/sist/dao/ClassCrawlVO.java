package com.sist.dao;
/*
 * CATENO NOT NULL NUMBER         
TITLE           VARCHAR2(500)  
LINK            VARCHAR2(1000) 
 */
public class ClassCrawlVO {
	private int cateno;
	private String title,link;
	public int getCateno() {
		return cateno;
	}
	public void setCateno(int cateno) {
		this.cateno = cateno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
