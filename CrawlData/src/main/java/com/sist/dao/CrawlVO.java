package com.sist.dao;
/*
 * H_CRAWL_NO  NOT NULL NUMBER        
	CRAWL_TITLE NOT NULL VARCHAR2(200) 
	LINK        NOT NULL VARCHAR2(500) 
 * 
 */
public class CrawlVO {
	private int h_crawl_no;
	private String crawl_title,link;
	public int getH_crawl_no() {
		return h_crawl_no;
	}
	public void setH_crawl_no(int h_crawl_no) {
		this.h_crawl_no = h_crawl_no;
	}
	public String getCrawl_title() {
		return crawl_title;
	}
	public void setCrawl_title(String crawl_title) {
		this.crawl_title = crawl_title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
