package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CrawlDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@211.63.89.131:1521:XE";
    Random random=new Random();
    
	
	public CrawlDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//카테고리 긁기
	//호텔 카테고리 추가
		public void hotelCategoryInsert(CrawlVO vo) {//30
			try {
				getConnection();
				String sql="INSERT INTO hotel_crawl_1 VALUES(hc_crawlno_seq.nextval,?,?)";
				//sql문장 전송
				ps=conn.prepareStatement(sql);
				ps.setString(1,vo.getCrawl_title());
				ps.setString(2, vo.getLink());

				//실행요청
				ps.executeUpdate(); //commit()포함
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				disConnection();
			}
		}
		
		//카테고리에서 카테고리 번호, 링크, 제목 
		public ArrayList<CrawlVO> crawlCategoryInfoData(){
			ArrayList<CrawlVO> list=new ArrayList<CrawlVO>();
			try {
				getConnection();
				/*
				 *  H_CRAWL_NO  NOT NULL NUMBER        
					CRAWL_TITLE NOT NULL VARCHAR2(200) 
					LINK        NOT NULL VARCHAR2(500) 
				 */
				String sql="SELECT h_crawl_no,crawl_title,link FROM hotel_crawl_1 ORDER BY h_crawl_no ASC";
				ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				while(rs.next()) {
					CrawlVO vo=new CrawlVO();
					vo.setH_crawl_no(rs.getInt(1));
					vo.setCrawl_title(rs.getString(2));
					vo.setLink(rs.getString(3));
					list.add(vo);
				}
				rs.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				disConnection();
			}
			return list;
		}
	
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
		public void hotelDetailInsert(HotelVO vo) {
			try {
				getConnection();
				String sql="INSERT INTO hotel_1(hno,all_cate_no,h_crawl_no,name,grade,addr,intro,time,hotel_image) "
						+ "VALUES(hotel_hno_seq_1.nextval,1,?,?,?,?,?,?,?)";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, vo.getH_crawl_no());
				ps.setString(2, vo.getName());
				ps.setString(3, vo.getGrade());
				ps.setString(4, vo.getAddr());
				ps.setString(5, vo.getIntro());
				ps.setString(6, vo.getTime());
				ps.setString(7, vo.getHotel_image());

				ps.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				disConnection();
			}
		}
		
		
		
		public void roomDetailInsert(RoomVO vo) {
			try {
				getConnection();
				String sql="INSERT INTO room_1(room_no,hno,room_name,room_price,room_image,room_persons,room_bed_info,account) "
						+ "VALUES(room_rno_seq_1.nextval,?,?,?,?,?,?,?)";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, vo.getHno());
				ps.setString(2, vo.getRoom_name());
				ps.setString(3, vo.getRoom_price());
				ps.setString(4, vo.getRoom_image());
				ps.setString(5, vo.getRoom_persons());
				ps.setString(6, vo.getRoom_bed_info());
				ps.setInt(7, random.nextInt(2, 10));
				
				ps.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				disConnection();
			}
		}
	
		/*
		 * CAR_NO     NOT NULL NUMBER         
			RCNO                NUMBER         
			CAR_NAME            VARCHAR2(200)  
			CAR_IMAGE           VARCHAR2(260)  
			CAR_PRICE           VARCHAR2(50)   
			CAR_OPTION          VARCHAR2(2000) 
			ACCOUNT 			NUMBER
		 */
		public void carData(CarVO vo) {
			try {
				getConnection();
				String sql="INSERT INTO jj_car_1(car_no,rcno,car_name,car_image,car_price,car_type,car_option1,car_option2,account) "
						+ "VALUES(jj_car_carno_seq_1.nextval,1,?,?,?,?,?,?,?)";
				ps=conn.prepareStatement(sql);
				ps.setString(1, vo.getCar_name());
				ps.setString(2, vo.getCar_image());
				ps.setString(3, vo.getCar_price());
				ps.setString(4, vo.getCar_type());
				ps.setString(5, vo.getCar_option1());
				ps.setString(6, vo.getCar_option2());
				ps.setInt(7, random.nextInt(2, 4));
				
				
				ps.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				disConnection();
			}
		}
		
		/*
		 * /*
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
		
		
		public void classCategoryInsert(ClassCrawlVO vo) {//30
			try {
				getConnection();
				String sql="INSERT INTO ch_classcrawl VALUES(ch_ccrawl_cateno.nextval,?,?)";
				//sql문장 전송
				ps=conn.prepareStatement(sql);
				ps.setString(1,vo.getTitle());
				ps.setString(2, vo.getLink());

				//실행요청
				ps.executeUpdate(); //commit()포함
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				disConnection();
			}
		}
		
		//카테고리에서 카테고리 번호, 링크, 제목 
		public ArrayList<ClassCrawlVO> classCategoryInfoData(){
			ArrayList<ClassCrawlVO> list=new ArrayList<ClassCrawlVO>();
			try {
				getConnection();
				/*
				 *  H_CRAWL_NO  NOT NULL NUMBER        
					CRAWL_TITLE NOT NULL VARCHAR2(200) 
					LINK        NOT NULL VARCHAR2(500) 
				 */
				String sql="SELECT cateno,title,link FROM ch_classcrawl ORDER BY cateno ASC";
				ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				while(rs.next()) {
					ClassCrawlVO vo=new ClassCrawlVO();
					vo.setCateno(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setLink(rs.getString(3));
					list.add(vo);
				}
				rs.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				disConnection();
			}
			return list;
		}
		
		public void classDetail(ClassDetailVO vo) throws SQLException {
			try {
				conn.setAutoCommit(false);
				getConnection();
				String sql="INSERT INTO ch_classDetail_2_3(cno,title,image,place,location,notice,perprice,totalprice,"
						+ "jjim_count,cateno,detail_cateno,summary,target,tutor_intro,class_intro,class_curri,class_video,"
						+ "onoff,inwon,tutor_info_nickname,tutor_info_img,tutor_info_grade_total,schedule) "
						+ "VALUES(ch_cd_cno_seq_2_3.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps=conn.prepareStatement(sql);
				ps.setString(1, vo.getTitle());
				ps.setString(2, vo.getImage());
				ps.setString(3, vo.getPlace());
				ps.setString(4, vo.getLocation());
				ps.setString(5, vo.getNotice());
				ps.setString(6, vo.getPerprice());
				ps.setString(7, vo.getTotalprice());
				ps.setInt(8, random.nextInt(10, 3323));
				ps.setInt(9, vo.getCate_no());
				ps.setInt(10, vo.getDetail_cateno());
				ps.setString(11, vo.getSummary());
				ps.setString(12, vo.getTarget());
				ps.setString(13, vo.getTutor_intro());
				ps.setString(14, vo.getClass_intro());
				ps.setString(15, vo.getClass_curri());
				ps.setString(16, vo.getClass_video());
				ps.setString(17, vo.getOnoff());
				ps.setString(18, vo.getInwon());
				ps.setString(19, vo.getTutor_info_nickname());
				ps.setString(20, vo.getTutor_info_img());
				double rand=(double)(Math.random()*1.0)+4.0;
		        double rand1=Math.round(rand*10.0)/10.0;
				ps.setDouble(21, rand1);
				ps.setString(22, vo.getSchedule());
				ps.executeUpdate();
				conn.commit();
			}catch(Exception e) {
				e.printStackTrace();
				conn.rollback();
			}finally {
				conn.setAutoCommit(true);
				disConnection();	
			}
		}
	
}
