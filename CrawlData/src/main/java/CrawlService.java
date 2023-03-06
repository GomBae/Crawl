import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sist.dao.CarVO;
import com.sist.dao.ClassCrawlVO;
import com.sist.dao.ClassDetailVO;
import com.sist.dao.CrawlDAO;
import com.sist.dao.CrawlVO;
import com.sist.dao.HotelVO;
import com.sist.dao.RoomVO;

public class CrawlService {

	private WebDriver driver;
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static final String WEB_DRIVER_PATH = "C:\\Users\\myg17\\Desktop\\chromedriver.exe";
	
	public void process() {
		//크롬 드라이버 셋팅
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		ChromeOptions options=new ChromeOptions();
		//브라우저를 생성해서 띄우지않고 내부적으로 돌리는 옵션
//		options.addArguments("headless");
					
		//위에 설정한 옵션을 담은 드라이버 객체 생성
		driver=new ChromeDriver(options);
		
		try {
			//데이터 수집 함수 ( 함부로 실행하면 데이터 중복됨!!! )
//			crawlGetCate();
//			hotelDetailData();
//			roomDetailData();
//			carDetailData();
//			GetCate();
			classDetailData();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			driver.quit();
		}
		
	}
	
	public void crawlGetCate() {
		CrawlDAO dao = new CrawlDAO();		
		int c=1;
		for(int i=1;i<=10;i++) {
			//이동 원하는 url
			String url="https://www.myrealtrip.com/accommodations/stays/?place[place_type]=GEO&place[place_name]=%EC%A0%9C%EC%A3%BC%EB%8F%84&place[gid]=1074213&place[latitude]=33.4848652881&place[longitude]=126.4885574539&checkin=2023-01-09&checkout=2023-01-13&adult_count=2&order_by=WEIGHT_DESC&page[page]="+i+"&page[size]=30&filter[min_price]=0&filter[max_price]=1000000&filter[star]=0&filter[only_dgm_promo]=false&filter[curation_type]=HOTEL";

			//이동
			driver.get(url);
					
			//HTTP응답 속도보다 컴파일러가 더 빨라서 5초 기다려줌
			try {
				Thread.sleep(5000);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
					
			//타이틀 갯수 정해서 상세보기로 넘어가는 카운트 
			List<WebElement> hotelTitle = driver.findElements(By.cssSelector("#Mrt3Stay > main > div > section > div > a > div.css-4tl1r5--ProductCard--Content.e9e97mf3 > h2"));

			int j=2; //링크 div 번호
					
//			for(WebElement list:hotelTitle) {
//				System.out.println(c + list.getText());
//				c++;
//			}
			for(int k=0;k<hotelTitle.size();k++) {
				String title = hotelTitle.get(k).getText();
				WebElement hotelLink = driver.findElement(By.cssSelector("#Mrt3Stay > main > div > section > div:nth-child("+j+") > a"));
				String link=hotelLink.getAttribute("href");
				link=link.substring(0,link.lastIndexOf("?"));
				System.out.println(c + " " + title);
				System.out.println(link);
				System.out.println("=========================================================");
				CrawlVO vo=new CrawlVO();
				vo.setCrawl_title(title);
				vo.setLink(link);
				//실행시 주의 (데이터를 수집하는 함수)
//				dao.hotelCategoryInsert(vo);
				c++;
				j++;
			}
					

		}
	}
	
	public void hotelDetailData() {
		CrawlDAO dao=new CrawlDAO();
		try {
			ArrayList<CrawlVO> list=dao.crawlCategoryInfoData();
			for(CrawlVO vo: list) {
//				System.out.println(vo.getH_crawl_no()+" "+vo.getCrawl_title()+" "+vo.getLink());
				HotelVO hvo=new HotelVO();
				hvo.setH_crawl_no(vo.getH_crawl_no());
//				System.out.println(vo.getH_crawl_no()+"."+vo.getCrawl_title());
				
				String url=vo.getLink();
				driver.get(url);
				
				//HTTP응답 속도보다 컴파일러가 더 빨라서 5초 기다려줌
				try {
					Thread.sleep(5000);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				/*
				 *  HNO           NOT NULL NUMBER        
					ALL_CATE_NO            NUMBER        
					H_CRAWL_NO             NUMBER        
					GRADE                  VARCHAR2(20)  
					NAME          NOT NULL VARCHAR2(200) 
					ADDR          NOT NULL VARCHAR2(200) 
					INTRO         NOT NULL CLOB          
					TIME          NOT NULL VARCHAR2(100) 
					STAR                   NUMBER(2,1)   
					ACCOUNT                NUMBER        
					ROOM_NAME     NOT NULL VARCHAR2(100) 
					ROOM_IMAGE    NOT NULL CLOB 
					ROOM_PRICE    NOT NULL VARCHAR2(200) 
					ROOM_PERSONS  NOT NULL VARCHAR2(200) 
					ROOM_BED_INFO NOT NULL VARCHAR2(100) 
					
					==> hotel_hno_seq_1
				 */
				
				WebElement hotel_name = driver.findElement(By.cssSelector("#Mrt3Stay > main > div > section.css-14sv9e--ProductHeader--Head.eab4vzz5 > div > h1"));
				WebElement hotel_addr = driver.findElement(By.cssSelector("#Mrt3Stay > main > div > div.css-asy123--AddressWithCheckInOut--Wrapper.e1attxan3 > div.css-12aw1e0--AddressWithCheckInOut--IconAlign--AddressWithCheckInOut--Location.e1attxan1"));
				WebElement hotel_grade = driver.findElement(By.cssSelector("#Mrt3Stay > main > div > section.css-14sv9e--ProductHeader--Head.eab4vzz5 > div > p"));
				WebElement hotel_time = driver.findElement(By.cssSelector("#Mrt3Stay > main > div > div.css-asy123--AddressWithCheckInOut--Wrapper.e1attxan3 > div.css-u7h48r--AddressWithCheckInOut--IconAlign--AddressWithCheckInOut--CheckInOut.e1attxan0"));
				WebElement hotel_intro = driver.findElement(By.cssSelector("#introduction > div > div"));
				WebElement hotel_image = driver.findElement(By.cssSelector("#Mrt3Stay > main > div > div.css-1g220qt--GirdImageViewer--Wrapper.e1vzln8j7 > div.css-lq1w5u--GirdImageViewer--ImageBox.e1vzln8j5 > button > div > img"));
				
				
				String name=hotel_name.getText();
				String addr=hotel_addr.getText();
				String grade=hotel_grade.getText();
				String time=hotel_time.getText();
				String intro=hotel_intro.getText();
				String h_image=hotel_image.getAttribute("src");
				
				
				System.out.println(vo.getH_crawl_no()+"."+vo.getCrawl_title());
				System.out.println(name+" "+addr);
				System.out.println(grade);
				System.out.println(time);
				System.out.println(intro);
				System.out.println(h_image);
				hvo.setName(name);
				hvo.setGrade(grade);
				hvo.setAddr(addr);
				hvo.setIntro(intro);
				hvo.setTime(time);
				hvo.setHotel_image(h_image);
				//실행시 주의 (데이터를 수집하는 함수)
//				dao.hotelDetailInsert(hvo);
				
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void roomDetailData() {
		CrawlDAO dao=new CrawlDAO();
		try {
			ArrayList<CrawlVO> list=dao.crawlCategoryInfoData();
			for(CrawlVO vo:list) {
				RoomVO rvo=new RoomVO();
				rvo.setHno(vo.getH_crawl_no());
//				System.out.println(vo.getH_crawl_no()+"."+vo.getCrawl_title());
				
				String url=vo.getLink();
				driver.get(url);
				
				//HTTP응답 속도보다 컴파일러가 더 빨라서 5초 기다려줌
				try {
					Thread.sleep(5000);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				/*
				 * 
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
				
				List<WebElement> room_name = driver.findElements(By.cssSelector("#roomComparison > section > div > div > div.css-f5oj56--RoomCard--Box--RoomCard--Box.ekm6fv6 > div > h3"));
				List<WebElement> room_bed_info = driver.findElements(By.cssSelector("#roomComparison > section > div > div > div.css-f5oj56--RoomCard--Box--RoomCard--Box.ekm6fv6 > div > p:nth-child(3)"));
				List<WebElement> room_persons = driver.findElements(By.cssSelector("#roomComparison > section > div > div > div.css-f5oj56--RoomCard--Box--RoomCard--Box.ekm6fv6 > div > p:nth-child(2)"));
				List<WebElement> room_price = driver.findElements(By.cssSelector("#roomComparison > section > ul > div:nth-child(1) > li > div.css-13h2a7a--RoomOption--Reservation.e1mkec6w1 > div.css-ck4a4o--ProductPrice--Container.e8oi5wt6 > div > div > div > p"));
				List<WebElement> room_image = driver.findElements(By.xpath("//*[@id=\"roomComparison\"]/section/div/div/div[1]/img"));
				System.out.println(vo.getH_crawl_no()+" "+vo.getCrawl_title());

				
				for(int i=0;i<room_name.size();i++) {
					String bed_info="no";
					String name=room_name.get(i).getText();
					String persons=room_persons.get(i).getText();
					try{
						bed_info=room_bed_info.get(i).getText();
					}catch(Exception e) {}
					
					String price=room_price.get(i).getText();
					price = price.substring(0,price.length()-2);
					String image=room_image.get(i).getAttribute("src");
					
					System.out.println(name);
					System.out.println(persons);
					System.out.println(bed_info);
					System.out.println(price);
					System.out.println(image);
					rvo.setRoom_name(name);
					rvo.setRoom_persons(persons);
					rvo.setRoom_bed_info(bed_info);
					rvo.setRoom_price(price);
					rvo.setRoom_image(image);
					//실행시 주의 (데이터를 수집하는 함수)
//					dao.roomDetailInsert(rvo);

				}
				
					
					
				}
//				String name=room_name.getText();
//				String bed_info=room_bed_ingo.getText();
//				String grade=hotel_grade.getText();
//				String time=hotel_time.getText();
//				String intro=hotel_intro.getText();
//				String h_image=hotel_image.getAttribute("src");
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public void carDetailData() {
		CrawlDAO dao=new CrawlDAO();
		CarVO cvo=new CarVO();
		JavascriptExecutor jse= new JavascriptExecutor() {
			
			@Override
			public Object executeScript(String script, Object... args) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object executeAsyncScript(String script, Object... args) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		jse = (JavascriptExecutor) driver;
		
		try {
			driver.get("https://www.jeju-the-rentcar.com/realTimeSearch?sdate=20230209&shour=09&smin=00&edate=20230210&ehour=09&emin=00&insurance=%EC%99%84%EC%A0%84%EC%9E%90%EC%B0%A8&carType=%EC%A0%84%EC%B2%B4");
			int c=1;
			Thread.sleep(10000);
			
			List<WebElement> car_names=driver.findElements(By.xpath("//*[@id=\"sub_wrap\"]/div[2]/div[4]/div[3]/ul/li/div[2]/p/strong"));
			List<WebElement> car_info1=driver.findElements(By.xpath("//*[@id=\"sub_wrap\"]/div[2]/div[4]/div[3]/ul/li/div[2]/p/span"));
			List<WebElement> car_image=driver.findElements(By.xpath("//*[@id=\"sub_wrap\"]/div[2]/div[4]/div[3]/ul/li/div[1]/div/img"));
			List<WebElement> car_price=driver.findElements(By.xpath("//*[@id=\"sub_wrap\"]/div[2]/div[4]/div[3]/ul/li/div[3]/div[2]/p/strong"));
			List<WebElement> buttons=driver.findElements(By.xpath("//li[contains(@id,'more')]"));

			for(int i=227;i<car_names.size();i++) {
				try {
				WebElement button=buttons.get(i);
				jse.executeScript("arguments[0].click();", button);
				Thread.sleep(2000);
				}catch(Exception e) {}
				List<WebElement> options=driver.findElements(By.xpath("//*[@id=\"sub_wrap\"]/div[2]/div[4]/div[3]/ul/li["+(i+1)+"]/div[2]/ul/li"));
				
				String name=car_names.get(i).getText();
				String info1=car_info1.get(i).getText().trim();
				String type=info1.substring(info1.indexOf(" ")+3,info1.indexOf("2")-2).trim();
				String image=car_image.get(i).getAttribute("src");
				String price=car_price.get(i).getText();

				cvo.setCar_name(name);
				cvo.setCar_type(type);
				cvo.setCar_option1(info1);
				cvo.setCar_image(image);
				cvo.setCar_price(price);
				
				System.out.println(c+" "+cvo.getCar_name());
				System.out.println(cvo.getCar_type());
				System.out.println(cvo.getCar_option1());
				System.out.println(cvo.getCar_price());
				System.out.println(cvo.getCar_image());
				String op="";
				for(int k=0;k<options.size();k++) {
					if(!options.get(k).getText().isEmpty()) {
						String s=options.get(k).getText();
						op+=s+",";
					}else {
						continue;
					}
				}
				op=op.substring(0,op.lastIndexOf(","));
				cvo.setCar_option2(op);
				System.out.println(cvo.getCar_option2());
				System.out.println("========================================");
				c++;
				//실행시 주의 (데이터를 수집하는 함수)
//				dao.carData(cvo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
			
	}
	
	public void GetCate() {
		CrawlDAO dao = new CrawlDAO();	
		ClassCrawlVO vo=new ClassCrawlVO();
		int c=1;
		for(int i=1;i<=3;i++) {
			//이동 원하는 url
			driver.get("https://taling.me/Home/Search/?page="+i+"&query=&cateMain=41&cateSub=&region=&day=&time=&tType=3&region=&classTypeCode=1&regionMain=&orderIdx=&code=&org=");

					
			//HTTP응답 속도보다 컴파일러가 더 빨라서 5초 기다려줌
			try {
				Thread.sleep(5000);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
					
			//타이틀 갯수 정해서 상세보기로 넘어가는 카운트 
			List<WebElement> classTitle = driver.findElements(By.xpath("//*[@id=\"wrap\"]/main/section/ul/li/a/div[2]/h3"));
			int j=1;//링크 div 번호
			for(int k=0;k<classTitle.size();k++) {
				String title=classTitle.get(k).getText();
				WebElement classLink = driver.findElement(By.xpath("//*[@id=\"wrap\"]/main/section/ul/li["+j+"]/a"));				
				String link=classLink.getAttribute("href");
				vo.setTitle(title);
				vo.setLink(link);
				System.out.println(c+" "+vo.getTitle());
				System.out.println(vo.getLink());
				dao.classCategoryInsert(vo);
				c++;
				j++;
			}
		

		}
	}
	
	public void classDetailData() {
		CrawlDAO dao=new CrawlDAO();
		int g=1;
		try {
			ArrayList<ClassCrawlVO> list=dao.classCategoryInfoData();
			for(ClassCrawlVO vo: list) {

				ClassDetailVO cvo=new ClassDetailVO();
				
				String url=vo.getLink();
				driver.get(url);
				
				//HTTP응답 속도보다 컴파일러가 더 빨라서 5초 기다려줌
				try {
					Thread.sleep(5000);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				/*
				 *  CNO           NOT NULL NUMBER         
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
					
				 */
				if(vo.getCateno()<=60) {
					cvo.setCate_no(1);
					cvo.setDetail_cateno(1);
				}else if(vo.getCateno()>=61 && vo.getCateno()<=108) {
					cvo.setCate_no(1);
					cvo.setDetail_cateno(2);
				}else if(vo.getCateno()>=109 && vo.getCateno()<=149) {
					cvo.setCate_no(1);
					cvo.setDetail_cateno(3);
				}else if(vo.getCateno()>=150 && vo.getCateno()<=209) {
					cvo.setCate_no(1);
					cvo.setDetail_cateno(4);
				}else if(vo.getCateno()>=210 && vo.getCateno()<=269) {
					cvo.setCate_no(2);
					cvo.setDetail_cateno(5);
				}else if(vo.getCateno()>=270 && vo.getCateno()<=329) {
					cvo.setCate_no(2);
					cvo.setDetail_cateno(6);
				}else if(vo.getCateno()>=330 && vo.getCateno()<=344) {
					cvo.setCate_no(3);
					cvo.setDetail_cateno(7);
				}else if(vo.getCateno()>=345 && vo.getCateno()<=381) {
					cvo.setCate_no(3);
					cvo.setDetail_cateno(8);
				}else if(vo.getCateno()>=382 && vo.getCateno()<=430) {
					cvo.setCate_no(4);
					cvo.setDetail_cateno(9);
				}else if(vo.getCateno()>=431 && vo.getCateno()<=471) {
					cvo.setCate_no(4);
					cvo.setDetail_cateno(10);
				}else if(vo.getCateno()>=472 && vo.getCateno()<=483) {
					cvo.setCate_no(4);
					cvo.setDetail_cateno(11);
				}else if(vo.getCateno()>=484 && vo.getCateno()<=543) {
					cvo.setCate_no(5);
					cvo.setDetail_cateno(12);
				}else if(vo.getCateno()>=544 && vo.getCateno()<=603) {
					cvo.setCate_no(5);
					cvo.setDetail_cateno(13);
				}else if(vo.getCateno()>=604 && vo.getCateno()<=663) {
					cvo.setCate_no(6);
					cvo.setDetail_cateno(14);
				}else if(vo.getCateno()>=664 && vo.getCateno()<=723) {
					cvo.setCate_no(6);
					cvo.setDetail_cateno(15);
				}else if(vo.getCateno()>=724 && vo.getCateno()<=783) {
					cvo.setCate_no(7);
					cvo.setDetail_cateno(16);
				}else if(vo.getCateno()>=784 && vo.getCateno()<=843) {
					cvo.setCate_no(7);
					cvo.setDetail_cateno(17);
				}else if(vo.getCateno()>=844 && vo.getCateno()<=876) {
					cvo.setCate_no(8);
					cvo.setDetail_cateno(18);
				}else if(vo.getCateno()>=877 && vo.getCateno()<=936) {
					cvo.setCate_no(8);
					cvo.setDetail_cateno(19);
				}else if(vo.getCateno()>=937 && vo.getCateno()<=996) {
					cvo.setCate_no(8);
					cvo.setDetail_cateno(20);
				}else if(vo.getCateno()>=997 && vo.getCateno()<=1056) {
					cvo.setCate_no(8);
					cvo.setDetail_cateno(21);
				}else if(vo.getCateno()>=1057 && vo.getCateno()<=1116) {
					cvo.setCate_no(9);
					cvo.setDetail_cateno(22);
				}else if(vo.getCateno()>=1117 && vo.getCateno()<=1176) {
					cvo.setCate_no(9);
					cvo.setDetail_cateno(23);
				}else if(vo.getCateno()>=1177 && vo.getCateno()<=1201) {
					cvo.setCate_no(9);
					cvo.setDetail_cateno(24);
				}else if(vo.getCateno()>=1202 && vo.getCateno()<=1261) {
					cvo.setCate_no(10);
					cvo.setDetail_cateno(25);
				}else if(vo.getCateno()>=1262 && vo.getCateno()<=1321) {
					cvo.setCate_no(10);
					cvo.setDetail_cateno(26);
				}else if(vo.getCateno()>1322 && vo.getCateno()<=1381) {
					cvo.setCate_no(10);
					cvo.setDetail_cateno(27);
				}
				List<WebElement> detail_list = driver.findElements(By.cssSelector("#wrap > div.p2p_class_wrap > div.p2p_class_container > div > section > div.p_col_left > p"));
				WebElement onoff1 = driver.findElement(By.xpath("//*[@id=\"regionAll\"]"));
				WebElement inwon1 = driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[1]/section[1]/ul/li[3]"));
				WebElement class_title = driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[1]/section[1]/h1"));
				WebElement tutor_info_img = driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[1]/section[1]/div/div[1]/img"));
				WebElement tutor_info_nickname = driver.findElement(By.xpath("//*[@id=\"wrap\"]/div[2]/div[1]/section[1]/div/div[2]/em"));
				List<WebElement> class_image1;
				WebElement class_image2;
				WebElement class_sum = driver.findElement(By.cssSelector("#wrap > div.p2p_class_wrap > div.p2p_class_container > div > section.sec_common > div.p_col_right > div > p"));
				WebElement class_target = driver.findElement(By.cssSelector("#wrap > div.p2p_class_wrap > div.p2p_class_container > div > section.sec_common.p2p_class_target > div.p_col_right > div > p"));
				WebElement tutor_intro = driver.findElement(By.cssSelector("#wrap > div.p2p_class_wrap > div.p2p_class_container > div > section.idx.sec_common.p2p_tutor_intro > div.p_col_right > div.text_wrap > p"));
				WebElement class_intro = driver.findElement(By.cssSelector("#wrap > div.p2p_class_wrap > div.p2p_class_container > div > section.idx.sec_common.p2p_class_intro > div.p_col_right > div > p"));
				WebElement class_curri = driver.findElement(By.cssSelector("#wrap > div.p2p_class_wrap > div.p2p_class_container > div > section.idx.sec_common.p2p_class_curri > div.p_col_right > div > div > p"));
				List<WebElement> class_place = driver.findElements(By.cssSelector("#mCSB_1_container > li > span"));
				List<WebElement> class_location = driver.findElements(By.cssSelector("#mCSB_1_container > li > b"));
				List<WebElement> class_schedule = driver.findElements(By.cssSelector("#mCSB_1_container > li > p"));
				WebElement class_per1 = driver.findElement(By.cssSelector("#wrap > div.p2p_class_wrap > aside > div > p > span.per"));
//				WebElement class_per2 = driver.findElement(By.cssSelector("#wrap > div.p2p_class_wrap > aside > div > p > span.per > span"));
				WebElement class_total1 = driver.findElement(By.cssSelector("#wrap > div.p2p_class_wrap > aside > div > p > span.total > b"));
				WebElement class_total2 = driver.findElement(By.cssSelector("#wrap > div.p2p_class_wrap > aside > div > p > span.total > span"));
				
				
				String onoff=onoff1.getText();
				String inwon=inwon1.getText();
				String title=class_title.getText();
				String tutor_img = tutor_info_img.getAttribute("src");
				String tutor_nickname = tutor_info_nickname.getText();
				String sum = class_sum.getText();
				String target = class_target.getText();
				String tIntro = tutor_intro.getText();
				String cIntro = class_intro.getText();
				String curri = class_curri.getText();
				String notice="",video="";
				String place="", location="",schedule="";
				String perprice=class_per1.getText();
				String totalprice=class_total1.getText()+class_total2.getText();
				
				
				for(int i=0;i<detail_list.size()-1;i++) {
					String label=detail_list.get(i).getText();
					if(label.equals("클래스 전 숙지해주세요!")) {
						WebElement a=driver.findElement(By.cssSelector("#wrap > div.p2p_class_wrap > div.p2p_class_container > div > section.p2p_class_notice > div.p_col_right > div > p"));
						notice=a.getText();
						
					}
					if(label.equals("관련 영상 보고가세요.")) {
						List<WebElement> a=driver.findElements(By.cssSelector("#wrap > div.p2p_class_wrap > div.p2p_class_container > div > section.sec_common.p2p_class_video > div.p_col_right > div > iframe"));
						for(WebElement aa:a) {
							String c=aa.getAttribute("src");
							video+=c+"^";
						}
						video=video.substring(0,video.lastIndexOf("^"));
						video=video.trim();
						
					}
				}
				
//				String poster="";
//				for(int j=0;j<image.size();j++) {
//					String s=image.get(j).attr("src");
//					poster+=s+"^";
//				}
//				poster=poster.substring(0,poster.lastIndexOf("^"));
//				
//				poster=poster.replace("&","#");
//				fvo.setPoster(poster);
				String cImage="";
				
				if(driver.findElements(By.cssSelector("#wrap > div.p2p_class_wrap > div.p2p_class_container > section.p2p_class_img > div > div.swiper-container.gallery-thumbs.swiper-container-initialized.swiper-container-vertical.swiper-container-free-mode.swiper-container-thumbs > div.swiper-wrapper > div.swiper-slide")) != null) {
					class_image1 = driver.findElements(By.cssSelector("#wrap > div.p2p_class_wrap > div.p2p_class_container > section.p2p_class_img > div > div.swiper-container.gallery-thumbs.swiper-container-initialized.swiper-container-vertical.swiper-container-free-mode.swiper-container-thumbs > div.swiper-wrapper > div.swiper-slide"));
					for(int k=0;k<class_image1.size()-2;k++) {
						String c=class_image1.get(k).getCssValue("background-image");
						c=c.substring(c.indexOf("\"")+1,c.lastIndexOf("\""));
						cImage+=c+"^";
					}
					cImage=cImage.substring(0,cImage.lastIndexOf("^")).trim();
				}else {
					class_image2 = driver.findElement(By.cssSelector("#wrap > div.p2p_class_wrap > div.p2p_class_container > section.p2p_class_img > div > div.swiper-container.gallery-top.wide_slide.swiper-container-initialized.swiper-container-horizontal.swiper-container-rtl > div > div"));
					String c=class_image2.getCssValue("background-image");
					cImage=c.substring(c.indexOf("\"")+1,c.lastIndexOf("\"")).trim();
					
				}
				
				
				for(WebElement a:class_place) {
					String aa=a.getText();
					place+=aa+"^";
				}
				place=place.substring(0,place.lastIndexOf("^")).trim();
				
				for(WebElement a:class_location) {
					String aa=a.getText();
					location+=aa+"^";
				}
				location=location.substring(0,location.lastIndexOf("^")).trim();
				
				for(WebElement a:class_schedule) {
					String aa=a.getText();
					schedule+=aa+"^";
				}
				schedule=schedule.substring(0,schedule.lastIndexOf("^")).trim();
				
				cvo.setTitle(title);
				cvo.setImage(cImage);
				cvo.setPlace(place);
				cvo.setLocation(location);
				cvo.setSchedule(schedule);
				cvo.setNotice(notice);
				cvo.setPerprice(perprice);
				cvo.setTotalprice(totalprice);
				//cateno
				//detailcno
				cvo.setSummary(sum);
				cvo.setTarget(target);
				cvo.setTutor_intro(tIntro);
				cvo.setClass_intro(cIntro);
				cvo.setClass_curri(curri);
				cvo.setClass_video(video);
				cvo.setOnoff(onoff);
				cvo.setInwon(inwon);
				cvo.setTutor_info_nickname(tutor_nickname);
				cvo.setTutor_info_img(tutor_img);
				
				System.out.println("============= "+g+" ============== ");
				System.out.println(cvo.getCate_no());
				System.out.println(cvo.getDetail_cateno());
				System.out.println(cvo.getOnoff());
				System.out.println(cvo.getInwon());
				System.out.println(cvo.getTitle());
				System.out.println(cvo.getImage());
				System.out.println(cvo.getSummary());
				System.out.println(cvo.getTarget());
				System.out.println(cvo.getTutor_info_nickname());
				System.out.println(cvo.getTutor_info_img());
				System.out.println(cvo.getTutor_intro());
				System.out.println(cvo.getClass_intro());
				System.out.println(cvo.getClass_curri());
				System.out.println(cvo.getClass_video());
				System.out.println("== 스케쥴 ==");
				System.out.println(cvo.getSchedule());
				System.out.println("== 장소 ==");
				System.out.println(cvo.getPlace());
				System.out.println("== 위치 ==");
				System.out.println(cvo.getLocation());	
				System.out.println(cvo.getNotice());
				System.out.println(cvo.getPerprice());
				System.out.println(cvo.getTotalprice());
				
				g++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CrawlService ct=new CrawlService();
//		ct.crawlGetCate();
		ct.process();
		
		
	}
}
