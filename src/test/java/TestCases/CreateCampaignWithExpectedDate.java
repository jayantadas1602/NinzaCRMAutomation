package TestCases;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import genericUtility.ExcelUtility;
import genericUtility.JavaUtility;
import genericUtility.PropertiesFileUtility;
import genericUtility.WebDriverUtility;
import objectRepository.CampaignPage;
import objectRepository.HomePage;
import objectRepository.LoginPage;

public class CreateCampaignWithExpectedDate {
	public static void main(String[] args) throws InterruptedException, Throwable {
		
		PropertiesFileUtility putil = new PropertiesFileUtility();
		ExcelUtility eutil = new ExcelUtility();
		JavaUtility jutil = new JavaUtility();
		WebDriverUtility wutil = new WebDriverUtility();
		
		String BROWSER = putil.toReadDataFromPropertiesFile("Browser");
		String URL = putil.toReadDataFromPropertiesFile("Url");
		String USERNAME = putil.toReadDataFromPropertiesFile("Username");
		String PASSWORD = putil.toReadDataFromPropertiesFile("Password");
		
		
		//WebDriver initialization to avoid hardcoding later
		WebDriver driver = null;

		if(BROWSER.equals("Chrome")) {
		ChromeOptions settings = new ChromeOptions(); 
		Map<String, Object> prefs = new HashMap<>(); 
		prefs.put("profile.password_manager_leak_detection", false); 
		settings.setExperimentalOption("prefs", prefs);
		driver = new ChromeDriver(settings);
		}
		
		else if(BROWSER.equals("Edge")) {
			driver = new EdgeDriver();
		}
		else if(BROWSER.equals("Firefox")) {
			driver = new FirefoxDriver();
		}
		
		//Now using the file data instead of hardcoded data
		driver.manage().window().maximize();
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wutil.waitForPageToLoad(driver);
		driver.get(URL);
		
		//LOGIN PAGE
		LoginPage lp = new LoginPage(driver);
		
		lp.getUsernameTF().sendKeys(USERNAME);
	
		lp.getPwdTF().sendKeys(PASSWORD);
	
		lp.getSignInBtn().click();
		String datereq = jutil.togetReqDate(30);
		
		
		
		String campaign_name = eutil.toReadDataFromExcel("Campaign", 1, 2);
		String target = eutil.toReadDataFromExcel("Campaign", 1, 3);
		
		
		HomePage hp = new HomePage(driver);
		CampaignPage cp = new CampaignPage(driver);
		hp.getCreateCampBtn().click();
		hp.getCreateCampBtn().click();
		cp.getCampaignNameTF().sendKeys(campaign_name);
		WebElement target_size = cp.getTargetTF();
		target_size.clear();
		target_size.sendKeys(target);	
		driver.findElement(By.name("expectedCloseDate")).sendKeys(datereq);
		cp.getDateTF().sendKeys(datereq);
		Thread.sleep(3000);
		cp.getCreateCampSubmitBtn().click();
		
		
		WebElement toast_message = cp.getToastmsg();
		
		wutil.waitForVisibilityOfElement(toast_message, driver);
		
		//Getting the text of toast for validation
		String msg = toast_message.getText();
		//checking part of toast message for validation
		if(msg.contains(campaign_name)) {
			System.out.println("Campaign Created");
		}
		else {
			System.out.println("Campaign Not Created");
		}
		driver.findElement(By.xpath("//button[@aria-label='close']")).click();//to close toast message
		cp.getClosemsg().click();//to close toast message
		
		
		WebElement logout_icon = hp.getUserIcon();
		
		wutil.mouseHoverOnWebElement(driver, logout_icon);
		hp.getLogoutBtn().click();
		driver.quit();
	}
}




