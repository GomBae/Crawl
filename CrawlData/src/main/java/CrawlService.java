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
		options.addArguments("headless");
					
		//위에 설정한 옵션을 담은 드라이버 객체 생성
		driver=new ChromeDriver(options);
		
		try {
			//데이터 수집 함수 ( 함부로 실행하면 데이터 중복됨!!! )
//			crawlGetCate();
//			hotelDetailData();
//			roomDetailData();
//			carDetailData();
			
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CrawlService ct=new CrawlService();
//		ct.crawlGetCate();
		ct.process();
		
		
	}
}
